(ns komokio.components.faceeditor
  (:require [goog.dom :as gdom]
            [goog.dom.classes :as gclasses]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]
            [cljs.core.async :refer [timeout mult tap untap put! chan <! >!]]

            [komokio.components.palette :refer [Color]]
            [komokio.util :as util]))

;; TODO delete this when I move palette picker to it's own component
(declare Face)

(defn faceColorClick [comp e]
  (let [face (om/props comp)
        coordinates (-> (gstyle/getClientPosition e)
                      util/cljs-coordinates)]
    (println "in face color click")
    (.log js/console face)
    (om/transact! comp `[(palette-picker/update
                           {:palette-picker/active-face ~face
                            :palette-picker/coordinates ~coordinates}) :palette-picker])))

(defn face-color [comp]
  (let [{:keys [color/id color/rgb] :as color}  (get (om/props comp) :face/color)
        {:keys [editing?] :as state} (om/get-state comp)]
    (dom/div
      #js {:style     #js {:backgroundColor rgb}
           :onClick   #(faceColorClick comp %)
           :tabIndex  "0"
           :className "color color-trigger"})))

(defui Face
  static om/Ident
  (ident [this {:keys [face/name]}]
    [:faces/by-name name])

  static om/IQuery
  (query [this]
    `[:db/id
      :face/name
      {:face/color ~(om/get-query Color)}])

  Object
  (componentDidUpdate [this prev-props prev-state]
    (let [{:keys [face/name
                  face/color]}  (om/props this)

          color (:color/rgb color)]
      ;;(util/update-code-css name "color"      fg-color)
      ;;(util/update-code-css name "color" bg-color)
      )
    )

  (render [this]
    (let [{id         :db/id
           face-name  :face/name
           :as props} (om/props this)]
      (when (= face-name "doc")
        (.log js/console props))
      (dom/div #js {:className "face"}
        (face-color this)
        (dom/span #js {:className "face-name"} (clojure.core/name face-name))))))

(def face (om/factory Face {:keyfn :db/id}))

(defui FaceEditor
  static om/IQuery
  (query [this]
    [{:faces/list (om/get-query Face)}])

  Object
  (render [this]
    (let [{:keys [:faces/list]} (om/props this)]
      (dom/div #js {:id "face-editor"
                    :className "widget"}

        (dom/h5 nil "Faces")
        (map face list)))))

(def face-editor (om/factory FaceEditor))
