(ns koeeoadi.components.code
  (:require [goog.dom :as gdom]
            [goog.style :as gstyle] [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [koeeoadi.util :as util]
            [koeeoadi.reconciler :refer [reconciler]]
            [koeeoadi.components.colorpicker :refer [color-picker-comp]]))

(def code-class "code")

(defn code-face-class [face-name]
  (str "code-" (name face-name)))

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
  static om/IQuery
  (query [this]
    ;; TODO why can't I query Face here?
    [{:code-chunk/face util/shared-face-query}
     :code-chunk/line-chunk
     :code-chunk/string])

  Object
  (render [this]
    (let [{:keys [code-chunk/face
                  code-chunk/line-chunk
                  code-chunk/string]}   (om/props this)
          {:keys [face/name]}           face]
      (dom/span
        #js {:className (str code-class " " (code-face-class name))
             :onBlur    #(util/color-picker-hide (color-picker-comp))
             :onClick   #(util/color-picker-show (color-picker-comp) face :face/color-fg %)
             :style     (face-styles face)
             :tabIndex  0}
        string))))

(def code-chunk (om/factory CodeChunk {:keyfn :code-chunk/line-chunk}))

(defn update-face [face-ref faces-by-name colors-by-id]
  (let [{:keys [face/color-bg face/color-fg] :as face} (get faces-by-name (last face-ref))]
    (assoc face
      :face/color-bg (get colors-by-id (last color-bg))
      :face/color-fg (get colors-by-id (last color-fg)))))

(defn update-code [code-chunk faces-by-name colors-by-id]
  (update code-chunk :code-chunk/face update-face faces-by-name colors-by-id))

(defn code-line [line faces-by-name colors-by-id]
  (apply dom/div #js {:className "code-line"}
    (map #(code-chunk (update-code % faces-by-name colors-by-id)) line)))

(defn group-lines [code-chunks]
  (sort-by first (group-by #(.floor js/Math (/ (:code-chunk/line-chunk %) 1000))
                   code-chunks)))

(defui Code
  static om/IQuery
  (query [this]
    `[[:code/map ~'_]
      [:code/name ~'_]
      [:faces/by-name ~'_]
      [:colors/by-id ~'_]
      {:code-background ~util/shared-face-query}])

  Object
  (render [this]
    (let [{code-background :code-background
           code-name :code/name
           faces-by-name :faces/by-name
           colors-by-id :colors/by-id
           code-map :code/map} (om/props this)
          code-lines (group-lines (get-in code-map [code-name :code-chunks/list]))]
      (apply dom/code
        #js {:className (code-face-class "background")
             :id        "code"
             :onBlur    #(util/color-picker-hide (color-picker-comp))
             :onClick   #(util/color-picker-show (color-picker-comp) code-background :face/color-bg %)
             :style     #js {:backgroundColor (get-in code-background [:face/color-bg :color/hex])}
             :tabIndex  0}
        (map #(code-line (last %) faces-by-name colors-by-id) code-lines)))))

(def code (om/factory Code))

(defn code-comp []
  (om/class->any reconciler Code))
