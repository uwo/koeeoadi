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
    (om/transact! comp `[(palette-picker/update
                           {:palette-picker/active-face ~face
                            :palette-picker/coordinates ~coordinates}) :palette-picker/coordinates])))

(defn face-color [comp]
  (let [{:keys [color/id color/rgb] :as color}  (get (om/props comp) :face/color)
        {:keys [editing?] :as state} (om/get-state comp)]
    (dom/div
      #js {:className "color color-trigger"
           :onBlur    #(om/transact! comp `[(palette-picker/update
                                              {:palette-picker/active-face          nil
                                               :palette-picker/active-face-property nil
                                               :palette-picker/coordinates          nil}) :palette-picker/coordinates])
           :onClick   #(faceColorClick comp %)
           :style     #js {:backgroundColor rgb}
           :tabIndex  "0"})))

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
          color (:color/rgb color)]))

  (render [this]
    (let [{id         :db/id
           face-name  :face/name
           :as props} (om/props this)]
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
