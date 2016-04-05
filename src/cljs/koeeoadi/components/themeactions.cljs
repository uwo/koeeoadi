(ns koeeoadi.components.themeactions
  (:require [goog.dom :as gdom]
            [goog.events :as gevents]
            [cljs.reader :as reader]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [cljs.pprint :as pprint]

            [koeeoadi.reconciler :refer [reconciler]]
            [koeeoadi.util :as util]
            [koeeoadi.config :as config]
            [koeeoadi.themebuilder :as tb]))


(defn select-theme [comp new-theme]
  (om/transact! comp `[(theme/change ~new-theme) :theme/map]))

;; TODO This is slow because of the follow read
;; could probably use local component state to update
;; the themeactions component and update everything
;; when the input loses focus
(defn new-theme [comp]
  (om/transact! comp `[(theme/new) :theme/map]))

;; TODO consider saving a compressed file
(defn save-theme-file [comp current-theme]
  (let [link (gdom/getElement "download-link")]
    (aset link "download" (str current-theme ".koee"))
    ;; TODO cleanup
    (aset link "href" (str
                        "data:text/plain;charset=utf-8;base64,"
                        (.btoa js/window
                          (pr-str
                            (select-keys @reconciler [:theme/name
                                                      :colors/by-id
                                                      :colors/list
                                                      :faces/by-name
                                                      :faces/list
                                                      :user-faces/list
                                                      :user-faces/by-name])))))
    (.click link)))

(defn export-theme-file [current-theme editor]
  (let [link      (gdom/getElement "download-link")
        extension (editor config/editor-file-map)]
    (aset link "download" (str current-theme "-theme." extension))
    (aset link "href" (str "data:text/plain;charset=utf-8," (tb/build-theme editor)))
    (.click link)))

;; TODO figure out how this works
(defn file-list-to-cljs [js-col]
  (-> (clj->js [])
    (.-slice)
    (.call js-col)
    (js->clj)))

(defn load-theme [comp e]
  (let [result        (.. e -target -result)
        encoded-theme (aget (.split result ",") 1)
        decoded-theme (.atob js/window encoded-theme)
        theme         (reader/read-string decoded-theme)]
    (om/transact! comp `[(theme/load ~theme) :theme/map])))

(defn read-theme-file [comp e]
  (let [file-list   (file-list-to-cljs (.. e -target -files))
        file        (first file-list)
        file-reader (js/FileReader.)]
    (when file
      ;; TODO Add some kind of error handling
      ;; TODO look into why I could use set! instead
      (aset file-reader "onload" #(load-theme comp %))
      (.readAsDataURL file-reader file))))

(defn load-theme-file [e]
  (let [input (gdom/getElement "file-input")]
    (.click input)))

(defn export-theme []
  (println "stub"))

(defn handle-name-change [comp new-name prev-name]
  (om/transact! comp `[(theme/change-name {:new-name  ~new-name
                                           :prev-name ~prev-name})]))

;; TODO needs a serious refactor
(defui ThemeActions
  static om/IQuery
  (query [this]
    [:theme/name-temp
     :theme/name
     :theme/map])

  Object
  (componentDidUpdate [this prev-props prev-state]
    (when (om/get-state this :needs-focus)
      (let [node (dom/node this "editField")
            len  (.. node -value -length)]
        (.focus node)
        (.setSelectionRange node len len))
      (om/update-state! this assoc :needs-focus nil)))

  (render [this]
    (let [{current-theme :theme/name
           name-temp     :theme/name-temp
           theme-map     :theme/map} (om/props this)]
      (dom/div #js {:className "widget" :id "actions"}
        (dom/h5 #js {:className "widget-title"} "Theme")
        (dom/label nil "Choose theme:")
        (dom/div #js {:className "row control-row"}
          (apply dom/select
            #js {:id "theme-select"
                 :onChange #(select-theme
                              ;; TODO CLENAUP
                              this
                              (assoc (get theme-map (.. % -target -value))
                                :theme/name (.. % -target -value)))
                 :style    (util/display (not name-temp))}
            (map #(util/option % current-theme) (keys theme-map)))

          (dom/input #js {:id          "theme-name-input"
                          :onBlur      (fn [e]
                                         (let [input-text (.. e -target -value)]
                                           (when (util/valid-file-name? input-text)
                                             (handle-name-change this input-text current-theme))))
                          :onChange    (fn [e]
                                         (let [input-text (.. e -target -value)]
                                           (om/transact! this
                                             `[(theme/edit-name {:theme/name-temp ~input-text})])))
                          :onKeyDown   (fn [e]
                                         (let [keycode    (util/keycode e)
                                               input-text (.. e -target -value)]
                                           (when (and
                                                   (util/valid-file-name? input-text)
                                                   (= keycode 13))
                                             (handle-name-change this input-text current-theme))))
                          :placeholder current-theme
                          :ref         "editField"
                          :value       (or name-temp current-theme)
                          :style       (util/display name-temp)})

          (dom/button #js {:className "inline-button"
                           :id        "theme-name-edit-button"
                           :onClick   (fn [_]
                                        (om/transact! this `[(theme/edit-name {:theme/name-temp ~current-theme})])
                                        (om/update-state! this assoc :needs-focus true))}

            (dom/i #js {:className "fa fa-edit fa-2x"})))

        ;; TODO refactor into a theme action method
        (dom/div #js {:className "row control-row"}
          (dom/div #js {:className "one-third column"}
            (dom/button #js {:className "inline-button"
                             :onClick #(new-theme this)}

              (dom/i #js {:className "fa fa-file fa-3x"})))
          (dom/div #js {:className "one-third column"}
            (dom/button #js {:className "inline-button" :onClick #(save-theme-file this current-theme)}
              (dom/i #js {:className "fa fa-save fa-3x"})))
          (dom/div #js {:className "one-third column"}
            (dom/input  #js {:type "file"
                             :id "file-input"
                             :accept ".koee"
                             :onChange #(read-theme-file this %)
                             :style #js {:display "none"}})
            (dom/button #js {:className "inline-button" :onClick load-theme-file}
              (dom/i #js {:className "fa fa-upload fa-3x"}))))

        (dom/label nil "Export to:")
        (dom/button #js {:className "export-button"
                         :onClick #(export-theme-file current-theme :emacs)} "Emacs (GUI only)")

        (dom/button #js {:className "export-button"
                         :onClick #(export-theme-file current-theme :vim)} "Vim (GUI only)")

        ;;;; Wishful thinking
        ;; (dom/button #js {:className "export-button"
        ;;                  :onClick #(tb/build-sublime-text)} "Sublime Text")
        ))))

(def theme-actions (om/factory ThemeActions))
