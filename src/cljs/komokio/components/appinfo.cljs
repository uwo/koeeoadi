(ns komokio.components.appinfo
  (:require [om.dom :as dom]))

(defn app-info []
  (dom/div #js {:id "app-info"
                :className "widget"}
    (dom/h1 nil "KOMOKIO!")
    (dom/div nil "A theme creator for your favorite text editor")
    (dom/div nil "v0.0.1")))
