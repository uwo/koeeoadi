(ns koeeoadi.parser
  (:require [om.next :as om]
            [koeeoadi.util :as util]
            [clojure.set :refer [rename-keys]]))

(defmulti read om/dispatch)

(defmethod read :default
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

(defmethod read :color-picker
  [{:keys [state query]} _ _]
  {:value
   (let [st @state]
     (om/db->tree query st st))})

(defmethod read :theme
  [{:keys [state]} _ _]
  {:value (select-keys @state [:theme/name :theme/name-temp :theme/map])})

(defmethod read :code
  [{:keys [state query]} _ _]
  {:value
   (let [st @state]
     (om/db->tree query st st))})

;; TODO combine these types of queries
(defmethod read :palette
  [{:keys [state query]} _ _]
  {:value
   (let [st @state]
     (om/db->tree query st st))})

(defmethod read :faces
  [{:keys [state query]} _ _]
  {:value
   (let [st @state]
     (om/db->tree query st st))})

(defmethod read :user-faces
  [{:keys [state query]} _ _]
  {:value
   (let [st @state]
     (om/db->tree query st st))})

;;** Mutators
(defmulti mutate om/dispatch)

;; non-local
(defmethod mutate :default
  [_ _ _] {:remote true})

(defmethod mutate 'state/merge
  [{:keys [state]} _ props]
  (.log js/console props)
  (swap! state merge props))

(defmethod mutate 'theme/update
  [{:keys [state]} _ props]
  {:action
   (fn []
     (swap! state merge props))})

(defmethod mutate 'theme/rename
  [{:keys [state]} _ {:keys [new-name prev-name]}]
  {:action
   (fn []
     (reset! state (-> @state
                     (assoc :theme/name new-name :theme/name-temp nil)
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
  [{:keys [state]} _ theme]
  {:action
   (fn []
     (let [st @state
           new-theme-map (theme-map st (current-theme st) {(:theme/name theme) theme})]
       (swap! state (partial merge-with #(identity %2))
         theme {:theme/map new-theme-map})))})

;; TODO there is a bug in this function where
;; its not merging correctly.  Not a big deal.
(defmethod mutate 'theme/new
  [{:keys [state]} _ _]
  {:action
   (fn []
     (let [st            @state
           new-theme     (get-in st [:theme/map "basic-bw"])
           new-theme-map (merge {"new-theme" new-theme} (theme-map st))]
       (swap! state merge new-theme {:theme/name      "new-theme"
                                     :theme/name-temp "new-theme"
                                     :theme/map       new-theme-map})))})

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
  [{:keys [state]} _ _]
  {:action
   (fn []
     (let [st      @state
           new-id  (new-color-id (:colors/list st))]
       (reset! state (-> st
                       (assoc-in [:colors/by-id new-id] {:color/id new-id :color/hex "#FFFFFF"})
                       (update :colors/list conj [:colors/by-id new-id])
                       (assoc :palette-widget/active-color [:colors/by-id new-id])
                       (assoc :palette-widget/face-classes-by-color-type (util/faces-to-colorize (:faces/list st) new-id))))))})

(defmethod mutate 'palette-widget/update
  [{:keys [state]} _ props]
  {:action
   (fn []
     (swap! state merge props))})

(defn next-active-color [{:keys [:palette-widget/active-color :faces/list]} color-id-removing]
  (if (= color-id-removing (:color/id active-color))
    (let [[left-colors right-colors] (split-with #(< (last %) color-id-removing) list)]
      (or (second right-colors) (last left-colors)))
    active-color))

(defmethod mutate 'color/remove
  [{:keys [state ref]} _ _]
  {:action
   (fn []
     (let [st                @state
           color-id-removing (last ref)
           active-color      (next-active-color st color-id-removing)]
       (reset! state (-> st
                       (update :colors/by-id dissoc (last ref))
                       (update :colors/list #(filterv (partial not= ref) %))
                       (assoc :palette-widget/active-color active-color)
                       (assoc :palette-widget/face-classes-by-color-type (util/faces-to-colorize (:faces/list st) (:color/id active-color)))))))})

(defmethod mutate 'color/update
  [{:keys [state]} _ {:keys [color/id] :as props}]
  {:action
   (fn []
     (swap! state update-in [:colors/by-id id] merge props))})

(defmethod mutate 'face/update
  [{:keys [state]} _ {:keys [:face/name] :as props}]
  {:action
   (fn []
     (swap! state update-in [:faces/by-name name] merge props))})

(defmethod mutate 'face/color-update
  [{:keys [state]} _ {:keys [face/name] :as props}]
  {:action
   (fn []
     (swap! state update-in [:faces/by-name name] merge props))})

(defmethod mutate 'color-picker/update
  [{:keys [state]} _ props]
  {:action
   (fn []
     (swap! state merge props))})

(defmethod mutate 'user-face/add
  [{:keys [state]} _ {:keys [face/name] :as props}]
  {:action
   (fn []
     (reset! state (-> @state
                     (assoc-in [:user-faces/by-name name] props)
                     (update :user-faces/list conj [:user-faces/by-name name]))))})

(defmethod mutate 'user-face/remove
  [{:keys [state ref]} _ _]
  {:action
   (fn []
     (reset! state (-> @state
                     (update :user-faces/by-name dissoc (last ref))
                     (update :user-faces/list #(filterv (partial not= ref) %)))))})

(defmethod mutate 'user-face/update
  [{:keys [state ref]} _ props]
  {:action
   (fn []
     (swap! state update-in ref merge props))})

(defmethod mutate 'user-face/change-name
  [{:keys [state ref]} _ _]
  {:action
   (fn []
     (let [st        @state
           prev-name (last ref)
           new-name  (:face/name-temp (get-in st ref))]
       (reset! state (-> @state
                       (update-in ref merge {:face/name      new-name
                                             :face/name-temp nil})
                       (update :user-faces/by-name rename-keys {prev-name new-name})
                       (update :user-faces/list #(filterv (partial not= ref)
                                                   (conj % [:user-faces/by-name new-name])))))))})

(def parser (om/parser {:read read :mutate mutate}))
