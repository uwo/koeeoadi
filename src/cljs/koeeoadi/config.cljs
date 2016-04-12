(ns koeeoadi.config
  (:require [om.next :as om]
            [koeeoadi.code :refer [code]]
            [koeeoadi.themes :refer [themes]]))

(def project-version "0.0.1")
(def initial-code "c")
(def initial-theme "solarized-dark")

(def app-state
  (merge {:widget/active   :theme 
          :theme/version   project-version
          :palette-widget  {}
          :code/name       initial-code
          :code-background [:faces/by-name "background"]
          :theme/map       themes
          :code/map        code
          :user-faces/list []}  (get themes initial-theme)))





