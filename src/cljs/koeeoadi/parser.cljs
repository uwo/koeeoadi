(ns koeeoadi.parser
  (:require [om.next :as om]))

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
  (let [face  (get-in state face-ident)]
    (merge face (select-keys face [:face/color-fg :face/color-bg]))))

(defn get-faces [state key]
  (let [st @state]
    (into [] (map #(get-face st %)) (get st key))))

(defmethod read :faces/list
  [{:keys [state query]} k params]
  {:value (get-faces state k)})

(defmethod read :custom-faces
  [{:keys [state query]} _ _]
  {:value
   (let [st @state]
     (om/db->tree query st st))})

(defmethod read :palette-picker
  [{:keys [state query]} _ _]
  {:value
   (let [st @state]
     (om/db->tree query st st))})

(defmethod read :theme-actions
  [{:keys [state]} _ _]
  {:value (select-keys @state [:theme/name :theme/name-temp :theme/map])})

(defmethod read :code-display
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

(defmethod read :face-editor
  [{:keys [state query]} _ _]
  {:value
   (let [st @state]
     (om/db->tree query st st))})

;;** Mutators
(defmulti mutate om/dispatch)

;; non-local
(defmethod mutate :default
  [_ _ _] {:remote true})

(defmethod mutate 'theme/edit-name
  [{:keys [state]} _ props]
  {:action
   (fn []
     (swap! state merge props))})

(defmethod mutate 'theme/change-name
  [{:keys [state]} _ {:keys [new-name prev-name]}]
  {:action
   (fn []
     (reset! state (-> @state
                     (assoc :theme/name new-name :theme/name-temp nil)
                     (update :theme/map clojure.set/rename-keys {prev-name new-name}))))})

(defn current-theme [state]
  "Extracts current theme data from the current state"
  (let [current-theme-name (:theme/name state)
        current-theme-data (select-keys state [:colors/list
                                               :faces/list
                                               :colors/by-id
                                               :faces/by-name])]
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
                                     :theme/name-temp ""
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
  {:value {:keys [:faces/list]}
   :action
   (fn []
     (let [st      @state
           new-id  (new-color-id (:colors/list st))
           state'  (update st :colors/by-id merge {new-id {:color/id new-id}})
           state'' (update state' :colors/list conj [:colors/by-id new-id])]
       (reset! state state'')))})

(defmethod mutate 'color/remove
  [{:keys [state ref]} _ _]
  {:action
   (fn []
     (reset! state (-> @state
                     (update :colors/by-id dissoc (last ref))
                     (update :colors/list #(filterv (partial not= ref) %)))))})

(defmethod mutate 'color/update
  [{:keys [state]} _ {:keys [id rgb] :as props}]
  {:value {:keys [:faces/list]}
   :action
   (fn []
     (swap! state assoc-in [:colors/by-id id :color/rgb] rgb))})

(defmethod mutate 'face/update
  [{:keys [state]} _ {:keys [:face/name] :as props}]
  {:action
   (fn []
     (swap! state update-in [:faces/by-name name] merge props))})

(defmethod mutate 'face/color-update
  [{:keys [state]} _ {:keys [face/name] :as props}]
  {:value {:keys [:faces/list]}
   :action
   (fn []
     (println "in color-update")
     (.log js/console props)
     (swap! state update-in [:faces/by-name name] merge props))})

(defmethod mutate 'palette-picker/update
  [{:keys [state]} _ props]
  {:value {:keys [:palette-picker]}
   :action
   (fn []
     (swap! state merge props))})

(defmethod mutate 'custom-face/add
  [{:keys [state]} _ props]
  {:action
   (fn []
     (swap! state update :custom-faces/map merge props))})

(defmethod mutate 'custom-face/remove
  [{:keys [state]} _ {:keys [face/name]}]
  {:action
   (fn []
     (swap! state update :custom-faces/map dissoc name))})

(defmethod mutate 'custom-face/edit-name
  [{:keys [state]} _ {:keys [face/name] :as props}]
  {:action
   (fn []
     (swap! state update-in [:custom-faces/map name] merge props))})

(defmethod mutate 'custom-face/change-name
  [{:keys [state]} _ {:keys [current-name]}]
  {:action
   (fn []
     (let [st       @state
           new-name (get-in st [:custom-faces/map current-name :face/name-temp])]
       (swap! state merge
         {:custom-faces/map (-> (:custom-faces/map st)
                              (clojure.set/rename-keys {current-name new-name})
                              (update new-name merge {:face/name new-name
                                                      :face/name-temp nil}))})))})

(defmethod mutate 'custom-face/update
  [{:keys [state]} _ {:keys [face/name] :as props}]
  {:action
   (fn []
     (swap! state update-in [:custom-faces/map name] merge props))})

(def parser (om/parser {:read read :mutate mutate}))
