(ns komokio.components.codedisplay
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [komokio.config :refer [code-clojure]]))

(defui CodeDisplay
  Object
  (componentDidMount [this]
    (let [parent (gdom/getElement "code-display")
          code (gdom/htmlToDocumentFragment code-clojure)]
      (gdom/append parent code)))

  (render [this]
    (dom/div #js {:id "code-display"} "")))

(def code-display (om/factory CodeDisplay))

