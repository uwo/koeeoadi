(ns koeeoadi.components.appinfo
  (:require [om.dom :as dom]))

(defn app-info []
  (dom/div #js {:className "widget"
                :id        "app-info"}
    (dom/h2 #js {:id "app-title"} "KOEEOADI")
    (dom/div nil "A theme creator for Emacs")))
