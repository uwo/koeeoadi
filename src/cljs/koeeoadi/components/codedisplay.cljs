(ns koeeoadi.components.codedisplay
  (:require [goog.dom :as gdom]
            [goog.style :as gstyle] [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [koeeoadi.util :as util]
            [koeeoadi.reconciler :refer [reconciler]]
            [koeeoadi.components.palettepicker :refer [palette-picker-comp]]))

(defn style-maybe [prop style]
  (if prop style {}))


(defn face-styles [{:keys [face/color-fg face/color-bg face/bold face/italic face/underline]}]
  (clj->js
    (merge
      {:color                (:color/rgb color-fg)}
      {:backgroundColor      (:color/rgb color-bg)}
      (style-maybe bold      {:fontWeight "bold"})
      (style-maybe italic    {:fontStyle "italic"})
      (style-maybe underline {:textDecoration "underline"}))))

(defui CodeChunk
  static om/Ident
  (ident [this props]
    [:code-chunks/by-line-chunk (:code-chunk/line-chunk props)])

  static om/IQuery
  (query [this]
    ;; TODO why can't I query Face here?
    [{:code-chunk/face [:face/id
                        :face/name
                        :face/bold
                        :face/italic
                        :face/underline
                        {:face/color-fg [:color/id :color/rgb]}
                        {:face/color-bg [:color/id :color/rgb]}
                        ]}
     :code-chunk/line-chunk
     :code-chunk/string])

  Object
  (render [this]
    (let [{:keys [code-chunk/face
                  code-chunk/line-chunk
                  code-chunk/string]}   (om/props this)
          {:keys [face/name]}           face]
      (dom/span
        #js {:className    (str util/code-class " " (util/code-face-class name))
             :onBlur       #(util/palette-picker-hide (palette-picker-comp))
             :onClick      #(util/palette-picker-show (palette-picker-comp) face :face/color-fg %)
             :style        (face-styles face)
             :tabIndex     0}
        string))))

(def code-chunk (om/factory CodeChunk {:keyfn :code-chunk/line-chunk}))

(defn code-line [line]
  (let [code-chunks (sort-by :line-chunk line)]
    (apply dom/div #js {:className "code-line"}
      (map code-chunk code-chunks))))

(defn group-lines [code-chunks]
  (sort-by first (sort-by first
                   (group-by #(.floor js/Math (/ (:code-chunk/line-chunk %) 1000))
                     code-chunks))))

(defui CodeDisplay
  static om/IQuery
  (query [this]
    [{:code-chunks/list (om/get-query CodeChunk)}
     {:code-background  [:face/id
                         :face/name
                         {:face/color-fg [:color/id
                                          :color/name
                                          :color/rgb]}]}])

  Object
  (render [this]
    (let [{:keys [code-background
                  code-chunks/list]} (om/props this)
          code-lines                 (group-lines list)
          ]
      (apply dom/code
        #js {:className (util/code-face-class "background")
             :id        "code-display"
             :onBlur    #(util/palette-picker-hide (palette-picker-comp))
             :onClick   #(util/palette-picker-show (palette-picker-comp) code-background :face/color-fg %)
             :style     #js {:backgroundColor (get-in code-background [:face/color-fg :color/rgb])}
             :tabIndex  0}
        (map #(code-line (last %)) code-lines)))))

(def code-display (om/factory CodeDisplay))

(defn code-display-comp []
  (om/class->any reconciler CodeDisplay))
