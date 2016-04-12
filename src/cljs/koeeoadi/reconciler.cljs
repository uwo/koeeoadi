(ns koeeoadi.reconciler
  (:require [om.next :as om]
            [koeeoadi.config :refer [app-state]]
            [koeeoadi.parser :refer [parser]]))

(defonce reconciler
  (om/reconciler
    {:normalize false
     :state     app-state
     :parser    parser}))
