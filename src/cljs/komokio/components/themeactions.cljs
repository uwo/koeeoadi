(ns komokio.components.themeactions
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [komokio.themes :refer [themes]]))

(defn select-theme [comp theme]
  (println "in select-theme")
  (println theme)
  (om/transact! comp `[(theme/change {:theme ~theme}) :widgets]))

(defn save-theme []
  (println "stub"))

(defn export-theme []
  (println "stub"))

(defn theme-option [theme-name]
  (dom/option #js {:onHover #()} theme-name))

(defui ThemeActions
  static om/IQuery
  (query [this]
    [:base-theme])

  Object
  (render [this]
    (let [{:keys [base-theme]} (om/props this)]
      (println "in theme actions")
      (println base-theme)
      (dom/div #js {:id        "actions"
                    :className "widget"}
        (dom/h5 nil "Theme")
        (dom/label nil "Base theme:" 
          (apply dom/select
            #js {:id "theme-select"
                 :onChange #(select-theme
                              this
                              (get themes (.. % -target -value)))}
            (map theme-option (keys themes))))))))

(def theme-actions (om/factory ThemeActions))

