(ns komokio.components.sidebar
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]

            [komokio.components.appinfo :refer [app-info]]
            [komokio.components.faceeditor :refer [FaceEditor face-editor]]
            [komokio.components.palette :refer [palette Palette]]))

(defui Sidebar
  static om/IQuery
  (query [this]
    [{:palette (om/get-query Palette)}
     {:faces (om/get-query FaceEditor)}])

  Object
  (render [this]
    (let [{faces-data :faces
           palette-data :palette}  (om/props this)]
      (dom/div #js {:id "sidebar"}
        (app-info)
        (palette palette-data)
        (face-editor faces-data)
        ))))

(def sidebar (om/factory Sidebar))
