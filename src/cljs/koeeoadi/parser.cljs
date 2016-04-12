(ns koeeoadi.parser
  (:require [om.next :as om]
            [koeeoadi.util :as util]
            [clojure.set :refer [rename-keys]]))

(defmulti read om/dispatch)

(defmethod read :default
  [{:keys [state query]} _ _]
  {:value
   (let [st @state]
     (om/db->tree query st st))})

(defmethod read :code-background
  [{:keys [state query]} k params]
  (let [st @state]
    (if (contains? st k)
      {:value (get st k)}
      {:remote true})))

(defn get-colors [state]
  (let [st @state]
    (into [] (map #(get-in st %)) (get st :colors/list))))

(defmethod read :colors/list
  [{:keys [state]} _ _]
  {:value (get-colors state)})

(defn get-face [state face-ident]
  (get-in state face-ident))

(defn get-faces [state key]
  (let [st @state]
    (into [] (map #(get-face st %)) (get st key))))

(defmethod read :faces/list
  [{:keys [state query]} k _]
  {:value (get-faces state k)})

(defmethod read :user-faces/list
  [{:keys [state query]} k _]
  {:value (get-faces state k)})

(defmethod read :theme
  [{:keys [state]} _ _]
  {:value (select-keys @state [:theme/name :theme/map])})

;;** Mutators
(defmulti mutate om/dispatch)

(defn wrap-mutate-name [props k]
  (assoc props :mutate/name k))

;; non-local
(defmethod mutate :default
  [_ _ _] {:remote true})

(defmethod mutate 'state/merge
  [{:keys [state]} _ props]
  {:action
   (fn []
     (swap! state merge props))})

(defmethod mutate 'state/reset
  [{:keys [state]} _ props]
  {:action
   (fn []
     (reset! state props))})

(defmethod mutate 'state/update-ref
  [{:keys [state ref]} _ {:keys [mutate/name props]}]
  {:action
   (fn []
     (reset! state (-> @state
                     (wrap-mutate-name name)
                     (update-in ref merge props))))})

(defmethod mutate 'theme/rename
  [{:keys [state]} k {:keys [new-name prev-name]}]
  {:action
   (fn []
     (reset! state (-> @state
                     (wrap-mutate-name k)
                     (assoc :theme/name new-name)
                     (update :theme/map rename-keys {prev-name new-name}))))})

(defn current-theme [state]
  "Extracts current theme data from the current state"
  (let [current-theme-name (:theme/name state)
        current-theme-data (select-keys state
                             [:colors/list     :colors/by-id
                              :faces/list      :faces/by-name
                              :user-faces/list :user-faces/by-name])]
    {current-theme-name current-theme-data}))

(defn theme-map
  ([state]
   (theme-map state (current-theme state)))
  ([state & themes]
   (apply merge (:theme/map state) themes)))

(defmethod mutate 'theme/change
  [{:keys [state]} _ new-theme]
  {:action
   (let [st @state
         new-theme-map (theme-map st)]
     (swap! state merge new-theme {:theme/map new-theme-map}))})

(defmethod mutate 'theme/load
  [{:keys [state]} k theme]
  {:action
   (fn []
     (let [st @state
           new-theme-map (theme-map st (current-theme st) {(:theme/name theme) theme})]
       (swap! state (partial merge-with #(identity %2))
         theme {:theme/map new-theme-map :mutate/name k})))})

(defmethod mutate 'theme/new
  [{:keys [state]} k _]
  {:action
   (fn []
     (let [st            @state
           new-theme     (get-in st [:theme/map "basic-bw"])
           new-theme-map (merge {"new-theme" new-theme} (theme-map st))]
       (swap! state merge new-theme {:theme/name  "new-theme"
                                     :theme/map   new-theme-map
                                     :mutate/name 'theme/new})))})

(defn code-merge [current-data new-data]
  (cond (map? current-data)
        (merge current-data new-data)

        (set? current-data)
        (clojure.set/union current-data new-data)

        :else new-data))

(defmethod mutate 'code/change
  [{:keys [state]} _ code]
  {:action
   (fn []
     (swap! state (partial merge-with code-merge) code))})

(defn new-color-id [colors]
  (let [ids (sort-by #(- (last %)) colors)]
    (inc (last (first ids)))))

(defmethod mutate 'color/add
  [{:keys [state]} k _]
  {:action
   (fn []
     (let [st      @state
           new-id  (new-color-id (:colors/list st))]
       (reset! state (-> st
                       (wrap-mutate-name k)
                       (assoc-in [:colors/by-id new-id] {:color/id new-id :color/hex "#FFFFFF"})
                       (update :colors/list conj [:colors/by-id new-id])
                       (assoc :palette-widget/active-color [:colors/by-id new-id])
                       (assoc :palette-widget/face-classes-by-color-type (util/faces-to-colorize (:faces/list st) new-id))))))})

(defn next-active-color [{:keys [:palette-widget/active-color :colors/list]} color-id-removing]
  (if (= color-id-removing (last active-color))
    (let [[left-colors right-colors] (split-with #(< (last %) color-id-removing) list)]
      (or (second right-colors) (last left-colors)))
    active-color))

(defmethod mutate 'color/remove
  [{:keys [state ref] :as all} k _]
  {:action
   (fn []
     (let [st                @state
           color-id-removing (last ref)
           active-color      (next-active-color st color-id-removing)]
       (reset! state (-> st
                       (wrap-mutate-name k)
                       (update :colors/by-id dissoc (last ref))
                       (update :colors/list #(filterv (partial not= ref) %))
                       (assoc :palette-widget/active-color active-color)
                       (assoc :palette-widget/face-classes-by-color-type (util/faces-to-colorize (:faces/list st) (:color/id active-color)))))))})

(defmethod mutate 'user-face/add
  [{:keys [state]} k {:keys [face/name] :as props}]
  {:action
   (fn []
     (reset! state (-> @state
                     (wrap-mutate-name k)
                     (assoc-in [:user-faces/by-name name] props)
                     (update :user-faces/list conj [:user-faces/by-name name]))))})

(defmethod mutate 'user-face/remove
  [{:keys [state ref]} k _]
  {:action
   (fn []
     (reset! state (-> @state
                     (wrap-mutate-name k)
                     (update :user-faces/by-name dissoc (last ref))
                     (update :user-faces/list #(filterv (partial not= ref) %)))))})

(defmethod mutate 'user-face/change-name
  [{:keys [state ref]} k {:keys [new-name]}]
  {:action
   (fn []
     (let [st        @state
           prev-name (last ref)]
       (reset! state (-> @state
                       (wrap-mutate-name k)
                       (update-in ref merge {:face/name      new-name})
                       (update :user-faces/by-name rename-keys {prev-name new-name})
                       (update :user-faces/list #(filterv (partial not= ref)
                                                   (conj % [:user-faces/by-name new-name])))))))})

(def parser (om/parser {:read read :mutate mutate}))
