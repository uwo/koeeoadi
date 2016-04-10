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

(defui History
  Object
  (componentDidMount [this]
    (add-watch (om/app-state reconciler) :history-watcher
      (fn [key atom old-state new-state]
        (println "in watcher")
        (let [mutate-name       (:mutate/name new-state)
              hist-map          @(internal-history-map)
              last-uuid         (js-last (internal-history-stack))
              mutate-name-prev  (:mutate/name (get hist-map last-uuid))]
          (println mutate-name-prev)
          ;; (when-not (and mutate-name (not= :redo mutate-name) (not= :undo mutate-name))

          )
        )))

  (componentInitState [this]
    {:history-stack []})
  (render [this]
    (let [{:keys [history-stack]} (om/get-state this)]
      (dom/div #js {:id "history" :className "widget"}
        (dom/h5 #js {:className "widget-title"} "History")
        ;;(apply dom/div nil (map #(history-item comp %) history-stack))
        ))))

(def history (om/factory History))

;;; if I ever pop up

;; on mutate



;; # | mutate # | type? | undo stack | redo stack | lastwasredo? |
;; # |----------+-------+------------+------------+--------------|
;; # | m1       | n     | []         |            | n            |
;; # | m2       | t     | [m1]       |            | n            |
;; # | m3       | t     | [m1 m2]    |            | n            |
;; # | m4       | t     | [m1 m2 m3] |            | n            |
;; # | m5       | undo  | [m1 m2]    | [m3]       | n            |
;; # | m6       | n     | [m1 m2]    | [m3]       | n            |
;; # | m7       | undo  | [m1]       | [m3 m2]    | n            |
;; # | m8       | redo  | [m1 m2]    | [m3]       | y            |
;; # | m9       | y     | [m1 m2 m3] | []         |              |
