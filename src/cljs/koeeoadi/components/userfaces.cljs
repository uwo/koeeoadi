(ns koeeoadi.components.userfaces
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [koeeoadi.util :as util]
            [koeeoadi.config :as config]
            [koeeoadi.reconciler :refer [reconciler]]))

(declare user-faces-comp)

(defn face-update [comp props]
  (om/transact! comp `[(user-face/update ~props)]))

(defn change-name [comp face-name new-name needs-focus]
  (when-not (empty? new-name)
    (om/update-state! comp assoc :name-temp (clojure.string/trim new-name)))
  (when needs-focus
    (om/update-state! comp assoc :needs-focus true)))

(defn face-add [comp]
  (let [{user-faces  :user-faces/list} (om/props comp)
        faces-sorted (sort-by #(- (:face/id %)) user-faces)
        new-id       (inc (:face/id (first faces-sorted)))
        new-name     (str "new-face-" new-id)]
    (om/transact! comp
      `[(user-face/add {:face/id        ~new-id
                        :face/name      ~new-name
                        :face/editor    :emacs
                        :face/color-bg  nil
                        :face/color-fg  nil
                        :face/bold      false
                        :face/italic    false
                        :face/underline false})])))

(defn face-remove [comp]
  (om/transact! comp `[(user-face/remove) :palette]))

(defn color-ref [value]
  (when-not (= value "None")
    [:colors/by-id (.parseInt js/window value)]))

(defn color-change [comp color-type e]
  (let [{face-name    :face/name}    (om/props comp)
        {colors-by-id :colors/by-id} (om/get-computed comp)
        color-ref (color-ref (util/target-value e))]
    (face-update comp {:face/name face-name
                       color-type color-ref})))

(defn edit-name-button [comp]
  (let [name      (:face/name (om/props comp))
        name-temp (:name-temp (om/get-state comp))]
    (dom/button #js {:id "user-face-edit-button"
                     :className "inline-button"
                     :onClick #(change-name comp name name true)
                     :style  (util/display (not name-temp))}
      (dom/i #js {:className "fa fa-edit fa-2x"}))))

(defn rename
  ([comp]
   (let [name      (:face/name (om/props comp))
         name-temp (:name-temp (om/get-state comp))]
     (when (and
             (not= name name-temp)
             (util/valid-face-name? name-temp))
       (om/transact! comp `[(user-face/change-name {:new-name ~name-temp}) :palette]))))
  ([comp e]
   (when (= 13 (util/keycode e))
     (rename comp))))

(defn editable-name [comp]
  (let [{:keys [face/name]} (om/props comp)
        name-temp (:name-temp (om/get-state comp))]
    (dom/td #js {:className "user-face-name-td"}
      (dom/input #js {:value     name-temp
                      :onKeyDown #(rename comp %)
                      :onBlur    #(rename comp)
                      :onChange  #(change-name comp name (util/target-value %) false)
                      :style     (util/display name-temp)
                      :ref       "editField"})
      (dom/div #js {:onClick #(change-name comp name name true)
                    :style   (util/display (not name-temp))}
        name)
      (edit-name-button comp))))

(defn color-option [{:keys [color/hex color/id] :as color} current-color]
  (let [selected? (= current-color color)]
    (dom/option
      #js {:style    #js {:backgroundColor hex}
           :selected selected?
           :value    id}
      hex)))

(defn color-select [comp color color-type]
  (let [color-hex (:color/hex color)
        colors-by-id (:colors/by-id (om/get-computed comp))]
    (dom/td nil
      (apply dom/select
        #js {:style    #js {:backgroundColor (or color-hex nil)}
             :onChange #(color-change comp color-type %)}
        (dom/option #js {:value "None"} "None")
        (map #(color-option % color) (vals colors-by-id))))))

(defn style-checkbox [comp checked?]
  (dom/td nil
    (dom/input
      #js {:type "checkbox"
           :checked checked?
           :onClick #(face-update comp
                       {:face/name comp
                        :face/underline (util/target-checked %)})})))

(defn face-update-editor [comp e]
  (face-update comp
    {:face/name   (:face/name comp)
     :face/editor (keyword (util/target-value e))}))

(defn editor-select [comp]
  (let [{:keys [:face/editor :face/name]} (om/props comp)
        editor-name (clojure.core/name editor)
        editors (keys config/editor-file-map)]
    (dom/td nil
      (apply dom/select #js {:onChange face-update-editor}
        (map #(util/option (clojure.core/name %) editor-name) editors)))))

(defn remove-button [comp]
  (dom/td nil
    (dom/button #js {:className "user-face-remove-button"
                     :onClick   #(face-remove comp)}
      (dom/i #js {:className "fa fa-close fa-2x"}))))

(defui UserFace
  static om/Ident
  (ident [this {:keys [face/name]}]
    [:user-faces/by-name name])

  static om/IQuery
  (query [this]
    [:colors/by-id
     :face/id
     :face/name
     :face/bold
     :face/italic
     :face/underline
     :face/editor
     {:face/color-bg [:color/id :color/hex]}
     {:face/color-fg [:color/id :color/hex]}])

  Object
  (componentDidUpdate [this prev-props prev-state]
    (when (om/get-state this :needs-focus)
      (let [node (dom/node this "editField")
            len  (.. node -value -length)]
        (.focus node)
        (.setSelectionRange node len len))
      (om/update-state! this assoc :needs-focus nil)))

  (render [this]
    (let [{bold      :face/bold
           italic    :face/italic
           underline :face/underline
           color-bg  :face/color-bg
           color-fg  :face/color-fg} (om/props this)]
      (dom/tr nil
        (editable-name this)
        (color-select this color-bg :face/color-bg)
        (color-select this color-fg :face/color-fg)
        (style-checkbox this bold)
        (style-checkbox this italic)
        (style-checkbox this underline)
        (editor-select this)
        (remove-button this)))))

(def user-face (om/factory UserFace {:keyfn :face/name}))

(defui UserFaces
  static om/IQuery
  (query [this]
    [:colors/by-id
     {:user-faces/list (om/get-query UserFace)}])

  Object
  (render [this]
    (let [{colors-by-id    :colors/by-id
           user-faces-list :user-faces/list}  (om/props this)
          user-faces-sorted (sort-by :face/id user-faces-list)
          active      (:active (om/get-state this))
          modal-class (str "modal " (if active "" "hide"))]
      (dom/div #js {:id "user-faces-modal" :className modal-class}
        (dom/div #js {:className "modal-content-container"}
          (dom/div #js {:className "modal-content"}
            (dom/h5 #js {:className "modal-title"} "User Faces")
            (dom/button #js {:onClick #(face-add this)} "Add Face")
            (dom/div #js {:id "user-faces-scroll-container"}
              (dom/table #js {:id "user-faces-container"}
                (dom/thead nil
                  (dom/tr nil
                    (dom/th #js {:id "user-face-heading-name"} "Name")
                    (dom/th #js {:id "user-face-heading-background"} "Background")
                    (dom/th #js {:id "user-face-heading-foreground"} "Foreground")
                    (dom/th #js {:id "user-face-heading-bold"} "Bold")
                    (dom/th #js {:id "user-face-heading-italic"} "Italic")
                    (dom/th #js {:id "user-face-heading-underline"} "Underline")
                    (dom/th #js {:id "user-face-heading-editor"} "Editor")
                    (dom/th #js {:id "user-face-heading-remove"} "")))
                (apply dom/tbody nil
                  (map #(user-face (om/computed % {:colors/by-id colors-by-id})) user-faces-sorted))))
            (dom/button #js {:className "modal-close-button"
                             :onClick   #(om/update-state! this assoc :active false)} "CLOSE")))))))

(def user-faces (om/factory UserFaces))

(defn user-faces-comp []
  (om/class->any reconciler UserFaces))
