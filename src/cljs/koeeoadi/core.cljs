(ns koeeoadi.core
  (:require [goog.array :as garray]
            [goog.dom :refer [getElement]]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]
            [devtools.core :as devtools]
            [koeeoadi.components.title :refer [title]]
            [koeeoadi.components.history :refer [history]]
            [koeeoadi.components.theme :refer [Theme theme]]
            [koeeoadi.components.language :refer [Language language]]
            [koeeoadi.components.faces :refer [Faces faces Face]]
            [koeeoadi.components.help :refer [help]]
            [koeeoadi.components.palette :refer [Palette palette]]
            [koeeoadi.components.colorpicker :refer [ColorPicker color-picker]]
            [koeeoadi.components.code :refer [Code code CodeChunk]]
            [koeeoadi.components.palette :refer [Color]]
            [koeeoadi.components.userfaces :refer [UserFaces user-faces UserFace]]
            [koeeoadi.reconciler :refer [reconciler]]))

;; ;; TODO separate out this dev stuff
;; (enable-console-print!)
;; ; this enables additional features, :custom-formatters is enabled by default
;; (devtools/enable-feature! :sanity-hints :dirac)
;; (devtools/install!)

(defui Root
  static om/IQuery
  (query [this]
    [:code-background
     :code/map
     :code/name
     :palette-widget
     :palette-widget/active-color
     :palette-widget/closure-comp
     :palette-widget/face-classes-by-color-type
     :theme/map
     :theme/name
     {:code             (om/get-query Code)}
     {:code-chunks/list (om/get-query CodeChunk)}
     {:color-picker     (om/get-query ColorPicker)}
     {:colors/list      (om/get-query Color)}
     {:faces            (om/get-query Faces)}
     {:faces/list       (om/get-query Face)}
     {:language         (om/get-query Language)}
     {:palette          (om/get-query Palette)}
     {:theme            (om/get-query Theme)}
     {:user-faces       (om/get-query UserFaces)}
     {:user-faces/list  (om/get-query UserFace)}])

  Object
  (render [this]
    (let [{color-picker-data :color-picker
           theme-data        :theme
           code-picker-data  :code-picker
           code-data         :code
           palette-data      :palette
           faces-data        :faces
           language-data     :language
           user-faces-data   :user-faces :as props} (om/props this)]
      (dom/div #js {:id "root" }
        (dom/div #js {:className "sidebar" :id "sidebar-left"}
          (title this)
          (theme theme-data)
          (language language-data)
          (history))
        (code code-data)
        (dom/div #js {:className "sidebar" :id "sidebar-right"}
          (palette palette-data)
          (faces faces-data))
        (user-faces user-faces-data)
        (color-picker color-picker-data)
        (help this)))))


(om/add-root! reconciler
  Root (getElement "app"))
