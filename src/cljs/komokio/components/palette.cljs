(ns komokio.components.palette
  (:require [goog.dom :as gdom]
            [goog.events :as gevents]
            [om.dom :as dom]
            [om.next :as om :refer-macros [defui]])
  (:import goog.events.EventType))

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
      (dom/div #js {:className "color color-input"
                    :style     #js {:backgroundColor rgb}}
        (dom/div #js {:className "input-overlay"
                      :style     #js {:backgroundColor rgb}})
        (dom/input #js {:type     "color"
                        :value    rgb
                        :onClick  #(println "color picker clicked")
                        :onChange #(handle-color-change % this color)})))))

(def color (om/factory Color {:keyfn :db/id}))

;; (gevents/dispatchEvent
;;                               (gdom/getElement "color-add-button")
;;                               (aget EventType "CLICK"))
(defui ColorAdd
  Object
  (componentDidUpdate [this prev-props prev-state]
    (println "color adder updated"))

  (render [this]
    (dom/div #js {:className "color color-add"}
      (dom/i #js {:className "fa fa-plus"})
      (dom/div #js {:className "input-overlay"
                    })
      (dom/input #js {:id "color-add-button"
                      :type "color"
                      :value "#000000"
                      :onChange #(om/update-state! this update :editing-rgb (.. % -target -value))
                      })
      )))

(def color-add (om/factory ColorAdd))

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
        (apply dom/div #js {:className "palette-colors"}
          (conj (mapv color list)
            (color-add)))
        
        ))))

(def palette (om/factory Palette))
