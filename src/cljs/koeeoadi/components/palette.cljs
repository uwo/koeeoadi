(ns koeeoadi.components.palette
  (:require [goog.style :refer [setStyle]]
            [goog.events :as gevents]
            [goog.array :refer [forEach]]
            [goog.dom :refer [getElement]]
            [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]
            [koeeoadi.util :as util]
            [koeeoadi.reconciler :refer [reconciler]])
  (:import [goog.dom query]
           [goog.ui HsvPalette]
           [goog.ui.Component EventType]))

(declare Palette)
(declare PaletteWidget)

(defn color-update [comp props]
  (om/transact! comp
    `[(color/update ~props) :palette]))

(defn color-remove [comp {:keys [color/id] :as color}]
  (om/transact! comp
    `[(color/remove) :palette]))

(defn colorize-faces [classes color-prop hex-temp]
  (when-not (empty? classes)
    (let [elements (query classes)]
      (forEach elements #(setStyle % color-prop hex-temp)))))

(defn faces-to-colorize [faces color-id]
  (hash-map
    :bg-faces (map :face/name (filter #(= color-id (last (:face/color-bg %))) faces))
    :fg-faces (map :face/name (filter #(= color-id (last (:face/color-fg %))) faces))))

(defn selectors-for-colorize [faces color-type]
  (let [face-color-type-class-prefix (if (= :face/color-fg color-type) ".color-fg-" ".color-bg-")
        code-class-prefix            ".code-"
        face-classes-groups          (->> faces
                                       (map #(vector
                                               (str code-class-prefix %)
                                               (str face-color-type-class-prefix %))))]
    (if (= 1 (count face-classes-groups))
      (first face-classes-groups)
      (reduce #(vector
                 (str (first %1) "," (first %2))
                 (str (last %1) "," (last %2)))
        face-classes-groups ))))

(defui Color
  static om/Ident
  (ident [this {:keys [color/id]}]
    [:colors/by-id id])

  static om/IQuery
  (query [this]
    '[:color/id :color/hex
      [:faces/list _]
      [:palette/active-color _]])

  Object
  (render [this]
    (let [{:keys [color/id
                  color/hex
                  faces/list
                  palette/active-color] :as color} (om/props this)
          {:keys [hex-temp]} (om/get-state this)
          active?            (= id (:color/id active-color))]
      (dom/div #js {:className (str "color color-editable" (when active? " color-active") (when-not hex " color-missing") " color-" id)
                    :onClick #(om/transact! this `[(color/set-active) :palette])
                    ;; :onClick   #(om/update-state! (om/class->any reconciler PaletteWidget) assoc
                    ;;               :status :load
                    ;;               :face-classes-by-color-type (faces-to-colorize list id))
                    :style     (when (or hex-temp hex) #js {:backgroundColor (or hex-temp hex)})}
        (dom/button #js {:className "color-remove"
                         :onClick   #(color-remove this color)}
          (dom/i #js {:className "fa fa-remove fa-2x"}))))))

(def color (om/factory Color {:keyfn :color/id}))

(defn color-add [comp]
  (om/transact! comp '[(color/add)]))

(defui ColorAdder
  Object
  (render [this]
    (let [callback (:callback (om/get-computed this))]
      (dom/div #js {:className "color color-add"
                    :onClick   callback}
        (dom/i #js {:className "fa fa-plus"})))))

(def color-adder (om/factory ColorAdder))

(defn handle-action [comp e]
  (let [{:keys [color/id color/hex]} (om/props comp)
        {:keys [face-classes-by-color-type]} (om/get-state comp)
        hex-temp (util/target-color e)]
    ;; TODO refactor this so its in the state, its wasteful to calculate this on each run through, could maybe even use the element references themeselves
    (let [{:keys [bg-faces fg-faces]} face-classes-by-color-type
          color-editable-selector (str ".color-" id)
          [code-bg-selector face-bg-color-selector] (selectors-for-colorize bg-faces :face/color-bg)
          [code-fg-selector face-fg-color-selector] (selectors-for-colorize fg-faces :face/color-fg)]

      ;; TODO combine these
      (colorize-faces color-editable-selector "background-color" hex-temp)

      (when-not (empty? fg-faces)
        (colorize-faces code-fg-selector "color" hex-temp)
        (colorize-faces face-fg-color-selector "background-color" hex-temp))

      (when-not (empty? bg-faces)
        (colorize-faces code-bg-selector "background-color" hex-temp)
        (colorize-faces face-bg-color-selector "background-color" hex-temp)))))

;; clicking on a color changes active-color
;; this updates palettewidget
;; palette widget is always setup to check for a change in active-color if it does that then it updates the widget UI, otherwise it does nothing

(defui PaletteWidget
  static om/IQuery
  (query [this]
    '[:color/id :color/hex [:faces/list _]])

  Object
  (componentDidMount [this]
    (let [{:keys [color/id color/hex faces/list] :as props} (om/props this)
          palette-widget (HsvPalette. nil nil "goog-hsv-palette-sm")]
      (.render palette-widget (getElement (dom/node this "paletteWidget")))
      (.setColor palette-widget hex)
      (gevents/listen palette-widget "action" #(handle-action this %))
      (om/update-state! this assoc
        :face-classes-by-color-type (faces-to-colorize list id)
        :closure-comp palette-widget)))

  (componentDidUpdate [this {id-prev :color/id} _]
    (let [{:keys [color/id color/hex faces/list]} (om/props this)
          {:keys [closure-comp]} (om/get-state this)]
      (when (not= id id-prev)
        (om/update-state! this assoc :face-classes-by-color-type (faces-to-colorize list id))
        (.setColor closure-comp hex))))

  (render [this]
    (let [{:keys [closure-comp]} (om/get-state this)]
      (dom/div #js {:onMouseUp #(color-update this {:color/id (:color/id (om/props this))
                                                    :color/hex (.getColor closure-comp)})
                    :onTouchEnd #(color-update this (.getColor closure-comp))
                    :ref "paletteWidget"} nil))))

(def palette-widget (om/factory PaletteWidget))

(om/get-query Palette)

(defui Palette
  static om/IQuery
  (query [this]
    `[{:colors/list ~(om/get-query Color)}
      {[:palette/active-color ~'_] ~(om/get-query PaletteWidget)}])

  Object
  (render [this]
    (let [{colors/list         :colors/list
           palette-widget-data :palette/active-color} (om/props this)
          callback (partial color-add this)]
      (dom/div #js {:className "widget"
                    :id        "palette"}
        (util/widget-title "Palette")
        (apply dom/div #js {:id "palette-colors"}
          (palette-widget palette-widget-data)
          (conj (mapv color list)
            (color-adder (om/computed {} {:callback callback}))))))))

(def palette (om/factory Palette))
