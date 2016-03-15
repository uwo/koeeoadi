(ns komokio.core
  (:require [goog.array :as garray]
            [goog.dom :as gdom]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [clojure.string :as string]
            [cljs.pprint :as pprint]

            [komokio.config :as config]
            [komokio.parser :refer [parser]]
            [komokio.components.sidebar :refer [sidebar]]
            [komokio.components.codedisplay :refer [code-display]]
            [komokio.components.faceeditor :refer [Face]]
            [komokio.components.palette :refer [Color]]))

(enable-console-print!)

(defui Root
  static om/IQueryParams
  (params [this]
    {:face (om/get-query Face)
     :colors (om/get-query Color)})

  static om/IQuery
  (query [this]
    '[{:faces/list ?face}
      {:colors/list ?colors}])

  Object
  (componentDidMount [this]
    (om/set-state! this {}))

  (render [this]
    (let [props (om/props this)]
      (dom/div nil
        (sidebar props)
        (code-display props)))))

(def reconciler
  (om/reconciler
    {:normalize true
     :state config/app-state
     :parser parser}))

(om/add-root! reconciler
  Root (gdom/getElement "app"))
