(ns koeeoadi.components.history
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [koeeoadi.reconciler :refer [reconciler]]))

(def mutate-display-strings
  {'code/change            "change language"
   'color/add              "add color"
   'color/remove           "remove color"
   'color/update           "change color"
   'face/update            "update face style"
   'face/color-update      "update face color"
   'theme/select           "change theme"
   'theme/rename           "rename theme"
   'theme/new              "new theme"
   'theme/load             "load theme"
   'user-face/add          "add face"
   'user-face/change-name  "change face name"
   'user-face/remove       "remove face"
   'user-face/update       "change face style"})

(defn js-last [arr]
  (let [length (.-length arr)]
    (aget arr (dec length))))

(defn internal-history-map []
  (.-index (get-in reconciler [:config :history])))

(defn internal-history-stack []
  (.-arr (get-in reconciler [:config :history])))

(defn latest-uuid []
  (js-last (internal-history-stack)))

(defn history-item [history]
  (let [mutate-name (:mutate/name history)
        display-string (mutate-name mutate-display-strings)]
    (dom/li #js {:className "striped"} (or display-string (str mutate-name)))))

;; Undo pops one from its stack and adds it to active
;; also commits the current state and adds that to the redo stack
(defn undo [comp]
  ;; if active is nil then conj current one onto stack and make active 1
  (let [{:keys [history-stack active] :as state} (om/get-state comp)
        uuid (:mutate/uuid (first history-stack))]
    (if (nil? active)
      (do
        (om/transact! comp
          `[(state/reset ~(merge
                            (om/from-history reconciler uuid)
                            {:mutate/name :history/ignore}))])
        (om/set-state! comp (-> state
                              (update :history-stack conj {:mutate/name :history/ignore
                                                           :mutate/uuid (latest-uuid)})
                              (assoc :active 1)
                              (assoc :action :history/undo))))
      (om/update-state! comp update :active inc))))

(defn redo [comp]
  (let [active (:active (om/get-state comp))]
    (om/update-state! comp update :active dec)))

(defn log-mutation? [mutate-name]
  (and mutate-name
    (not= :history/redo mutate-name)
    (not= :history/ignore mutate-name)
    (not= :history/undo mutate-name)))

(defn log-history [comp key atom old-state new-state]
  (let [mutate-name (key new-state)
        {:keys [active history-stack] :as state} (om/get-state comp)
        new-entry {:mutate/uuid (latest-uuid)
                   :mutate/name mutate-name}]
    (when (log-mutation? mutate-name)
      ;; In this case:
      ;; drop undo-stack to active
      ;; set active back to nil
      ;; conj new-entry onto undo
      (if active
        (let [history-stack' (conj (drop (inc active) history-stack) new-entry)]
          (om/update-state! comp assoc :active nil :history-stack history-stack'))
        (om/update-state! comp assoc :history-stack (conj history-stack new-entry))))))

(defn disabled-class [undos-or-redos]
  (when (empty? undos-or-redos)
    "disabled"))

(defn history-split [active history-stack]
  "Splits history stack into undos and redos"
  (if active
    (let [[redos active-and-undos] (split-at active history-stack)]
      [redos (rest active-and-undos)])
    [[] history-stack]))

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

  (initLocalState [this]
    {:history-stack (list)
     ;; active is an index to the history-stack
     ;; if the currently active app-state is from history then active should reflect that
     ;; this gets set to nil whenever a named mutation is received (besides history mutations of course)
     :active nil})

  (componentDidUpdate [this prev-props {active-prev :active}]
    (let [{:keys [history-stack active action]} (om/get-state this)]
      ;; the undo and redo functions above will update active.
      ;; when this happens, reset the app-state to the state
      ;; it indexes in the history-stack.
      (when (not= active active-prev)
        (let [uuid (:mutate/uuid (nth history-stack active))]
          (om/transact! this `[(state/reset
                                 ~(merge
                                    (om/from-history reconciler uuid)
                                    {:mutate/name action}))
                               :palette
                               ])))))
  (render [this]
    (let [{:keys [history-stack active]} (om/get-state this)
          [redos undos] (history-split active history-stack)]
      (dom/div #js {:id "history" :className "widget"}
        (dom/h5 #js {:className "widget-title"} "History")
        (dom/div #js {:className "row"}
          (dom/div #js {:className "one-half column"}
            (dom/button #js {:onClick #(undo this)
                             :className (disabled-class undos)} "Undo"))
          (dom/div #js {:className "one-half column"}
            (dom/button #js {:onClick #(redo this)
                             :className (disabled-class redos)} "Redo")))
        (apply dom/ul #js {:id "history-container"}
          (map history-item undos))))))

(def history (om/factory History))
