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

(defn color-update [comp active-color closure-comp]
  (om/transact! comp
    `[(color/update {:color/id ~(:color/id active-color)
                     :color/hex ~(.getColor closure-comp)}) :palette]))

(defn color-remove [comp {:keys [color/id] :as color}]
  (om/transact! comp
    `[(color/remove) :palette]))

(defn palette-widget-update [color-id faces-list]
  (om/transact! (om/class->any reconciler Palette)
    `[(palette-widget/update
        {:palette-widget/active-color [:colors/by-id ~color-id]
         :palette-widget/face-classes-by-color-type ~(util/faces-to-colorize faces-list color-id)})]))

(defn face-class-prefix [color-type]
  (if (= :face/color-fg color-type)
    ".color-fg-"
    ".color-bg-"))

(defn face-to-selectors [face color-type]
  (vector (str ".code-" %) (str (face-class-prefix color-type) %)))

(defn colorize-faces [classes color-prop hex-temp]
  (when-not (empty? classes)
    (let [elements (query classes)]
      (forEach elements #(setStyle % color-prop hex-temp)))))

(defn selectors-for-colorize [faces color-type]
  (let [selectors (map #(face-to-selectors % color-type) faces)]
    (if (= 1 (count faces))
      (first selectors)
      (reduce #(vector
                 (str (first %1) "," (first %2))
                 (str (last %1) "," (last %2)))
        selectors))))

(defn color-class [props]
  (let [{:keys [:color/id :color/hex :palette-widget/active-color]} props
        active? (= id (:color/id active-color))]
    (str "color color-editable"
      (when active? " color-active")
      (when-not hex " color-missing")
      " color-" id)))

(defui Color
  static om/Ident
  (ident [this {:keys [color/id]}]
    [:colors/by-id id])

  static om/IQuery
  (query [this]
    '[:color/id
      :color/hex
      [:palette-widget/active-color _]
      [:faces/list _]])

  Object
  (render [this]
    (let [{:keys [color/id color/hex faces/list] :as color} (om/props this)
          {:keys [hex-temp]} (om/get-state this)]
      (dom/div #js {:className (color-class color)
                    :onClick   #(palette-widget-update id list)
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
  (let [{:keys [palette-widget/active-color palette-widget/face-classes-by-color-type]} (om/props comp)
        hex-temp (util/target-color e)]
    ;; TODO refactor this so its in the state, its wasteful to calculate this on each run through, could maybe even use the element references themeselves
    (let [{:keys [bg-faces fg-faces]} face-classes-by-color-type
          color-editable-selector (str ".color-" (:color/id active-color))
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

(defui PaletteWidget
  static om/IQuery
  (query [this]
    '[[:palette-widget/closure-comp _]
      [:palette-widget/active-color _]
      [:palette-widget/face-classes-by-color-type _]
      [:faces/list _]])

  Object
  (componentDidMount [this]
    (let [{:keys [palette-widget/active-color faces/list] :as props} (om/props this)
          palette-widget (HsvPalette. nil nil "goog-hsv-palette-sm")]
      (.render palette-widget (getElement (dom/node this "paletteWidget")))
      (.setColor palette-widget (:color/hex active-color))
      (gevents/listen palette-widget "action" #(handle-action this %))
      (om/transact! this `[(palette-widget/update
                             {:palette-widget/face-classes-by-color-type ~(util/faces-to-colorize list (:color/id active-color))
                              :palette-widget/closure-comp               ~palette-widget})])))

  (componentDidUpdate [this {id-prev :color/id} _]
    (let [{:keys [palette-widget/active-color
                  palette-widget/closure-comp
                  faces/list]} (om/props this)]
      (when (not= (:color/id active-color) id-prev)
        (om/transact! this `[(palette-widget/update
                               {:palette-widget/face-classes-by-color-type ~(util/faces-to-colorize list (:color/id active-color))})])
        (.setColor closure-comp (:color/hex active-color)))))

  (render [this]
    (let [{:keys [palette-widget/closure-comp
                  palette-widget/active-color] :as props} (om/props this)]
      (dom/div #js {:onMouseUp #(color-update this active-color closure-comp)
                    ;;:onTouchEnd #(color-update this active-color closure-comp)
                    :ref "paletteWidget"} nil))))

(def palette-widget (om/factory PaletteWidget))

(defui Palette
  static om/IQuery
  (query [this]
    `[{:colors/list ~(om/get-query Color)}
      {:palette-widget ~(om/get-query PaletteWidget)}])

  Object
  (render [this]
    (let [{colors/list         :colors/list
           palette-widget-data :palette-widget} (om/props this)
          callback (partial color-add this)]
      (dom/div #js {:className "widget"
                    :id        "palette"}
        (util/widget-title "Palette")
        (apply dom/div #js {:id "palette-colors"}
          (palette-widget palette-widget-data)
          (conj (mapv #(color (merge % palette-widget-data)) list)
            (color-adder (om/computed {} {:callback callback}))))))))

(def palette (om/factory Palette))
