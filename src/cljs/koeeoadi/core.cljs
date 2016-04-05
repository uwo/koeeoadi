(ns koeeoadi.core
  (:require [goog.array :as garray]
            [goog.dom :refer [getElement]]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]

            [devtools.core :as devtools]

            [koeeoadi.components.appinfo :refer [app-info]]
            [koeeoadi.components.themeactions :refer [ThemeActions theme-actions]]
            [koeeoadi.components.codepicker :refer [code-picker]]
            [koeeoadi.components.faceeditor :refer [FaceEditor face-editor Face]]
            [koeeoadi.components.helpmodal :refer [help-modal]]
            [koeeoadi.components.palette :refer [Palette palette]]
            [koeeoadi.components.palettepicker :refer [PalettePicker palette-picker]]
            [koeeoadi.components.codedisplay :refer [CodeDisplay code-display CodeChunk]]
            [koeeoadi.components.palette :refer [Color]]
            [koeeoadi.components.userfaces :refer [UserFaces user-faces UserFace]]
            [koeeoadi.reconciler :refer [reconciler]]))

;; TODO separate out this dev stuff
(enable-console-print!)
; this enables additional features, :custom-formatters is enabled by default
(devtools/enable-feature! :sanity-hints :dirac)
(devtools/install!)

(defui Root
  static om/IQuery
  (query [this]
    [:theme/name
     :theme/name-temp
     :theme/map
     {:theme-actions    (om/get-query ThemeActions)}

     {:code-chunks/list (om/get-query CodeChunk)}
     :code/map
     :code/name
     :code-background
     {:code-display     (om/get-query CodeDisplay)}

     {:faces/list       (om/get-query Face)}
     {:face-editor      (om/get-query FaceEditor)}

     {:colors/list      (om/get-query Color)}
     {:palette          (om/get-query Palette)}
     {:palette-picker   (om/get-query PalettePicker)}

     {:user-faces/list (om/get-query UserFace)}
     {:user-faces      (om/get-query UserFaces)}])

  Object
  (render [this]
    (let [{palette-picker-data :palette-picker
           theme-actions-data  :theme-actions
           code-picker-data    :code-picker
           code-display-data   :code-display
           palette-data        :palette
           face-editor-data    :face-editor
           user-faces-data     :user-faces :as props} (om/props this)]
      (dom/div nil
        (dom/div #js {:className "sidebar" :id "sidebar-left"}
          (app-info this)
          (theme-actions theme-actions-data)
          (code-picker props))
        (code-display code-display-data)
        (dom/div #js {:className "sidebar" :id "sidebar-right"}
          (palette palette-data)
          (face-editor face-editor-data))
        (user-faces user-faces-data)
        (palette-picker palette-picker-data)
        (help-modal this)))))

(om/add-root! reconciler
  Root (getElement "app"))
