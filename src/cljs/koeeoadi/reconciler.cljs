(ns koeeoadi.reconciler
  (:require [om.next :as om]

            [koeeoadi.config :refer [app-state]]
            [koeeoadi.parser :refer [parser]]))

(defonce reconciler
  (om/reconciler
    {:normalize true
     :state     app-state
     :parser    parser}))
