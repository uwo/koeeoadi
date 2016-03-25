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

(defn handleCodeClick [comp face face-property e]
  (let [coordinates (-> (gstyle/getClientPosition e)
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
    [:db/id :face/name :face/color :face/color]))

(defui CodeChunk
  static om/Ident
  (ident [this props]
    [:code-chunks/by-line-chunk (:code-chunk/line-chunk props)])

  static om/IQuery
  (query [this]
    ;; TODO why can't I query Face here?
    [{:code-chunk/face [:db/id :face/name
                        {:face/color [:color/id :color/rgb]}]}
     :code-chunk/line-chunk
     :code-chunk/string])

  Object
  (render [this]
    (let [{:keys [code-chunk/face code-chunk/line-chunk code-chunk/string]} (om/props this)
          {:keys [face/name]} face
          {color :color/rgb} (:face/color face)]
      (dom/span #js {:className    (str util/code-class " " (util/code-face-class name))
                     :onClick      #(handleCodeClick this face :face/color %)
                     :tabIndex 0
                     :onBlur       #(om/transact! this `[(palette-picker/update
                                                           {:palette-picker/active-face          nil
                                                            :palette-picker/active-face-property nil
                                                            :palette-picker/coordinates          nil}) :palette-picker/coordinates])
                     ;; :onMouseOver  (fn [e] (util/update-code-other-elements name #(gclasses/add % "code-temp-minimize")))
                     ;; :onMouseLeave (fn [e] (util/update-code-other-elements name #(gclasses/remove % "code-temp-minimize")))
                     :style #js    {:color color}} string))))

(def code-chunk (om/factory CodeChunk {:keyfn :code-chunk/line-chunk}))

(defn code-line [line]
  (let [code-chunks (sort-by :line-chunk line)]
    (apply dom/div #js {:className "code-line"}
      (map code-chunk code-chunks))))

(defui CodeBackground
  static om/IQuery
  (query [this]
    [:db/id
     :face/name
     {:face/color [:color/id :color/rgb]}])

  Object
  (render [this]
    (let [{:keys [:face/color]} (om/props this)]
      (dom/div #js {
                    :className (str util/code-class " " (util/code-face-class "background"))
                    :id        "code-background"
                    :onBlur    #(om/transact! this `[(palette-picker/update
                                                       {:palette-picker/active-face nil
                                                        :palette-picker/active-face-property nil
                                                        :palette-picker/coordinates nil}) :palette-picker/coordinates])
                    :onClick   #(handleCodeClick this (om/props this) :face/color %)
                    :style     #js {:backgroundColor (:color/rgb color)}
                    :tabIndex  0}))))

(def code-background (om/factory CodeBackground))

(defui CodeDisplay
  static om/IQuery
  (query [this]
    [{:code-chunks/list (om/get-query CodeChunk)}
     {:code-background (om/get-query CodeBackground)}])

  Object
  (render [this]
    (let [code-chunks (:code-chunks/list (om/props this))
          code-lines (sort-by first (sort-by first
                                      (group-by #(.floor js/Math (/ (:code-chunk/line-chunk %) 1000))
                                        code-chunks)))]
      (dom/code #js {:id "code-display"}
        (code-background (:code-background (om/props this)))
        (apply dom/code nil
          (map #(code-line (last %)) code-lines))))))


(def code-display (om/factory CodeDisplay))
