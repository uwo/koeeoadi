(ns komokio.components.palette
  (:require [goog.dom :as gdom]
            [goog.events :as gevents]
            [om.dom :as dom]
            [om.next :as om :refer-macros [defui]])
  (:import goog.events.EventType))

(defn handle-color-change [e comp {:keys [color/id] :as color}]
  (let [rgb-editing (.. e -target -value)]
    (om/transact! comp
      `[(color/update {:id ~id :rgb ~rgb-editing}) :faces]))) ;; update all faces

(defui Color
  static om/Ident
  (ident [this {:keys [color/id]}]
    [:colors/by-id id])

  static om/IQuery
  (query [this]
    [:color/id :color/rgb])

  Object
  ;; will be adding a editing-color property here
  (initLocalState [this] {})

  (render [this]
    (let [{:keys [color/id color/rgb] :as color} (om/props this)
          {:keys [rgb-editing]}                          (om/get-state this)]
      (dom/div #js {:className "color color-input"}
        (dom/div #js {:className  (str "input-overlay" (if rgb "" " color-missing"))
                      :style      (when rgb #js {:backgroundColor rgb})})
        (dom/input #js {:type     "color"
                        :value    rgb
                        :onChange #(handle-color-change % this color)})))))

(def color (om/factory Color {:keyfn :color/id}))

(defn handle-color-adder-add [comp]
  (om/transact! comp '[(colors/add) :colors/list]))

(defui ColorAdder
  Object
  (render [this]
    (let [callback (:callback (om/get-computed this))]
      (dom/div #js {:className "color color-add"
                    :onClick callback}
        (dom/i #js {:className "fa fa-plus"})))))

(def color-adder (om/factory ColorAdder))

(defui Palette
  static om/IQuery
  (query [this]
    [{:colors/list (om/get-query Color)}])

  Object
  (render [this]
    (let [{:keys [colors/list]} (om/props this)
          callback (partial handle-color-adder-add this)]
      (dom/div #js {:id "palette"
                    :className "widget"}
        (dom/h5 nil "Palette")
        (apply dom/div #js {:className "palette-colors"}
          (conj (mapv color list)
            (color-adder (om/computed {} {:callback callback}))))))))

(def palette (om/factory Palette))
