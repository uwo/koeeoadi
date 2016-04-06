(ns koeeoadi.components.palette
  (:require [goog.style :refer [setStyle]]
            [goog.events :as gevents]
            [goog.array :refer [forEach]]
            [goog.dom :refer [getElement]]
            [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]
            [koeeoadi.util :as util])
  (:import [goog.dom query]))

(defn color-update [comp hex]
  (om/transact! comp
    `[(color/update {:color/hex ~hex}) :palette]))

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
    '[:color/id :color/hex [:faces/list _]])

  Object
  (componentDidUpdate [this prev-props prev-state]
    (let [{:keys [status face-classes-by-color-type hex-temp]} (om/get-state this)]
      (case status
        :run  (let [{:keys [bg-faces fg-faces]} face-classes-by-color-type
                    [code-bg-selector face-bg-color-selector] (selectors-for-colorize bg-faces :face/color-bg)
                    [code-fg-selector face-fg-color-selector] (selectors-for-colorize fg-faces :face/color-fg)]
                ;; TODO May be possible to group bg updates into one call t colorize faces
                (when-not (empty? fg-faces)
                  (colorize-faces code-fg-selector "color" hex-temp)
                  (colorize-faces face-fg-color-selector "background-color" hex-temp))

                (when-not (empty? bg-faces)
                  (colorize-faces code-bg-selector "background-color" hex-temp)
                  (colorize-faces face-bg-color-selector "background-color" hex-temp)))
        :done (do
                (color-update this hex-temp)
                (om/update-state! this assoc
                  :state nil
                  :hex-temp nil
                  :face-classes-by-color-type nil))
        :wait)))

  (render [this]
    (let [{:keys [color/id color/hex faces/list] :as color} (om/props this)
          {:keys [hex-temp]}                          (om/get-state this)]
      (dom/div #js {:className "color color-input"}
        (dom/div #js {:className (str "color input-overlay" (if hex "" " color-missing"))
                      :ref      "colorOverlay"
                      :style     (when (or hex-temp hex) #js {:backgroundColor (or hex-temp hex)})})
        (dom/input #js {:onChange #(om/update-state! this assoc :hex-temp (util/target-value %))
                        :onBlur   #(om/update-state! this assoc :state :done)
                        :onClick  #(om/update-state! this assoc
                                     :status :run
                                     :hex-temp hex
                                     :face-classes-by-color-type (faces-to-colorize list id))
                        :type     "color"
                        :value    (or hex-temp hex)})
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

(defui Palette
  static om/IQuery
  (query [this]
    [{:colors/list (om/get-query Color)}])

  Object
  (render [this]
    (let [{:keys [colors/list]} (om/props this)
          callback (partial color-add this)]
      (dom/div #js {:className "widget"
                    :id "palette"}
        (util/widget-title "Palette")
        (apply dom/div #js {:id "palette-colors"}
          (conj (mapv color list)
            (color-adder (om/computed {} {:callback callback}))))))))

(def palette (om/factory Palette))
