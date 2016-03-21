(ns komokio.core
  (:require [goog.array :as garray]
            [goog.dom :as gdom]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [clojure.string :as string]
            [cljs.pprint :as pprint]

            [devtools.core :as devtools]

            [komokio.config :as config]
            [komokio.parser :refer [parser]]
            [komokio.components.codedisplay :refer [code-display CodeChunk]]
            [komokio.components.faceeditor :refer [Face]]
            [komokio.components.palettepicker :refer [PalettePicker palette-picker]]
            [komokio.components.palette :refer [Color]]))

;; TODO separate out this dev stuff
(enable-console-print!)
                                        ; this enables additional features, :custom-formatters is enabled by default
(devtools/enable-feature! :sanity-hints :dirac)
(devtools/install!)

(defui Root
  static om/IQueryParams
  (params [this]
    {:face (om/get-query Face)
     :colors (om/get-query Color)})

  static om/IQuery
  (query [this]
    [{:faces/list (om/get-query Face)}
     {:colors/list (om/get-query Color)}
     {:palette-picker (om/get-query PalettePicker)}
     {:code-chunks/list (om/get-query CodeChunk)}])

  Object
  (render [this]
    (println "rendering core")
    (let [props (om/props this)]
      (dom/div nil
        (palette-picker props)
        (code-display props)))))

(defonce reconciler
  (om/reconciler
    {:normalize true
     :state config/app-state
     :parser parser}))

(om/add-root! reconciler
  Root (gdom/getElement "app"))
