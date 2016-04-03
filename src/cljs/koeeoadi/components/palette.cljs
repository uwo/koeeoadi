(ns koeeoadi.components.palette
  (:require [goog.dom :as gdom]
            [goog.events :as gevents]
            [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]

            [koeeoadi.util :refer [target-value]]))

;; TODO this is choppy.  This could be fixed by either
;; some sort of rate limiting or updating the css of only
;; the affected faces
(defn color-update [comp e]
  (om/transact! comp
    `[(color/update {:color/rgb ~(target-value e)}) :palette]))

;; TODO this will cause issues since I'm not ussing idents for user-faces list
(defn color-remove [comp {:keys [color/id] :as color}]
  (om/transact! comp
    `[(color/remove) :palette]))

(defui Color
  static om/Ident
  (ident [this {:keys [color/id]}]
    [:colors/by-id id])

  static om/IQuery
  (query [this]
    [:color/id :color/rgb])

  Object
  (render [this]
    (let [{:keys [color/id color/rgb] :as color} (om/props this)
          {:keys [rgb-editing]}                          (om/get-state this)]
      (dom/div #js {:className "color color-input"}
        (dom/div #js {:className (str "color input-overlay" (if rgb "" " color-missing"))
                      :style     (when rgb #js {:backgroundColor rgb})})
        (dom/input #js {:onChange #(color-update this %)
                        :type     "color"
                        :value    rgb})
        (dom/button #js {:className "color-remove"
                         :onClick   #(color-remove this color)}
          (dom/i #js {:className "fa fa-remove fa-2x"}))))))

(def color (om/factory Color {:keyfn :color/id}))

(defn color-add [comp]
  (om/transact! comp '[(color/add) :colors/list]))

(defui ColorAdder
  Object
  (render [this]
    (let [callback (:callback (om/get-computed this))]
      (dom/div #js {:className "color color-add"
                    :onClick   callback}
        (dom/i #js {:className "fa fa-plus"})))))

(def color-adder (om/factory ColorAdder))

(defui Palette
  static om/IQuery
  (query [this]
    [{:colors/list (om/get-query Color)}])

  Object
  (render [this]
    (let [{:keys [colors/list]} (om/props this)
          callback (partial color-add this)]
      (dom/div #js {:className "widget"
                    :id "palette"}
        (dom/h5 #js {:className "widget-title"} "Palette")
        (apply dom/div #js {:className "palette-colors"}
          (conj (mapv color list)
            (color-adder (om/computed {} {:callback callback}))))))))

(def palette (om/factory Palette))
