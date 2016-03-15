(ns komokio.components.appinfo
  (:require [om.dom :as dom]))

(defn app-info []
  (dom/div #js {:id "app-info"}
    (dom/h1 nil "KOMOKIO!")))
