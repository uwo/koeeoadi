(ns koeeoadi.components.faces
  (:require [goog.dom :as gdom]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [koeeoadi.components.palette :refer [Color]]
            [koeeoadi.util :as util]
            [koeeoadi.reconciler :refer [reconciler]]
            [koeeoadi.components.colorpicker :refer [color-picker-comp]]
            [koeeoadi.components.userfaces :refer [user-faces-comp]]
            [koeeoadi.components.code :refer [code-comp]]))

(declare faces-comp)

(defn face-color-class [disabled? hex]
  (cond disabled?  "color-void"
        (nil? hex) "color-undefined"
        :else      ""))

(defn face-disabled? [name color-type]
  (or
    (and (= name "background")
      (= :face/color-fg color-type))
    (and (= name "default")
      (= :face/color-bg color-type))))

(defn face-color [comp color-type]
  (let [{:keys [face/name] :as props} (om/props comp)
        {hex :color/hex}              (color-type props)
        disabled?                     (face-disabled? name color-type)
        disabled-class                (face-color-class disabled? hex)
        face-color-type-class         (str (if (= :face/color-fg color-type) "color-fg" "color-bg") "-" name)]
    (dom/div
      #js {:className (clojure.string/join " " ["color" "color-trigger" disabled-class face-color-type-class])
           :onBlur    (if-not disabled? #(util/color-picker-hide (color-picker-comp)) #(do))
           :onClick   (if-not disabled? #(util/color-picker-show (color-picker-comp) (om/props comp) color-type %) #(do))
           :style     #js {:backgroundColor hex}
           :tabIndex  (when-not disabled? "0")})))

(defn face-update [comp prop e]
  (let [name    (:face/name (om/props comp))
        checked (util/target-checked e)]
    (om/transact! comp
      `[(state/update-ref {:mutate/name face/update
                           :props {:face/name   ~name
                                   ~prop        ~checked}}) :code])))

(defn face-style [comp prop]
  (dom/li nil
    (dom/input #js
      {:type    "checkbox"
       :checked (get (om/props comp) prop)
       :onClick #(face-update comp prop %)})
    (clojure.core/name prop)))

(defn face-styles [comp]
  (let [{:keys [face/active-face
                face/name]} (om/props comp)]
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
    (conj util/shared-face-query
      '[:face/active-face _]))

  Object
  (render [this]
    (let [{id          :face/id
           name   :face/name
           active-face :face/active-face
           :as props}           (om/props this)
          active?               (= name active-face)
          icon-classes          (str "fa fa-2x "
                                  (if active? "fa-angle-down" "fa-angle-right"))]
      (dom/div #js {:className "face striped"}
        (face-color this :face/color-bg)
        (face-color this :face/color-fg)
        (dom/div #js {:className "face-name"
                      :onClick   #(om/transact! (faces-comp) `[(state/merge {:face/active-face ~name})])}
          name)
        (dom/i #js {:className icon-classes})
        (when active?
          (face-styles this))))))

(def face (om/factory Face {:keyfn :face/name}))

(defui Faces
  static om/IQuery
  (query [this]
    [:widget/active
    {:faces/list (om/get-query Face)}])
  Object
  (render [this]
    (let [{:keys [widget/active faces/list]}  (om/props this)]
      (dom/div #js {:className  (util/widget-class :faces active)
                    :id         "faces"}
        (util/widget-title "Faces")
        (dom/button
          #js {:id      "user-face-map-button"
               :onClick #(om/update-state! (user-faces-comp) assoc :active true)}
          "Edit User Faces")
        (apply dom/div #js {:id "faces-container"}
          (map face list))))))

(def faces (om/factory Faces))

(defn faces-comp []
  (om/class->any reconciler Faces))
