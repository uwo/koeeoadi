(ns komokio.components.sidebar
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]

            [komokio.components.appinfo :refer [app-info]]
            [komokio.components.faceeditor :refer [Face]]
            [komokio.components.palette :refer [palette Palette]]))

(defui Sidebar
  static om/IQuery
  (query [this]
    [{:palette (om/get-query Palette)}
     {:faces [{:faces/list (om/get-query Face)}]}])

  Object
  (render [this]
    (println "printin sidebar")
    (.log js/console (om/props this))
    (let [palette-data (:palette (om/props this))]
      (println "palette-data")
      (println palette-data)
      (dom/div #js {:id "sidebar"}
        (app-info)
        (palette palette-data)
        ;;(face-editor props)
        ))))

(def sidebar (om/factory Sidebar))
