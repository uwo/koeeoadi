(ns komokio.components.faceeditor
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [goog.dom :as gdom]
            [goog.dom.classes :as gclasses]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]
            [cljs.core.async :refer [timeout mult tap untap put! chan <! >!]]

            [komokio.components.palette :refer [Color]]
            [komokio.config :as config]
            [komokio.util :as util]))

;; TODO delete this when I move palette picker to it's own component
(declare Face)

(defn cljs-coordinates [coords]
  {:x (aget coords "y")
   :y (aget coords "x")})

(defn update-face-color-style [comp]
  (let [props (om/props comp)
        rgb  (:color/rgb props)
        css-updater (:css-updater (om/get-computed props))]
    (css-updater rgb)))

(defui ColorOption
  static om/Ident
  (ident [this {:keys [color/name]}]
    [:colors/by-name name])

  static om/IQuery
  (query [this]
    [:db/id :color/name :color/rgb])

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

(def color-option (om/factory ColorOption {:keyfn :db/id}))

(defui PalettePicker
  static om/IQuery
  (query [this]
    [:palette-picker/coordinates
     :palette-picker/active-face-property
     {:palette-picker/active-face (om/get-query Face)}
     {:colors/list (om/get-query Color)}])

  Object
  (componentInitState [this]
    {})

  (componentDidUpdate [this prev-props prev-state])

  (render [this]
    (let [{:keys [colors/list
                  palette-picker/coordinates
                  palette-picker/active-face
                  palette-picker/active-face-property]} (om/props this)

          css-property (if (= active-face-property :face/background) "background" "color")
          {face-name :face/name} active-face
          face-color-rgb (get-in active-face [active-face-property :color/rgb])

          colorClickHandler (fn [color]
                              (let [{:keys [db/id face/name]} active-face]
                                (om/transact! this `[(face/update
                                                       {:id       ~id
                                                        :name     ~name
                                                        :bg-or-fg ~active-face-property
                                                        :color    ~color}) :face/name])))
          colorHoverHandler (fn [hover-color-rgb]
                              (if hover-color-rgb
                                (util/update-code-css face-name css-property hover-color-rgb)
                                (util/update-code-css face-name css-property face-color-rgb)))

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

(defn face-option-elm [face-node child-no]
  (aget
    (gdom/getElementsByTagNameAndClass "div" "color-trigger" (gdom/getElement face-node)) child-no))

(defn faceColorClick [_ comp face-property]
  (let [face (om/props comp)
        child-no    (if (= :face/foreground face-property) 0 1)
        coordinates (-> (face-option-elm (dom/node comp) child-no)
                      gdom/getElement
                      gstyle/getPosition
                      cljs-coordinates)]
    (om/transact! comp `[(palette-picker/update
                           {:palette-picker/active-face          ~face
                            :palette-picker/active-face-property ~face-property
                            :palette-picker/coordinates          ~coordinates}) :palette-picker/coordinates])))

(defn face-color [comp face-property]
  (let [{:keys [db/id color/rgb color/name] :as color}  (get (om/props comp) face-property)
        {:keys [editing?] :as state} (om/get-state comp)]

    (dom/div
      #js {:style     #js {:backgroundColor rgb}
           :tabIndex  "0"
           :className "color color-trigger"
           :onClick   #(faceColorClick % comp face-property)
           :onBlur    #(om/transact! comp `[(palette-picker/update
                                              {:palette-picker/active-face nil
                                               :palette-picker/active-face-property nil
                                               :palette-picker/coordinates nil}) :palette-picker/coordinates])})))

(defui Face
  static om/Ident
  (ident [this {:keys [face/name]}]
    [:faces/by-name name])

  static om/IQuery
  (query [this]
    `[:db/id
      :face/name
      {:face/background ~(om/get-query Color)}
      {:face/foreground ~(om/get-query Color)}])


  Object
  (componentDidUpdate [this prev-props prev-state]
    (let [{:keys [face/name
                  face/background
                  face/foreground]}  (om/props this)

          bg-color (:color/rgb background)
          fg-color (:color/rgb foreground)]
      (util/update-code-css name "color"      fg-color)
      (util/update-code-css name "background" bg-color)))

  (render [this]
    (let [{id         :db/id
           face-name  :face/name
           bg         :face/background
           fg         :face/foreground
           :as props} (om/props this)]

      (dom/div #js {:className "face-container"}
        (dom/div #js {:className "face"}
          (dom/span #js {:className "face-name"} (clojure.core/name face-name))
          (face-color this :face/foreground)
          (face-color this :face/background))))))

(def face (om/factory Face {:keyfn :db/id}))

(defui FaceEditor
  Object
  (render [this]
    (let [{pp :palette-picker
           faces :faces/list :as props} (om/props this)]
      (dom/div #js {:id "faces-editor"
                    :className "widget"
                    :style #js {:position "relative"}}
        (dom/h5 nil "Faces Editor")
        (apply dom/div nil (map face faces))
        (palette-picker pp)))))

(def face-editor (om/factory FaceEditor))
