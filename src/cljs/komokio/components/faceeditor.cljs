(ns komokio.components.faceeditor
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [goog.dom :as gdom]
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
    (let [{:keys [color/rgb]} (om/props this)]
      (dom/div #js {:className "color color-option"
                    :style #js {:backgroundColor rgb}}
        ""))))

(def color-option (om/factory ColorOption {:keyfn :db/id}))

(defui PalettePicker
  Object
  (componentWillMount [this]
    (let [ch (:palette-picker-chan (om/get-computed this))]
      (go-loop [f (<! ch)]
        (println "signal received")
        (recur (<! ch)))))

  (render [this]
    (let [color-options (:color-options (om/props this))]
      (apply dom/div #js {:className"palette-picker"}
        (map color-option color-options)))))

(def palette-picker (om/factory PalettePicker))


(defn faceColorClick [e comp]
  (let [editing? (:editing? (om/props comp))]
    (om/update-state! comp assoc :editing? (not editing?))))

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
    ;; TODO would be better to have dedicated components in code display to render face styles
    (update-face-color-style this)
    (let [{:keys [editing?] :as state} (om/get-state this)
          {ch :palette-picker-chan} (om/get-computed this)]
      (when editing? (put! ch comp))))
  (render [this]
    (let [{:keys [color/rgb] :as props} (om/props this)
          {:keys [editing?] :as state} (om/get-state this)]
      (dom/div #js {:style #js {:backgroundColor rgb}
                    :className "color color-trigger"
                    :onClick #(faceColorClick % this)} ""))))

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
    (let [{id :db/id
           face-name :face/name
           bg :face/background
           fg :face/foreground
           :as props} (om/props this)
          computed (om/get-computed this)
          cssUpdater (fn [css-property color-rgb]
                       (util/update-code-css face-name css-property color-rgb))

          bg-computed (om/computed
                        bg
                        (merge computed
                          {:css-updater (partial cssUpdater "background-color")}))

          fg-computed (om/computed
                        fg
                        (merge computed
                          {:css-updater (partial cssUpdater "color")}))]
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

          faces-computed         (map #(om/computed % {:palette-picker-chan palette-picker-chan}) faces)
          color-options-computed (om/computed {:color-options color-options} {:palette-picker-chan palette-picker-chan})]
      (dom/div #js {:id "faces-editor"
                    :className "widget"}
        (dom/h3 nil "Faces Editor")
        (apply dom/div nil (map face faces-computed))
        (palette-picker color-options-computed)))))

(def face-editor (om/factory FaceEditor))
