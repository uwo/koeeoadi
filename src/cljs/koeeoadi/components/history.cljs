(ns koeeoadi.components.history
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [koeeoadi.util :as util]
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

(defn undo [comp]
  (let [{:keys [history-stack active] :as state} (om/get-state comp)
        uuid (:mutate/uuid (last history-stack))]
    ;; if active is nil then conj current state onto stack and make active 1
    (if (nil? active)
      (do
        (om/transact! comp
          `[(state/reset ~(merge
                            (om/from-history reconciler uuid)
                            {:mutate/name :history/ignore}))])
        (om/set-state! comp (-> state
                              (update :history-stack conj {:mutate/name :blahblah
                                                           :mutate/uuid (latest-uuid)})
                              (assoc :active  (- (count history-stack) 1)))))
      ;; Otherwise, just increment the active index
      (om/update-state! comp update :active dec))))

(defn redo [comp]
  (let [active (:active (om/get-state comp))]
    (om/update-state! comp update :active inc)))

(defn log-mutation? [mutate-name]
  (and mutate-name
    (not= :history/ignore mutate-name)))

(defn drop-redos [active history-stack]
  (let [cnt (count history-stack)]
    (into [] (drop-last (- (dec cnt) active) history-stack))))

(defn log-history [comp key atom old-state new-state]
  (let [mutate-name (key new-state)
        {:keys [active history-stack] :as state} (om/get-state comp)
        new-entry {:mutate/uuid (latest-uuid)
                   :mutate/name mutate-name}]
    (when (log-mutation? mutate-name)
      ;; In this case: drop history-stack from active, set active back to
      ;; nil, conj new-entry onto history
      ;;(.alert js/window "logging active " active)
      (if active
        (let [history-stack' (drop-redos active history-stack)]
          ;;(.alert js/window "setting active to nil")
          (om/update-state! comp assoc :active nil :history-stack history-stack'))
        ;; Otherwise, just conj the new-entry on to the history stack
        (om/update-state! comp assoc :history-stack (conj history-stack new-entry))))))

(defn disabled-class [undos-or-redos]
  (when (empty? undos-or-redos)
    "disabled"))

(defn history-split [active history-stack]
  "Splits history stack into undos and redos"
  (if active
    (let [[undos active-and-redos] (split-at active history-stack)]
      [undos (rest active-and-redos)])
    [history-stack []]))

(defn history-button [comp type]
  (let [{:keys [active history-stack]} (om/get-state comp)
        [undos redos] (history-split active history-stack)
        [func items]  (if (= :undo type) [undo undos] [redo redos])]
    (dom/button #js {:onClick #(func comp)
                     :className (disabled-class items)}
      (dom/i #js {:className "fa fa-undo fa-3x fa-fw"}))))

(defui History
  static om/IQuery
  (query [this]
    [:widget/active])

  Object
  (componentDidMount [this]
    (add-watch (om/app-state reconciler) :mutate/name
      (partial log-history this)))

  (initLocalState [this]
    {:history-stack []
     ;; active is an index to the history-stack
     ;; if the currently active app-state is from history then active should reflect that
     ;; this gets set to nil whenever a named mutation is received (besides history mutations of course)
     :active nil})

  (componentDidUpdate [this prev-props {active-prev :active}]
    (let [{:keys [history-stack active]} (om/get-state this)]
      ;; the undo and redo functions above will update active.
      ;; when this happens, set the app-state to the state
      ;; active indexes in the history-stack.
      (when (and active (not= active active-prev))
        (let [uuid (:mutate/uuid (nth history-stack active))]
          (om/transact! this `[(state/reset
                                 ~(merge
                                    (om/from-history reconciler uuid)
                                    {:mutate/name :history/ignore}))
                               :palette])))))
  (render [this]
    (let [{:keys [history-stack active]} (om/get-state this)
          [undos redos] (history-split active history-stack)
          widget-class  (util/widget-class :history (:widget/active (om/props this)))]
      (dom/div #js {:id "history"}
        (history-button this :undo)
        (history-button this :redo)
        (dom/div nil (str history-stack))
        (dom/div nil (str active))))))

(def history (om/factory History))

(defn history-comp []
  (om/class->any reconciler History))
