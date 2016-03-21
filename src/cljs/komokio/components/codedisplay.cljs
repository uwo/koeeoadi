(ns komokio.components.codedisplay
  (:require [goog.dom :as gdom]
            [goog.dom.classes :as gclasses]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [komokio.util :as util]
            [komokio.config :refer [code-clojure]]))

(defn cljs-coordinates [coords]
  {:x (aget coords "y")
   :y (aget coords "x")})

(defn handleCodeClick [comp face-property]
  (let [face        (:face (om/props comp))
        coordinates (-> (dom/node comp)
                      gdom/getElement
                      gstyle/getPosition
                      cljs-coordinates)]
    (om/transact! comp `[(palette-picker/update
                           {:palette-picker/active-face          ~face
                            :palette-picker/active-face-property ~face-property
                            :palette-picker/coordinates          ~coordinates}) :palette-picker/coordinates])))

(defui CodeChunk
  static om/Ident
  (ident [this {:keys [line-chunk]}]
    ;; dirty ident but it works for our purposes
    [:code-chunks/by-linechunk line-chunk])

  static om/IQuery
  (query [this]
    [:face
     :line-chunk
     :string])

  Object
  (render [this]
    (let [{:keys [face line-chunk string]} (om/props this)
          {:keys [face/name]} face
          {fg :color/rgb} (:face/foreground face)
          {bg :color/rgb} (:face/background face)]
      (dom/span #js {:className    (str util/code-class " " (util/code-face-class name))
                     :onClick      #(handleCodeClick this :face/foreground)
                     :tabIndex 0
                     :onBlur       #(om/transact! this `[(palette-picker/update
                                                           {:palette-picker/active-face          nil
                                                            :palette-picker/active-face-property nil
                                                            :palette-picker/coordinates          nil}) :palette-picker/coordinates])
                     ;; :onMouseOver  (fn [e] (util/update-code-other-elements name #(gclasses/add % "code-temp-minimize")))
                     ;; :onMouseLeave (fn [e] (util/update-code-other-elements name #(gclasses/remove % "code-temp-minimize")))
                     :style #js    {:backgroundColor (if bg bg "transparent")
                                    :color           (if fg fg "black")}} string))))

(def code-chunk (om/factory CodeChunk {:keyfn :line-chunk}))

(defn code-line [line]
  (let [code-chunks (sort-by :line-chunk line)]
    (apply dom/div #js {:className "code-line"}
      (map code-chunk code-chunks))))

(defn code-display [props]
  (println "rerendering code")
  (let [code-chunks (:code-chunks/list props)
        code-lines (sort-by first (sort-by first
                                    (group-by #(.floor js/Math (/ (:line-chunk %) 1000))
                                      code-chunks)))]
    (apply dom/code #js {:id "code-display"}
      (map #(code-line (last %)) code-lines))))
