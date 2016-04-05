(ns koeeoadi.components.colorpicker
  (:require [goog.style :as gstyle]
            [goog.dom.classes :as gclasses]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]

            [koeeoadi.components.palette :refer [Color]]
            [koeeoadi.reconciler :refer [reconciler]]
            [koeeoadi.util :as util]))

(defn color-option-void [{:keys [color-update-temp color-update-void]}]
  (dom/button #js
    {:className    "color color-option color-undefined"
     :onMouseLeave #(color-update-temp nil)
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
          {:keys [color-update-temp
                  color-update]} (om/get-computed this)]
      (dom/button #js
        {:className    "color color-option"
         :onMouseEnter #(color-update-temp hex)
         :onMouseLeave #(color-update-temp nil)
         :onMouseDown  #(color-update (om/props this))
         :style        #js {:backgroundColor hex}}))))

(def color-option (om/factory ColorOption {:keyfn :color/id}))

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
  (componentDidUpdate [this prev-props prev-state]
    (let [{:keys [face/name]} (:color-picker/active-face (:color-picker (om/props this)))]
      (if name
        (util/update-other-code-face-elements name #(gclasses/add % "code-temp-minimize"))
        (util/update-code-elements #(gclasses/remove % "code-temp-minimize")))))

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
          color-update-temp
          (fn [hover-color-hex]
            (let [prop (if (and
                             (not (= face-name "background"))
                             (= color-type :face/color-fg))
                         "color"
                         "background-color")]
              (if hover-color-hex
                (util/update-code-face-elements face-name #(gstyle/setStyle % prop hover-color-hex))
                (util/update-code-face-elements face-name #(gstyle/setStyle % prop (:color/hex (get active-face color-type)))))))

          computed
          {:color-update-temp color-update-temp
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
