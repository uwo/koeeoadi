(ns komokio.components.palette
  (:require [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]))

(defn handle-color-change [e comp {:keys [color/name db/id] :as color}]
  (let [rgb-editing (.. e -target -value)]
    (om/transact! comp
      `[(color/update {:id ~id :name ~name :rgb ~rgb-editing}) :face/name]))) ;; update all faces

(defui Color

  static om/Ident
  (ident [this {:keys [color/name]}]
    [:colors/by-name name])

  static om/IQuery
  (query [this]
    [:db/id :color/name :color/rgb])

  Object
  ;; will be adding a editing-color property here
  (initLocalState [this] {})

  (render [this]
    (let [{:keys [db/id color/name color/rgb] :as color} (om/props this)
          {:keys [rgb-editing]}                          (om/get-state this)]
      (dom/div #js {:className "color-input"
                    :style     #js {:backgroundColor rgb}}
        (dom/div #js {:className "input-overlay"
                      :style     #js {:backgroundColor rgb}})
        (dom/input #js {:type     "color"
                        :value    rgb
                        :onClick  #(println "color picker clicked")
                        :onChange #(handle-color-change % this color)})))))

(def color (om/factory Color {:keyfn :db/id}))

(defui Palette
  static om/IQuery
  (query [this]
    [{:colors/list (om/get-query Color)}])

  Object
  (render [this]
    (let [{:keys [colors/list]} (om/props this)]
      (dom/div #js {:id "palette"
                    :className "widget"}
        (dom/h5 nil "Palette")
        (dom/div nil "Click color to edit:")
        (apply dom/div nil (map color list))))))

(def palette (om/factory Palette))
