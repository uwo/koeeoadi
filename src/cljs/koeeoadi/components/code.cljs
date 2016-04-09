(ns koeeoadi.components.code
  (:require [goog.dom :as gdom]
            [goog.style :as gstyle] [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [koeeoadi.util :as util]
            [koeeoadi.reconciler :refer [reconciler]]
            [koeeoadi.components.colorpicker :refer [color-picker-comp]]))

(defn style-maybe [prop style]
  (if prop style {}))


(defn face-styles [{:keys [face/color-fg face/color-bg face/bold face/italic face/underline]}]
  (clj->js
    (merge
      {:color                (:color/hex color-fg)}
      {:backgroundColor      (:color/hex color-bg)}
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

                        {:face/color-fg [:color/id :color/hex]}
                        {:face/color-bg [:color/id :color/hex]}
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
             :onBlur       #(util/color-picker-hide (color-picker-comp))
             :onClick      #(util/color-picker-show (color-picker-comp) face :face/color-fg %)
             :style        (face-styles face)
             :tabIndex     0}
        string))))

(def code-chunk (om/factory CodeChunk {:keyfn :code-chunk/line-chunk}))

(defn code-line [line]
  (apply dom/div #js {:className "code-line"}
    (map code-chunk line)))

(defn group-lines [code-chunks]
  (sort-by first (group-by #(.floor js/Math (/ (:code-chunk/line-chunk %) 1000))
                   code-chunks)))


(defui Code
  static om/IQuery
  (query [this]
    [{:code-chunks/list (om/get-query CodeChunk)}
     {:code-background  [:face/id
                         :face/name
                         {:face/color-bg [:color/id
                                          :color/name
                                          :color/hex]}]}])

  Object
  (render [this]
    (let [{:keys [code-background
                  code-chunks/list]} (om/props this)
          code-lines                 (group-lines list)]
      (apply dom/code
        #js {:className (util/code-face-class "background")
             :id        "code"
             :onBlur    #(util/color-picker-hide (color-picker-comp))
             :onClick   #(util/color-picker-show (color-picker-comp) code-background :face/color-bg %)
             :style     #js {:backgroundColor (get-in code-background [:face/color-bg :color/hex])}
             :tabIndex  0}
        (map #(code-line (last %)) code-lines)))))

(def code (om/factory Code))

(defn code-comp []
  (om/class->any reconciler Code))
