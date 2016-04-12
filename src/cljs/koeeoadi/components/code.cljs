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
     :code-chunk/string]))

(defn update-face [face-ref faces-by-name colors-by-id]
  (let [{:keys [face/color-bg face/color-fg] :as face} (get faces-by-name (last face-ref))]
    (assoc face
      :face/color-bg (get colors-by-id (last color-bg))
      :face/color-fg (get colors-by-id (last color-fg)))))

(defn update-code [code-chunk faces-by-name colors-by-id]
  (update code-chunk :code-chunk/face update-face faces-by-name colors-by-id))

(defn code-chunk [props]
  (let [{:keys [code-chunk/face
                code-chunk/line-chunk
                code-chunk/string]}   props
        {:keys [face/name]}           face]
    (dom/span
      #js {:className (str util/code-class " " (util/code-face-class name))
           :onBlur    #(util/color-picker-hide (color-picker-comp))
           :onClick   #(util/color-picker-show (color-picker-comp) face :face/color-fg %)
           :style     (face-styles face)
           :tabIndex  0}
      string)))

(defn code-line [line faces-by-name colors-by-id]
  (apply dom/div #js {:className "code-line"}
    (map #(code-chunk (update-code % faces-by-name colors-by-id)) line)))

(defn group-lines [code-chunks]
  (sort-by first (group-by #(.floor js/Math (/ (:code-chunk/line-chunk %) 1000))
                   code-chunks)))

(defui Code
  static om/IQuery
  (query [this]
    '[[:code/map _]
      [:code/name _]
      [:faces/by-name _]
      [:colors/by-id _]
      {:code-background  [:face/id
                          :face/name
                          {:face/color-bg [:color/id
                                           :color/name
                                           :color/hex]}]}])

  Object
  (render [this]
    (let [{code-background :code-background
           code-name :code/name
           faces-by-name :faces/by-name
           colors-by-id :colors/by-id
           code-map :code/map} (om/props this)
          code-lines (group-lines (get-in code-map [code-name :code-chunks/list]))]
      (apply dom/code
        #js {:className (util/code-face-class "background")
             :id        "code"
             :onBlur    #(util/color-picker-hide (color-picker-comp))
             :onClick   #(util/color-picker-show (color-picker-comp) code-background :face/color-bg %)
             :style     #js {:backgroundColor (get-in code-background [:face/color-bg :color/hex])}
             :tabIndex  0}
        (map #(code-line (last %) faces-by-name colors-by-id) code-lines)))))

(def code (om/factory Code))

(defn code-comp []
  (om/class->any reconciler Code))
