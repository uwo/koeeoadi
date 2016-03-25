(ns komokio.components.palettepicker
  (:require [goog.style :as gstyle]
            [goog.dom.classes :as gclasses]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]

            [komokio.components.palette :refer [Color]]
            [komokio.components.faceeditor :refer [Face]]
            [komokio.util :as util]))

(defui ColorOption
  static om/Ident
  (ident [this {:keys [color/id]}]
    [:colors/by-id id])

  static om/IQuery
  (query [this]
    [:color/id :color/rgb])

  Object
  (render [this]
    (let [{:keys [color/rgb]}         (om/props this)
          {:keys [colorHoverHandler
                  colorClickHandler]} (om/get-computed this)]
      (dom/button #js
        {:className    "color color-option"
         :onMouseEnter #(colorHoverHandler rgb)
         :onMouseLeave #(colorHoverHandler nil)
         :onMouseDown  #(colorClickHandler (om/props this))
         :style        #js {:backgroundColor rgb}}))))

(def color-option (om/factory ColorOption {:keyfn :color/id}))


(defui PalettePicker
  static om/IQuery
  (query [this]
    [:palette-picker/coordinates
     {:palette-picker/active-face (om/get-query Face)}
     {:colors/list (om/get-query Color)}])

  Object
  (componentInitState [this]
    {})

  (componentDidUpdate [this prev-props prev-state]
    (let [{:keys [face/name]} (:palette-picker/active-face (:palette-picker (om/props this)))]
      (if name
        (util/update-other-code-face-elements name #(gclasses/add % "code-temp-minimize"))
        (util/update-code-elements #(gclasses/remove % "code-temp-minimize")))))

  (render [this]
    (let [{:keys [colors/list
                  palette-picker/coordinates
                  palette-picker/active-face]} (om/props this)

          {face-name :face/name
           face-color-rgb :face/color} active-face
          colorClickHandler (fn [color]
                              (let [{:keys [db/id face/name]} active-face]
                                (om/transact! this `[(face/update
                                                       {:id       ~id
                                                        :name     ~name
                                                        :color    ~color}) ;;:face/name
                                                     ])))
          colorHoverHandler (fn [hover-color-rgb]
                              (let [prop (if (= face-name "background") "background-color" "color")]
                                (if hover-color-rgb
                                  (util/update-code-face-elements face-name #(gstyle/setStyle % prop hover-color-rgb))
                                  (util/update-code-face-elements face-name #(gstyle/setStyle % prop face-color-rgb)))))

          color-options-computed (map #(om/computed % {:colorHoverHandler colorHoverHandler
                                                       :colorClickHandler colorClickHandler}) list)]
      (when coordinates
        (apply
          dom/div
          #js {:id    "palette-picker"
               :style #js {:position "absolute"
                           :top      (:x coordinates)
                           :left     (:y coordinates)}}
          (map color-option color-options-computed))))))

(def palette-picker (om/factory PalettePicker))
