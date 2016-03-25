(ns koeeoadi.components.sidebar
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]

            [koeeoadi.components.appinfo :refer [app-info]]
            [koeeoadi.components.themeactions :refer [ThemeActions theme-actions]]
            [koeeoadi.components.palette :refer [Palette palette]]
            [koeeoadi.components.faceeditor :refer [FaceEditor face-editor]]))

(defui Sidebar
  static om/IQuery
  (query [this]
    [{:theme-actions (om/get-query ThemeActions)}
     {:palette       (om/get-query Palette)}
     {:faces         (om/get-query FaceEditor)}])

  Object
  (render [this]
    (let [{theme-actions-data :theme-actions
           code-picker-data   :code-picker
           palette-data       :palette
           faces-data         :faces} (om/props this)]
      (dom/div #js {:id "sidebar-container"}
        (dom/div #js {:className "sidebar" :id "sidebar-left"}
          (app-info)
          (theme-actions theme-actions-data)
          )
        (dom/div #js {:className "sidebar" :id "sidebar-right"}
          (palette palette-data)
          (face-editor faces-data))))))

(def sidebar (om/factory Sidebar))
