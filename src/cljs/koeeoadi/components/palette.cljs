(ns koeeoadi.components.palette
  (:require [goog.style :refer [setStyle]]
            [goog.events :as gevents]
            [goog.array :refer [forEach]]
            [goog.dom :refer [getActiveElement getDocument getElement getElementsByTagNameAndClass]]
            [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]
            [koeeoadi.util :as util]
            [koeeoadi.reconciler :refer [reconciler]])
  (:import [goog.dom query]
           [goog.ui HsvPalette]
           [goog.ui.Component EventType]))

(declare Palette)
(declare PaletteWidget)

(defn widget-input-elem [comp]
  (aget (query "input" (getElement (dom/node comp))) 0))

(defn color-update
  ([comp]
   (let [{:keys [:palette-widget/active-color
                 :palette-widget/closure-comp]} (om/props comp)]
     (color-update comp active-color closure-comp (.getColor closure-comp))))
  ([comp active-color closure-comp color-hex]
   (when (not= color-hex (:color/hex active-color))
     (let [id         (:color/id active-color)
           color-comp (om/ref->any reconciler [:colors/by-id id])]
       (om/transact! color-comp
         `[(state/update-ref  {:mutate/name color/update
                               :props {:color/id  ~id
                                       :color/hex ~(.getColor closure-comp)}}) :palette])))))

(defn color-remove [comp {:keys [color/id] :as color} e]
  (.preventDefault e)
  (.stopPropagation e)
  (om/transact! comp
    `[(color/remove) :palette]))

(defn palette-widget-update [color-id faces-list]
  (om/transact! (om/class->any reconciler Palette)
    `[(state/merge
        {:palette-widget/active-color [:colors/by-id ~color-id]
         :palette-widget/face-classes-by-color-type ~(util/faces-to-colorize faces-list color-id)})]))

(defn face-class-prefix [color-type]
  (if (= :face/color-fg color-type)
    ".color-fg-"
    ".color-bg-"))

(defn face-to-selectors [face color-type]
  (vector (str ".code-" face) (str (face-class-prefix color-type) face)))

(defn colorize-faces [classes color-prop hex-temp]
  (when-not (empty? classes)
    (let [elements (query classes)]
      (forEach elements #(setStyle % color-prop hex-temp)))))

(defn selectors-for-colorize [faces color-type]
  (let [selectors (map #(face-to-selectors % color-type) faces)
        cnt (count selectors)]
    (cond
      (= count 0)
      [nil nil]

      (= count 1 (count faces))
      (first selectors)

      :else (reduce #(vector
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
                    :style     (when (or hex-temp hex) #js {:backgroundColor (or hex-temp hex)})}
        (dom/div #js {:className "color-mask"
                      :onClick   #(palette-widget-update id list)})
        (dom/button #js {:className "color-remove"
                         :onClick   #(color-remove this color %)}
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

(defn selectors [bg-faces fg-faces]
  (let [fg-faces-empty? (empty? fg-faces)
        bg-faces-empty? (empty? bg-faces)]
    (vector
      (clojure.string/join []))))

(defn handle-action [comp e]
  (let [{:keys [palette-widget/active-color
                palette-widget/face-classes-by-color-type]} (om/props comp)
        hex-temp (util/target-color e)]
    (if  (= (widget-input-elem comp) (getActiveElement (getDocument)))
      (color-update comp)
      ;; TODO refactor this so its in the state, its wasteful to calculate this on each run through, could maybe even use the element references themeselves
      (let [{:keys [bg-faces fg-faces]} face-classes-by-color-type
            color-editable-selector (str ".color-" (:color/id active-color))
            [code-bg-selector face-bg-color-selector] (selectors-for-colorize bg-faces :face/color-bg)
            [code-fg-selector face-fg-color-selector] (selectors-for-colorize fg-faces :face/color-fg)
            bg-selectors  (clojure.string/join "," (remove nil? [color-editable-selector code-bg-selector face-bg-color-selector face-fg-color-selector]))
            fg-selectors   (clojure.string/join "," (remove nil? [code-fg-selector]))]

        (colorize-faces bg-selectors "background-color" hex-temp)
        (colorize-faces fg-selectors "color" hex-temp)))))

(defui PaletteWidget
  static om/IQuery
  (query [this]
    '[[:palette-widget/closure-comp _]
      [:palette-widget/active-color _]
      [:palette-widget/face-classes-by-color-type _]
      [:faces/list _]])

  Object
  (componentDidMount [this]
    (let [{:keys        [palette-widget/active-color faces/list] :as props} (om/props this)
          widget        (HsvPalette. nil nil "goog-hsv-palette-sm")]
      (.render widget (getElement (dom/node this "paletteWidget")))
      (let [input (widget-input-elem this)]
        (.setColor widget (:color/hex active-color))
        (gevents/listen widget EventType.ACTION #(handle-action this %))
        (om/transact! this `[(state/merge
                               {:palette-widget/face-classes-by-color-type ~(util/faces-to-colorize list (:color/id active-color))
                                :palette-widget/closure-comp ~widget})]))))

  (componentDidUpdate [this {id-prev :color/id} _]
    (let [{:keys [palette-widget/active-color
                  palette-widget/closure-comp
                  faces/list]} (om/props this)]
      (when (not= (:color/id active-color) id-prev)
        (.setColor closure-comp (:color/hex active-color)))))

  (render [this]
    (let [{:keys [palette-widget/closure-comp
                  palette-widget/active-color] :as props} (om/props this)]

      (dom/div #js {:onMouseUp    #(color-update this)
                    :onMouseLeave #(color-update this)
                    :id  "palette-widget"
                    :ref "paletteWidget"} nil))))

(def palette-widget (om/factory PaletteWidget))

(defn minimum-colors-class [colors]
  (when (< (count colors) 3)
    "minimum-colors"))

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
        (apply dom/div #js {:id "palette-colors"
                            :className (minimum-colors-class list)}
          (palette-widget palette-widget-data)
          (conj (mapv #(color (merge % palette-widget-data)) list)
            (color-adder (om/computed {} {:callback callback}))))))))

(def palette (om/factory Palette))
