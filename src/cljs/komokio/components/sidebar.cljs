(ns komokio.components.sidebar
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [komokio.components.appinfo :refer [app-info]]
            [komokio.components.faceeditor :refer [face-editor]]
            [komokio.components.palette :refer [palette]]))

(defui Sidebar
  Object
  (render [this]
    (let [{faces :faces/list
           colors :colors/list
           :as props} (om/props this)]
      (dom/div #js {:id "sidebar"}
        (app-info)
        (palette props)
        (face-editor props)))))

(def sidebar (om/factory Sidebar))
