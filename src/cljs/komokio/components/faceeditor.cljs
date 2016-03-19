(ns komokio.components.faceeditor
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [goog.dom :as gdom]
            [goog.dom.classes :as gclasses]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.core.async :refer [timeout mult tap untap put! chan <! >!]]

            [komokio.components.palette :refer [Color]]
            [komokio.util :as util]))

(def palette-picker-chan (chan))

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
         :onMouseEnter #(colorHoverHandler rgb) ;;#(colorHoverHandler rgb)
         :onMouseLeave #(colorHoverHandler nil)
         :onMouseDown  #(do (.preventDefault %)
                            (colorClickHandler (om/props this)))
         :style        #js {:backgroundColor rgb
                            ;;:border (str "0.1px solid " rgb)
                            }}))))

(def color-option (om/factory ColorOption {:keyfn :db/id}))

(defui PalettePicker
  Object
  (componentWillMount [this]
    (let [ch (:palette-picker-chan (om/get-computed this))]
      (go-loop [state (<! ch)]
        (om/set-state! this state)
        (recur (<! ch)))))

  (componentInitState [this]
    {})

  (render [this]
    (let [color-options (:color-options (om/props this))
          state         (om/get-state this)]
      (when-not (empty? state)
        (let [{:keys [face-comp color-comp coordinates css-property]} state

              {face-id     :db/id
               face-name   :face/name} (om/props face-comp)

              {color-id    :db/id
               color-name  :color/name
               color-rgb   :color/rgb} (om/props color-comp)

              colorClickHandler (fn [color]
                                  (let [{:keys [db/id face/name]} (om/props face-comp)
                                        bg-or-fg (if (= css-property "background-color") :face/background :face/foreground)]
                                    (.log js/console face-comp)
                                    (om/transact! face-comp `[(face/update {:id ~id :name ~name :bg-or-fg ~bg-or-fg :color ~color})])))
              colorHoverHandler (fn [hover-color-rgb]
                                  (if hover-color-rgb
                                    (util/update-code-css face-name css-property hover-color-rgb)
                                    (util/update-code-css face-name css-property color-rgb)))

              color-options-computed (map #(om/computed % {:colorHoverHandler colorHoverHandler
                                                           :colorClickHandler colorClickHandler}) color-options)]

          (.log js/console state)
          (apply
            dom/div
            #js {:id    "palette-picker"
                 :style #js {:position "absolute"
                             :top      (:x coordinates)
                             :left     (:y coordinates)
                             }}
            (map color-option color-options-computed)))))))

(def palette-picker (om/factory PalettePicker))

(defn faceColorClick [e comp]
  (let [editing? (:editing? (om/props comp))]
    (om/update-state! comp assoc :editing? (not editing?))))

(defn cljs-coordinates [coords]
  {:x (aget coords "y")
   :y (aget coords "x")})

(defui FaceColor
  static om/Ident
  (ident [this {:keys [color/name]}]
    [:colors/by-name name])

  static om/IQuery
  (query [this]
    ;; TODO use shared color query
    [:db/id :color/name :color/rgb])

  Object
  (componentDidUpdate [this prev-props prev-state]
    (when (:css-updater  (om/get-computed this))
      (update-face-color-style this))
    (let [{:keys [editing?] :as state}  (om/get-state this)
          {:keys [signalPalettePicker]} (om/get-computed this)
          coordinates (cljs-coordinates (gstyle/getPosition (gdom/getElement (dom/node this))))
          palette-picker-state-fragment {:coordinates coordinates :color-comp this}]
      (when signalPalettePicker
        (if editing?
          (signalPalettePicker palette-picker-state-fragment)
          (signalPalettePicker {})))))
  (render [this]
    (let [{:keys [color/rgb] :as props} (om/props this)
          {:keys [editing?] :as state} (om/get-state this)]

      (dom/div
        #js {:style     #js {:backgroundColor rgb}
             :tabIndex  "0"
             :className "color color-trigger"
             :onClick   #(faceColorClick % this)
             :onBlur    #(om/set-state! this {})}))))

(def face-color (om/factory FaceColor))

(defui Face
  static om/Ident
  (ident [this {:keys [face/name]}]
    [:faces/by-name name])

  static om/IQuery
  (query [this]
    `[:db/id
      :face/name
      {:face/background ~(om/get-query FaceColor)}
      {:face/foreground ~(om/get-query FaceColor)}])

  Object
  (render [this]
    (let [{id         :db/id
           face-name  :face/name
           bg         :face/background
           fg         :face/foreground
           :as props} (om/props this)
          {:keys [palette-picker-chan] :as computed} (om/get-computed this)
          signalPalettePicker (fn [css-property palette-picker-state-fragment]
                                (if-not (empty? palette-picker-state-fragment)
                                  ;; beginning editing: send palettepicker the data it needs to do its thing
                                  ;; this includes the color selection and the face associated with the selection
                                  (let [palette-picker-state (merge palette-picker-state-fragment
                                                               {:face-comp this :css-property css-property})]
                                    (put! palette-picker-chan (merge palette-picker-state)))
                                  ;; done editing: send palette-picker nil data so it will "turn off"
                                  (put! palette-picker-chan {})))
          cssUpdater (fn [css-property color-rgb]
                       (util/update-code-css face-name css-property color-rgb))

          bg-computed (om/computed
                        bg
                        (merge computed
                          {:css-updater         (partial cssUpdater "background-color")
                           :signalPalettePicker (partial signalPalettePicker "background-color")}))

          fg-computed (om/computed
                        fg
                        (merge computed
                          {:css-updater         (partial cssUpdater "color")
                           :signalPalettePicker (partial signalPalettePicker "color")}))]

      (dom/div #js {:className "face-container"}
        (dom/div #js {:className "face"}
          (dom/span #js {:className "face-name"} (clojure.core/name face-name))
          (face-color fg-computed)
          (face-color bg-computed))))))

(def face (om/factory Face {:keyfn :db/id}))

(defui FaceEditor
  Object
  (render [this]
    (let [{color-options :color-options/list
           faces :faces/list :as props} (om/props this)
          width  (* 20 (count color-options))
          faces-computed         (map #(om/computed % {:palette-picker-chan palette-picker-chan}) faces)
          color-options-computed (om/computed {:color-options color-options} {:palette-picker-chan palette-picker-chan})]

      (dom/div #js {:id "faces-editor"
                    :className "widget"
                    :style #js {:position "relative"}}
        (dom/h5 nil "Faces Editor")
        (apply dom/div nil (map face faces-computed))
        (palette-picker color-options-computed)))))

(def face-editor (om/factory FaceEditor))
