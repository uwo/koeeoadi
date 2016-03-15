(ns komokio.components.faceeditor
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [komokio.util :as util]))

(defn update-face-color-style [comp]
  (let [props (om/props comp)
        rgb  (:color/rgb props)
        css-updater (:css-updater (om/get-computed props))]
    (css-updater rgb)))

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
    (update-face-color-style this))

  (render [this]
    (let [{:keys [color/rgb] :as props} (om/props this)]
      (dom/div #js {:style #js {:backgroundColor rgb}
                    :className "swatch"
                    :onClick #(println "showing tooltip")} ""))))

(def face-color (om/factory FaceColor))

(defui Face
  static om/Ident
  (ident [this {:keys [face/name]}]
    [:faces/by-name name])

  static om/IQueryParams
  (params [this]
    {:face-color (om/get-query FaceColor)})

  static om/IQuery
  (query [this]
    `[:db/id
      :face/name
      {:face/background ~(om/get-query FaceColor)}
      {:face/foreground ~(om/get-query FaceColor)}])

  Object
  (render [this]
    (let [{id :db/id face-name :face/name
           bg :face/background fg :face/foreground
           :as props} (om/props this)

          cssUpdater (fn [css-property color-rgb]
                       (util/update-code-css face-name css-property color-rgb))

          bg-computed (om/computed
                        bg
                        {:css-updater (partial cssUpdater "background-color")})

          fg-computed (om/computed
                        fg
                        {:css-updater (partial cssUpdater "color")})]
      (dom/div #js {:className "face"}
        (dom/span #js {:className "face-name"} (clojure.core/name face-name))
        (face-color fg-computed)
        (face-color bg-computed)))))

(def face (om/factory Face {:keyfn :db/id}))

(defui FaceEditor
  Object
  (render [this]
    (let [{:keys [faces/list]} (om/props this)]
      (dom/div #js {:id "faces-editor"}
        (dom/h3 nil "Faces Editor")
        (apply dom/div nil (map face list))))))

(def face-editor (om/factory FaceEditor))
