(ns koeeoadi.components.colorpicker
  (:require [goog.style :as gstyle]
            [goog.dom.classes :as gclasses]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]

            [koeeoadi.components.palette :refer [Color]]
            [koeeoadi.reconciler :refer [reconciler]]
            [koeeoadi.util :as util]))

(defn color-option-void [{:keys [color-update-temp' color-update-void]}]
  (dom/button #js
    {:className    "color color-option color-undefined"
     :onMouseLeave #(color-update-temp')
     :onMouseDown  #(color-update-void)}))

(defui ColorOption
  static om/Ident
  (ident [this {:keys [color/id]}]
    [:colors/by-id id])

  static om/IQuery
  (query [this]
    [:color/id :color/hex])

  Object
  (render [this]
    (let [{:keys [color/hex]}         (om/props this)
          {:keys [color-update-temp'
                  color-update]} (om/get-computed this)]
      (dom/button #js
        {:className    "color color-option"
         :onMouseEnter #(color-update-temp' hex)
         :onMouseLeave #(color-update-temp')
         :onMouseDown  #(color-update (om/props this))
         :style        #js {:backgroundColor hex}}))))

(def color-option (om/factory ColorOption {:keyfn :color/id}))

(defn color-update-temp
  ([face-name orig-color color-type]
   (color-update-temp face-name orig-color color-type orig-color))
  ([face-name orig-color color-type hover-color-hex]
   (let [code-update-prop (if (and
                                (not (= face-name "background"))
                                (= color-type :face/color-fg))
                            "color"
                            "background-color")

         code-selector (str ".code-" face-name)

         face-color-type-selector (str ".color-" (if (= color-type :face/color-bg) "bg-" "fg-") face-name)]
     (util/update-elements-by-selector code-selector #(gstyle/setStyle % code-update-prop hover-color-hex))
     (util/update-elements-by-selector face-color-type-selector #(gstyle/setStyle % "background" hover-color-hex)))))

(defui ColorPicker
  static om/IQuery
  (query [this]
    [:color-picker/coordinates
     :color-type
     {:color-picker/active-face [:face/id
                                 :face/name
                                 {:face/color-bg [:color/id
                                                  :color/name
                                                  :color/hex]}
                                 {:face/color-fg [:color/id
                                                  :color/name
                                                  :color/hex]}]}
     {:colors/list (om/get-query Color)}])

  Object
  (render [this]
    (let [{:keys [colors/list
                  color-type
                  color-picker/coordinates
                  color-picker/active-face] :as props}
          (om/props this)

          {face-name :face/name}
          active-face

          color-update-void
          (fn []
            (let [{:keys [face/id face/name]} active-face]
              (om/transact! this `[(face/color-update
                                     {:face/id    ~id
                                      :face/name  ~name
                                      ~color-type nil})])))

          color-update
          (fn [color]
            (let [{:keys [face/id face/name]} active-face]
              (om/transact! this `[(face/color-update
                                     {:face/id    ~id
                                      :face/name  ~name
                                      ~color-type [:colors/by-id ~(:color/id color)]})])))
          ;; can factor out of this method and partially apply it
          color-update-temp' (partial color-update-temp face-name (get-in active-face [color-type :color/hex]) color-type)

          computed
          {:color-update-temp' color-update-temp'
           :color-update-void color-update-void
           :color-update      color-update}

          color-options-computed
          (map #(om/computed % computed) list)]
      (when coordinates
        (apply
          dom/div
          #js {:id    "color-picker"
               :style #js {:position "absolute"
                           :top      (:x coordinates)
                           :left     (:y coordinates)}}
          (color-option-void computed)
          (map color-option color-options-computed))))))


(def color-picker (om/factory ColorPicker))

(defn color-picker-comp []
  (om/class->any reconciler ColorPicker))
