(ns komokio.components.codedisplay
  (:require [goog.dom :as gdom]
            [goog.dom.classes :as gclasses]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]


            [komokio.components.palette :refer [Color]]
            [komokio.components.faceeditor :refer [Face]]
            [komokio.util :as util]
            ))

(defn cljs-coordinates [coords]
  {:x (aget coords "y")
   :y (aget coords "x")})

(defn handleCodeClick [comp face-property]
  (let [face        (:code-chunk/face (om/props comp))
        coordinates (-> (dom/node comp)
                      gstyle/getPosition
                      cljs-coordinates)]
    (om/transact! comp `[(palette-picker/update
                           {:palette-picker/active-face          ~face
                            :palette-picker/active-face-property ~face-property
                            :palette-picker/coordinates          ~coordinates}) :palette-picker/coordinates])))

(defui CodeFace
  static om/Ident
  (ident [this props]
    (:faces/by-name (:face/name props)))

  static om/IQuery
  (query [this]
    [:db/id :face/name :face/background :face/foreground]))

(defui CodeChunk
  static om/Ident
  (ident [this props]
    [:code-chunks/by-line-chunk (:code-chunk/line-chunk props)])

  static om/IQuery
  (query [this]
    ;; TODO why can't I query Face here?
    [{:code-chunk/face [:db/id :face/name
                        {:face/background [:db/id :color/name :color/rgb]}
                        {:face/foreground [:db/id :color/name :color/rgb]}]}
     :code-chunk/line-chunk
     :code-chunk/string])

  Object
  (render [this]
    (dom/div nil "testdf")
    ;;
    ;; (let [{:keys [face line-chunk string]} (om/props this)
    ;;       {:keys [face/name]} face
    ;;       {fg :color/rgb} (:face/foreground face)
    ;;       {bg :color/rgb} (:face/background face)]
    ;;   (dom/span #js {:className    (str util/code-class " " (util/code-face-class name))
    ;;                  :onClick      #(handleCodeClick this :face/foreground)
    ;;                  :tabIndex 0
    ;;                  :onBlur       #(om/transact! this `[(palette-picker/update
    ;;                                                        {:palette-picker/active-face          nil
    ;;                                                         :palette-picker/active-face-property nil
    ;;                                                         :palette-picker/coordinates          nil}) :palette-picker/coordinates])
    ;;                  ;; :onMouseOver  (fn [e] (util/update-code-other-elements name #(gclasses/add % "code-temp-minimize")))
    ;;                  ;; :onMouseLeave (fn [e] (util/update-code-other-elements name #(gclasses/remove % "code-temp-minimize")))
    ;;                  :style #js    {:backgroundColor (if bg bg "transparent")
    ;;                                 :color           (if fg fg "black")}} string))



    ))

(def code-chunk (om/factory CodeChunk {:keyfn :line-chunk}))

(defn code-line [line]
  (let [code-chunks (sort-by :line-chunk line)]
    (apply dom/div #js {:className "code-line"}
      (map code-chunk code-chunks))))

(defui CodeDisplay
  static om/IQuery
  (query [this]
    [{:code-chunks/list (om/get-query CodeChunk)}])

  Object
  (render [this]
    (dom/div nil "testdisplay")
    ;; (let [code-chunks (:code-chunks/list (om/props this))
    ;;       code-lines (sort-by first (sort-by first
    ;;                                   (group-by #(.floor js/Math (/ (:line-chunk %) 1000))
    ;;                                     code-chunks)))]
    ;;   (println "Printing code-display")
    ;;   (.log js/console code-chunks)
    ;;   (apply dom/code #js {:id "code-display"}
    ;;     (map #(code-line (last %)) code-lines)))
    ))


(def code-display (om/factory CodeDisplay))
