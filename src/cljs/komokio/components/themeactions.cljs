(ns komokio.components.themeactions
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [komokio.config :refer [app-state]]
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

(defn theme-export-emacs [comp]
  (.log js/console @app-state))

(defui ThemeActions
  static om/IQuery
  (query [this]
    [:base-theme])

  Object
  (render [this]
    (let [{:keys [base-theme]} (om/props this)]
      (dom/div #js {:id        "actions"
                    :className "widget"}
        (dom/h5 nil "Theme")
        (dom/label nil "Base theme:"
          (apply dom/select
            #js {:id "theme-select"
                 :onChange #(select-theme
                              this
                              ;; TODO CLENAUP
                              (assoc (get themes (.. % -target -value))
                                :base-theme (.. % -target -value)))}
            (map theme-option (keys themes))))
        (dom/button #js {:onClick theme-export-emacs} "Export to Emacs")
        (dom/a #js {:target "_blank"
                    ;:download "blahbla.txt"
                    ;:href (str "data:text/plain;charset=utf-8;base64," (.btoa js/window))
                    } "DOWNLOAD")))))

(def theme-actions (om/factory ThemeActions))
