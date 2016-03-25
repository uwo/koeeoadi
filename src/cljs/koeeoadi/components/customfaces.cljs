(ns koeeoadi.components.customfaces
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [koeeoadi.util :as util]
            [koeeoadi.config :as config]
            [koeeoadi.reconciler :refer [reconciler]]))

(declare custom-faces-comp)

(defn face-update [comp e]
  (om/transact! comp `[(custom-face/update ~e)]))

(defn face-edit-name [comp face-name new-name]
  (om/transact! comp `[(custom-face/edit-name
                         {:face/name ~face-name
                          :face/name-temp ~new-name})]))

(defn face-change-name [comp current-name]
  (om/transact! comp `[(custom-face/change-name
                         {:current-name ~current-name})]))

(defn face-add [comp]
  (let [{custom-faces :custom-faces/map} (om/props comp)
        faces-sorted (sort-by #(- (:face/id %)) (vals custom-faces))
        new-id       (inc (:face/id (first faces-sorted)))
        new-name     (str "new-face-" new-id)]
    (om/transact! comp
      `[(custom-face/add {~new-name {:face/id        ~new-id
                                     :face/name      ~new-name
                                     :face/editor    :emacs
                                     :face/color-bg  nil
                                     :face/color-fg  nil
                                     :face/bold      false
                                     :face/italic    false
                                     :face/underline false}})])))

(defn face-remove [comp face-name]
  (om/transact! comp `[(custom-face/remove {:face/name ~face-name})]))

(defn color-option [{:keys [color/rgb color/id] :as color} current-color]
  (let [selected? (= current-color color)]
    (dom/option #js {:style #js {:backgroundColor rgb}
                     :value id} rgb)))

(defn custom-face [comp face]
  (let [{colors-by-id :colors/by-id}   (om/props comp)
        colors-list                    (vals colors-by-id)
        {:keys [face/id
                face/name
                face/name-temp
                face/bold
                face/italic
                face/color-bg
                face/color-fg
                face/underline
                face/editor]}        face
        bg-rgb                       (:color/rgb color-bg)
        fg-rgb                       (:color/rgb color-fg)
        editors                      (keys config/editor-file-map)]
    (dom/tr nil
      (dom/td nil
        (if name-temp
          (dom/input #js {:value      name-temp
                          :autoFocus  true
                          :onBlur     #(when (util/valid-face-name? name-temp)
                                         (face-change-name comp name))
                          :onKeyDown  #(when (and (util/valid-face-name? name-temp) (= 13 (util/keycode %)))
                                         (face-change-name comp name))
                          :onChange   #(face-edit-name comp name (util/target-value %))})
          (dom/div #js {:className "row"
                        :onClick #(face-edit-name comp name "")}
            (dom/span nil name)
            (dom/button #js {:className "inline-button"}
              (dom/i #js {:className "fa fa-edit fa-2x"})))))

      (dom/td nil
        (apply dom/select #js {:style    #js {:backgroundColor (or bg-rgb nil)}
                               :onChange #(do (.log js/console (util/target-value %))
                                              (om/transact! comp `[(custom-face/update
                                                                     {:face/name ~name
                                                                      :face/color-bg ~(get colors-by-id (.parseInt js/window (util/target-value %)))})]))}
          (when-not bg-rgb
            (dom/option #js {:selected true
                             :disabled true} "Select a color"))
          (map #(color-option % color-bg) colors-list)))

      (dom/td nil
        ;; TODO refactor with above
        (apply dom/select #js {:style    #js {:backgroundColor (or fg-rgb nil)}
                               :onChange #(om/transact! comp `[(custom-face/update
                                                                 {:face/name ~name
                                                                  :face/color-fg ~(get colors-by-id (.parseInt js/window (util/target-value %)))})])}
          (when-not fg-rgb
            (dom/option #js {:selected true :disabled true} "Select a color"))
          (map #(color-option % color-fg) colors-list)))

      ;; BOLD
      (dom/td nil
        (dom/input #js {:type "checkbox"
                        :onClick #(face-update comp {:face/name name :face/bold (util/target-checked %)})}))
      ;; ITALIC
      (dom/td nil
        (dom/input #js {:type "checkbox"
                        :onClick #(face-update comp {:face/name name :face/italic (util/target-checked %)})}))
      ;; UNDERLINE
      (dom/td nil
        (dom/input #js {:type "checkbox"
                        :onClick #(face-update comp {:face/name name :face/underline (util/target-checked %)})}))

      (dom/td nil
        (apply dom/select #js {:onChange #(face-update comp {:face/name   name
                                                             :face/editor (keyword (util/target-value %))})}
          (map #(util/option (clojure.core/name %) (clojure.core/name editor)) editors)))

      (dom/td nil
        (dom/button #js {:onClick #(face-remove comp name)}
          (dom/i #js {:className "fa fa-close fa-2x"}))))))

(defui CustomFaces
  static om/IQuery
  (query [this]
    [:colors/by-id
     :faces/by-name
     :custom-faces/map])

  Object
  (render [this]
    (let [{colors-by-id      :colors/by-id
           faces-by-name     :faces/by-nam
           custom-faces-map  :custom-faces/map}  (om/props this)

          {:keys [active]}   (om/get-state this)]
      (println "reloading")
      (.log js/console (om/props this))
      (dom/div #js {:id "custom-face-map"
                    :className (str "modal widget "
                                 (if active "" "hide"))}
        (dom/h5
          #js {:className "widget-title"}
          "Custom Faces")
        (dom/button
          #js {:onClick #(face-add this)}
          "Add New Face")
        (dom/div #js {:id          "custom-faces-scroll-container"
                      :cellSpacing 0
                      :cellPadding 0
                      :border      0
                      :width       "400px"}
          (dom/table #js {:id "custom-faces-container"
                          :cellPadding 0
                          :cellSpacing 0
                          }
            (apply dom/tbody nil
              (dom/tr nil
                (dom/td nil "Name")
                (dom/td nil "Background")
                (dom/td nil "Foreground")
                (dom/td nil "Bold")
                (dom/td nil "Italic")
                (dom/td nil "Underline")
                (dom/td nil "Editor")
                (dom/td nil ""))
              (map #(custom-face this %) (sort-by :face/id (vals custom-faces-map))))))
        (dom/button #js {:onClick #(om/update-state! this assoc :active false)} "CLOSE")))))

(def custom-faces (om/factory CustomFaces))

(defn custom-faces-comp []
  (om/class->any reconciler CustomFaces))
