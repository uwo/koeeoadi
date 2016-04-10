(ns koeeoadi.components.history
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [koeeoadi.reconciler :refer [reconciler]]))

(defn js-last [arr]
  (let [length (.-length arr)]
    (aget arr (dec length))))

(defn internal-history-map []
  (.-index (get-in reconciler [:config :history])))

(defn internal-history-stack []
  (.-arr (get-in reconciler [:config :history])))

(defn history-item [comp history]
  (dom/div nil (str history)))

(defn undo [comp]
  (let [{:keys [history-stack]} (om/get-state comp)
        uuid (:mutate/uuid (first history-stack))]
    (om/update-state! comp update :history-stack #(drop 1 %))
    (om/transact! comp
      `[(state/reset ~(merge
                        (om/from-history reconciler uuid)
                        {:mutate/name :history/undo}))
        :colors/list
        :theme/map])))

(defn log-mutation? [mutate-name]
  (and mutate-name
    (not= :history/redo mutate-name)
    (not= :history/undo mutate-name)))

(defn log-history [comp key atom old-state new-state]
  (let [mutate-name (key new-state)]
    (when (log-mutation? mutate-name)
      (om/update-state! comp update :history-stack conj
        {:mutate/uuid (js-last (internal-history-stack))
         :mutate/name mutate-name}))))

(defn disabled-class [history-stack]
  (if (empty? history-stack)
    "disabled"
    ""))

(defui History
  ;; TODO Dummy query so I can transact! for undo and redo.
  ;; Wish there was a better way to do this
  static om/IQuery
  (query [this]
    [])

  Object
  (componentDidMount [this]
    (add-watch (om/app-state reconciler) :mutate/name
      (partial log-history this)))

  (componentInitState [this]
    {:history-stack nil})
  (render [this]
    (let [{:keys [history-stack]} (om/get-state this)]
      (apply dom/div #js {:id "history" :className "widget"}
        (dom/h5 #js {:className "widget-title"} "History")
        (dom/button #js {:onClick #(undo this)
                         :className (disabled-class history-stack)} "Undo")
        (map #(history-item comp %) history-stack)))))

(def history (om/factory History))
