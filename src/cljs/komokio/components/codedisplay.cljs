(ns komokio.components.codedisplay
  (:require [goog.dom :as gdom]
            [goog.dom.classes :as gclasses]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [komokio.util :as util]
            [komokio.config :refer [code-clojure]]))

(defui CodeChunk
  static om/IQuery
  (query [this]
    [:face :line :chunk :string])

  Object
  (render [this]
    (let [{:keys [face line chunk string]} (om/props this)
          {:keys [face/name]} face
          {fg :color/rgb} (:face/foreground face)
          {bg :color/rgb} (:face/background face)]
      (dom/span #js {:className (str util/code-class " " (util/code-face-class name))
                     :onMouseOver  (fn [e] (util/update-code-other-elements name #(gclasses/add % "code-temp-minimize")))
                     :onMouseLeave (fn [e] (util/update-code-other-elements name #(gclasses/remove % "code-temp-minimize")))
                     :style #js {:backgroundColor (if bg bg "transparent")
                                 :color           (if fg fg "black")}} string))))

(def code-chunk (om/factory CodeChunk {:keyfn #(+ (:chunk %) (* 10 (:line %)))}))

(defn code-line [line]
  (let [code-chunks (sort-by :chunk line)]
    (apply dom/span #js {:className "code-line"}

      (map #(code-chunk %) code-chunks))))

(defui CodeDisplay
  Object
  (render [this]
    (let [code-lines (sort-by first (group-by :line (om/props this)))]
      (apply dom/code #js {:id "code-display"}
        (map #(code-line (last %)) code-lines)))))

(def code-display (om/factory CodeDisplay))
