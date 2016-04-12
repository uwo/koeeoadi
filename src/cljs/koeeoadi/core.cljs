(ns koeeoadi.core
  (:require [goog.array :as garray]
            [goog.dom :refer [getElement]]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]
            [devtools.core :as devtools]
            [koeeoadi.components.title :refer [title]]
            [koeeoadi.components.history :refer [History history]]
            [koeeoadi.components.theme :refer [Theme theme]]
            [koeeoadi.components.language :refer [Language language]]
            [koeeoadi.components.faces :refer [Faces faces Face]]
            [koeeoadi.components.help :refer [help]]
            [koeeoadi.components.palette :refer [Palette palette]]
            [koeeoadi.components.colorpicker :refer [ColorPicker color-picker]]
            [koeeoadi.components.code :refer [Code code CodeChunk]]
            [koeeoadi.components.userfaces :refer [UserFaces user-faces UserFace]]
            [koeeoadi.reconciler :refer [reconciler]]
            [koeeoadi.util :as util]))

;; TODO separate out this dev stuff
(enable-console-print!)
                                        ; this enables additional features, :custom-formatters is enabled by default
(devtools/enable-feature! :sanity-hints :dirac)
(devtools/install!)

(defn switch-widget [comp widget]
  (om/transact! comp `[(state/merge {:widget/active ~widget
                                     :mutate/name :history/ignore})]))

(defn switcher [comp widget]
  (let [widget-active (:widget/active (om/props comp))]
    (dom/button #js {:className "switcher"
                     :onClick #(switch-widget comp widget)}
      (clojure.core/name widget))))

(defn widget-switchers [comp]
  (dom/div #js {:id "widget-switchers" :className "widget widget-active"}
    (dom/label nil "Choose a menu:")
    (switcher comp :theme)
    (switcher comp :language)
    (switcher comp :palette)
    (switcher comp :faces)
    ;; (history-button (history-comp) :undo) "Undo"
    ;; (history-button (history-comp) :redo) "Redo"
    )
  )

(defui Root
  static om/IQuery
  (query [this]
    [:widget/active
     {:palette-widget/active-color [:color/id :color/hex]}
     {:code             (om/get-query Code)}
     {:color-picker     (om/get-query ColorPicker)}
     {:colors/list      util/shared-color-query}
     {:faces            (om/get-query Faces)}
     {:faces/list       util/shared-face-query}
     {:language         (om/get-query Language)}
     {:history          (om/get-query History)}
     {:palette          (om/get-query Palette)}
     {:theme            (om/get-query Theme)}
     {:user-faces       (om/get-query UserFaces)}
     {:user-faces/list  util/shared-face-query}])

  Object
  (render [this]
    (let [{color-picker-data :color-picker
           theme-data        :theme
           code-picker-data  :code-picker
           code-data         :code
           palette-data      :palette
           faces-data        :faces
           language-data     :language
           history-data      :history
           user-faces-data   :user-faces :as props} (om/props this)]
      (dom/div #js {:id "root" }
        (dom/div #js {:className "sidebar" :id "sidebar-left"}
          (title this)
          (widget-switchers this)
          (theme theme-data)
          (language language-data))
        (code code-data)
        (history history-data)
        (dom/div #js {:className "sidebar" :id "sidebar-right"}
          (palette palette-data)
          (faces faces-data))
        (user-faces user-faces-data)
        (color-picker color-picker-data)
        (help this)))))

(om/add-root! reconciler
  Root (getElement "app"))
