(ns komokio.core
  (:require [goog.array :as garray]
            [goog.dom :as gdom]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [clojure.string :as string]
            [cljs.pprint :as pprint]

            [devtools.core :as devtools]

            [komokio.themes :refer [themes]]
            [komokio.config :as config]
            [komokio.parser :refer [parser]]
            [komokio.components.widgets :refer [Widgets widgets]]
            [komokio.components.sidebar :refer [Sidebar sidebar]]
            [komokio.components.codedisplay :refer [CodeChunk]]
            [komokio.components.faceeditor :refer [Face]]
            [komokio.components.palette :refer [Color]]))

;; TODO separate out this dev stuff
(enable-console-print!)
                                        ; this enables additional features, :custom-formatters is enabled by default
(devtools/enable-feature! :sanity-hints :dirac)
(devtools/install!)

(defui Root
  static om/IQuery
  (query [this]
    [{:data [{:code-chunks/list (om/get-query CodeChunk)}
             {:colors/list (om/get-query Color)}
             {:faces/list (om/get-query Face)}]}
     {:widgets (om/get-query Widgets)}])

  Object
  (render [this]
    (widgets (:widgets (om/props this)))))

(defonce reconciler
  (om/reconciler
    {:normalize true
     :state (atom config/app-state)
     :parser parser}))

(om/add-root! reconciler
  Root (gdom/getElement "app"))
