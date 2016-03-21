(ns komokio.components.faceeditor
  (:require [goog.dom :as gdom]
            [goog.dom.classes :as gclasses]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]
            [cljs.core.async :refer [timeout mult tap untap put! chan <! >!]]

            [komokio.components.palette :refer [Color]]
            [komokio.config :as config]
            [komokio.util :as util]))

;; TODO delete this when I move palette picker to it's own component
(declare Face)

(defn faceColorClick [_ comp face-property]
  (let [face (om/props comp)
        child-no    (if (= :face/foreground face-property) 0 1)
        coordinates (-> (face-option-elm (dom/node comp) child-no)
                      gdom/getElement
                      gstyle/getPosition
                      cljs-coordinates)]
    (om/transact! comp `[(palette-picker/update
                           {:palette-picker/active-face          ~face
                            :palette-picker/active-face-property ~face-property
                            :palette-picker/coordinates          ~coordinates}) :palette-picker])))

(defn face-color [comp face-property]
  (let [{:keys [db/id color/rgb color/name] :as color}  (get (om/props comp) face-property)
        {:keys [editing?] :as state} (om/get-state comp)]

    (dom/div
      #js {:style     #js {:backgroundColor rgb}
           :tabIndex  "0"
           :className "color color-trigger"
           :onClick   #(faceColorClick % comp face-property)
           :onBlur    #(om/transact! comp `[(palette-picker/update
                                              {:palette-picker/active-face nil
                                               :palette-picker/active-face-property nil
                                               :palette-picker/coordinates nil}) :palette-picker/coordinates])})))

(defui Face
  static om/Ident
  (ident [this {:keys [face/name]}]
    [:faces/by-name name])

  static om/IQuery
  (query [this]
    `[:db/id
      :face/name
      {:face/background ~(om/get-query Color)}
      {:face/foreground ~(om/get-query Color)}])

  Object
  (componentDidUpdate [this prev-props prev-state]
    (let [{:keys [face/name
                  face/background
                  face/foreground]}  (om/props this)

          bg-color (:color/rgb background)
          fg-color (:color/rgb foreground)]
      (util/update-code-css name "color"      fg-color)
      (util/update-code-css name "background" bg-color)))

  (render [this]
    (let [{id         :db/id
           face-name  :face/name
           bg         :face/background
           fg         :face/foreground
           :as props} (om/props this)]

      (dom/div #js {:className "face-container"}
        (dom/div #js {:className "face"}
          (dom/span #js {:className "face-name"} (clojure.core/name face-name))
          (face-color this :face/foreground)
          (face-color this :face/background))))))

(def face (om/factory Face {:keyfn :db/id}))
