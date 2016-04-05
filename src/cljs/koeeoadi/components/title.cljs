(ns koeeoadi.components.title
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defn title [comp]
  (let [{:keys [show-help]} (om/get-state comp)]
    (dom/div #js {:className "widget"
                  :id        "app-info"}
      (dom/h2 #js {:id "title"} "KOEEOADI")
      (dom/p nil "A theme creator for Emacs and Vim")
      (dom/div #js {:className "row"}
        (dom/div #js {:className "column one-third"}
          (dom/a #js {:href "https://twitter.com/sean_irbyy"
                      :target "_blank"} "Twitter"))

        (dom/div #js {:className "column one-third"}
          (dom/a #js {:href "https://github.com/seanirby/koeeoadi.git"
                      :target "_blank"} "Github"))

        (dom/div #js {:className "column one-third"}
          (dom/a #js {:href "#"
                      :onClick #(om/update-state! comp assoc :show-help true)} "Help"))))))
