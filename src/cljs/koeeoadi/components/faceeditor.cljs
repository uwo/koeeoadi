(ns koeeoadi.components.faceeditor
  (:require [goog.dom :as gdom]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [koeeoadi.components.palette :refer [Color]]
            [koeeoadi.util :as util]
            [koeeoadi.reconciler :refer [reconciler]]
            [koeeoadi.components.palettepicker :refer [palette-picker-comp]]
            [koeeoadi.components.userfaces :refer [user-faces-comp]]
            [koeeoadi.components.codedisplay :refer [code-display-comp]]))

(declare face-editor-comp)

(defn face-color-class [disabled? rgb]
  (cond disabled?  "color-void"
        (nil? rgb) "color-undefined"
        :else      ""))

(defn face-disabled? [name color-type]
  (and (or (= name "background") (= name "default"))
    (= :face/color-bg color-type)))

(defn face-color [comp color-type]
  (let [{:keys [face/name] :as props} (om/props comp)
        {rgb :color/rgb}              (color-type props)
        {:keys [editing?] :as state}  (om/get-state comp)
        disabled?                     (face-disabled? name color-type)
        clazz                         (face-color-class disabled? rgb)]
    (dom/div
      #js {:className (str "color color-trigger " clazz)
           :onBlur    (if-not disabled? #(util/palette-picker-hide (palette-picker-comp)) #(do))
           :onClick   (if-not disabled? #(util/palette-picker-show (palette-picker-comp) (om/props comp) color-type %) #(do))
           :style     #js {:backgroundColor rgb}
           :tabIndex  (when-not disabled? "0")})))

(defn face-update [name prop e]
  (let [checked (util/target-checked e)]
    (om/transact! (code-display-comp)
      `[(face/update {:face/name ~name
                      ~prop      ~checked})])))

(defn face-style [comp prop]
  (let [{:keys [face/name]} (om/props comp)]
    (dom/li nil
      (dom/input #js
        {:type    "checkbox"
         :onClick #(face-update name prop %)})
      (clojure.core/name prop))))

(defn face-styles [comp]
  (let [{:keys [face/name
                face/bold
                face/italic
                face/underline]} (om/props comp)
        {:keys [active-face]}    (om/get-computed comp)]
    (dom/ul #js {:className "face-styles"
                 :style (util/display (= name active-face))}
      (face-style comp :face/bold)
      (face-style comp :face/italic)
      (face-style comp :face/underline))))

(defui Face
  static om/Ident
  (ident [this {:keys [face/name]}]
    [:faces/by-name name])

  static om/IQuery
  (query [this]
    `[:face/id
      :face/name
      :face/bold
      :face/italic
      :face/underline
      {:face/color-fg ~(om/get-query Color)}
      {:face/color-bg ~(om/get-query Color)}])

  Object
  (render [this]
    (let [{id         :face/id
           face-name  :face/name
           :as props}           (om/props this)
          {:keys [active-face]} (om/get-computed this)
          active?               (= face-name active-face)
          icon-classes          (str "fa fa-2x "
                                  (if active? "fa-angle-down" "fa-angle-right"))]
      (dom/div #js {:className "face"}
        (face-color this :face/color-bg)
        (face-color this :face/color-fg)
        (dom/div #js {:className "face-name"
                      :onClick   #(om/update-state! (face-editor-comp) assoc :active-face (if active? nil face-name))}
          (clojure.core/name face-name))
        (dom/i #js {:className icon-classes})
        (when active?
          (face-styles this))))))

(def face (om/factory Face {:keyfn :face/name}))

(defui FaceEditor
  static om/IQuery
  (query [this]
    [{:faces/list (om/get-query Face)}])
  Object
  (render [this]
    (let [{:keys [faces/list]}  (om/props this)
          {:keys [active-face]} (om/get-state this)]
      (dom/div #js {:className  "widget"
                    :id         "face-editor"}
        (dom/h5 #js {:className "widget-title"} "Faces")
        (dom/button
          #js {:id      "user-face-map-button"
               :onClick #(om/update-state! (user-faces-comp) assoc :active true)}
          "Edit User Face Map")
        (dom/div #js {:id "faces-container"}
          (map #(face (om/computed % {:active-face active-face})) list))))))

(def face-editor (om/factory FaceEditor))

(defn face-editor-comp []
  (om/class->any reconciler FaceEditor))
