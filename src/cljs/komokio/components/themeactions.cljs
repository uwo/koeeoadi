(ns komokio.components.themeactions
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [komokio.util :as util]
            [komokio.themebuilder :as tb]))

(defn select-theme [comp new-theme]
  (om/transact! comp `[(theme/change {:new-theme ~new-theme}) :widgets]))

(defn save-theme []
  (println "stub"))

(defn export-theme []
  (println "stub"))

(defn theme-option [theme-name current-theme]
  (let [selected (= theme-name current-theme)]
    (dom/option #js {:selected selected} theme-name)))

(defn handle-name-change [comp new-name prev-name]
  (om/transact! comp `[(theme/change-name {:new-name  ~new-name
                                           :prev-name ~prev-name})]))

(defui ThemeActions
  static om/IQuery
  (query [this]
    [:name-temp
     :current-theme
     :theme/map])

  Object
  (componentDidUpdate [this prev-props prev-state]
    (if  (:name-temp (om/props this))
      (.focus (gdom/getElement "theme-name-input"))))

  (render [this]
    (println "rendering again")
    (let [{current-theme :current-theme
           name-temp     :name-temp
           theme-map     :theme/map} (om/props this)]
      (dom/div #js {:id        "actions"
                    :className "widget"}
        (dom/h5 nil "Theme")
        (dom/label nil "Current theme:"
          (apply dom/select
            #js {:id "theme-select"
                 :className (when name-temp "hide")
                 :onChange #(select-theme
                              this
                              ;; TODO CLENAUP
                              (assoc (get theme-map (.. % -target -value))
                                :current-theme (.. % -target -value)))}
            (map #(theme-option % current-theme) (keys theme-map))))

        (dom/input #js {:id        "theme-name-input"
                        :className (when-not name-temp "hide")
                        :value     (or name-temp current-theme)
                        :placeholder current-theme
                        :onKeyDown (fn [e]
                                     (let [keycode    (util/keycode e)
                                           input-text (.. e -target -value)]
                                       (when (= keycode 13)
                                         (handle-name-change this input-text current-theme))))

                        :onBlur    (fn [e]
                                     (let [input-text (.. e -target -value)]
                                       (handle-name-change this input-text current-theme)))

                        :onChange  (fn [e]
                                     (let [input-text (.. e -target -value)]
                                       (om/transact! this `[(theme/edit-name {:name-temp ~input-text})])))})
        ;; TODO Why won't this reload
        (dom/button #js {:onClick #(om/transact! this `[(theme/edit-name {:name-temp ""})])} "Rename Theme")
        (dom/button #js {:onClick #(tb/build-emacs)} "Export to Emacs")
        ;; (dom/a #js {:target "_blank"
        ;;             ;:download "blahbla.txt"
        ;;             ;:href (str "data:text/plain;charset=utf-8;base64," (.btoa js/window))
        ;;             } "DOWNLOAD")
        ))))

(def theme-actions (om/factory ThemeActions))
