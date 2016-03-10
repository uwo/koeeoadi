;; normalize data correctly
;; build up a sane UI tree
;; use stateless components where necessary
;; add outlines

(ns komokio.core (:require [goog.array :as garray]
                           [goog.dom :as gdom]
                           [goog.style :as gstyle]
                           [om.next :as om :refer-macros [defui]]
                           [om.dom :as dom]
                           [clojure.string :as string]
                           [cljs.pprint :as pprint]
                           [komokio.parser :refer [parser]]))

;;** Dev
(enable-console-print!)

(def code-clojure "<code><pre><div class=\"code code-comment\">
  ;; this is a comment</div><div class=\"code code-default\">
  This is some default text</div><div class=\"code code-default\">
  This is even more default text</div></pre></code>")

;; {:sidebar {:colors-editor []
;;            :faces-editor []
;;            :faces-by-color []}
;;  :code-display
;;  }

(def app-state
  (atom {
         ;;code widget and faces editor depend on this directly
         :faces/list [{
                       :db/id 100
                       :face/name :default
                       :face/background  {
                                          :db/id 200
                                          :color/name "yellow"
                                          :color/rgb  "#Ffd700"
                                          }
                       :face/foreground {
                                         :db/id 201
                                         :color/name "blue"
                                         :color/rgb  "#1e90ff"
                                         }
                       }

                      {
                       :db/id 101
                       :face/name :comment
                       :face/background {
                                         :db/id 202
                                         :color/name "red"
                                         :color/rgb  "#Cd0000"
                                         }
                       :face/foreground {
                                         :db/id 203
                                         :color/name "green"
                                         :color/rgb  "#2e8b57"
                                         }
                       }]

         ;; TODO rgb property name since they're not RGB values (hsv i think)
         :colors/list [
                       {
                        :db/id 200
                        :color/name "yellow"
                        :color/rgb  "#Ffd700"
                        }

                       {
                        :db/id 201
                        :color/name "blue"
                        :color/rgb  "#1e90ff"
                        }

                       {
                        :db/id 202
                        :color/name "red"
                        :color/rgb  "#Cd0000"
                        }

                       {
                        :db/id 203
                        :color/name "green"
                        :color/rgb  "#2e8b57"
                        }
                       ]
         }))


(defn rc []
  (clojure.string/join (conj (take 6 (repeatedly #(str (int (* 10  (rand)))))) "#"))
  )

;; TODO move to utilities
(defn matching-props [m1 m2]
  "Returns a new map only including the key value pairs that have a
  key in the key sequence, ks, and that reference a value equal to v."
  (let [s1 (into #{} m1)
        s2 (into #{} m2)
        m1' (into {} (clojure.set/intersection s1 s2))]
    (when-not (empty? m1')
      m1')))

;; move to face related
(defn face-matching-props [face m]
  "Returns a new face map that only contains the matching kv pairs in
  map m."
  (let [props (matching-props face m)]
    (when-not (empty? props)
      (merge
        {:face/id  (:db/id face)
         :face/name (:face/name face)}
        props))))

(defn handle-color-change [e comp {:keys [color/name db/id] :as color}]
  (let [rgb-editing (.. e -target -value)
        face-color-updater (om/get-computed comp :face-color-updater)
        color-no-computed (dissoc color :om.next/computed)]
    ;;    (om/update-state! comp assoc :rgb-editing rgb-editing)
    ;;    (om/update-state! comp assoc :color-editing rgb-editing)
    ;; (face-color-updater {:face/background color-no-computed
    ;;                     :face/foreground color-no-computed} rgb-editing)

    (om/transact! comp `[(color/update {:id ~id :name ~name :rgb ~rgb-editing})
                         ])
    ))

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
          {:keys [rgb-editing]} (om/get-state this)]
      (dom/div nil
        (dom/ul nil
          (dom/li nil name)
          (dom/li nil
            (dom/span nil rgb)
            (dom/input #js {:type "color"
                            :value rgb
                            :onClick #(pprint/pprint "color picker clicked")
                            :onChange #(handle-color-change % this color)})))))))

(def color (om/factory Color {:keyfn :db/id}))

(defn factory-with-computed [factory-func props props-computed]
  "Calls factory-func with the combined data of props and props-computed"
  (factory-func (om/computed props props-computed)))

(defn code-class [face-name]
  (str "code-" (name face-name)))

(defn update-code-css [face-name property value]
  (let [code (gdom/getElementsByTagNameAndClass "div" (code-class face-name))
        style (js-obj property value)]
    (garray/forEach code #(gstyle/setStyle % style))))

(defn update-face-color-style [comp]
  (let [props (om/props comp)
        rgb  (:color/rgb props)
        css-updater (:css-updater (om/get-computed props))]
    (css-updater rgb)))

(defui FaceColor
  static om/Ident
  (ident [this {:keys [color/name]}]
    [:colors/by-name name])

  static om/IQuery
  (query [this]
    ;; TODO use shared color query
    [:db/id :color/name :color/rgb])

  Object
  (componentDidUpdate [this prev-props prev-state]
    (update-face-color-style this))

  (render [this]
    (let [{:keys [color/rgb] :as props} (om/props this)]
      (dom/input #js {:type "color" :value rgb :disabled true}))))

(def face-color (om/factory FaceColor))

(defui Face
  static om/Ident
  (ident [this {:keys [face/name]}]
    [:faces/by-name name])

  static om/IQueryParams
  (params [this]
    {:face-color (om/get-query FaceColor)})

  static om/IQuery
  (query [this]
    `[:db/id
      :face/name
      {:face/background ~(om/get-query FaceColor)}
      {:face/foreground ~(om/get-query FaceColor)}])

  Object
  (render [this]
    (let [{id :db/id face-name :face/name
           bg :face/background fg :face/foreground
           :as props} (om/props this)

          cssUpdater (fn [css-property color-rgb]
                       (update-code-css face-name css-property color-rgb))

          bg-computed (om/computed
                        bg
                        {:css-updater (partial cssUpdater "background-color")})

          fg-computed (om/computed
                        fg
                        {:css-updater (partial cssUpdater "color")})]
      (dom/div nil
        (dom/ul nil
          (dom/li nil (clojure.core/name face-name))
          (dom/li nil
            (dom/span nil "Background")
            (face-color bg-computed))
          (dom/li nil
            (dom/span nil "Foreground")
            (face-color fg-computed)))))))

(def face (om/factory Face {:keyfn :db/id}))

(defui FacesEditor
  Object
  (render [this]
    (let [{:keys [faces/list]} (om/props this)]
      (dom/div #js {:id "faces-editor"}
        (dom/h3 nil "Faces Editor")
        (apply dom/div nil (map face list))))))

(def faces-editor (om/factory FacesEditor))

(defui ColorsEditor
  Object
  (render [this]
    (let [{:keys [colors/list]} (om/props this)]
      (dom/div #js {:id "colors-editor"}
        (dom/h3 nil "Colors Editor")
        (apply dom/div nil (map color list))))))

(def colors-editor (om/factory ColorsEditor))

(defui Sidebar
  Object
  (render [this]
    (let [{faces :faces/list
           colors :colors/list
           :as props} (om/props this)]
      (dom/div #js {:id "sidebar"}
        (faces-editor props)
        (colors-editor props)))))

(def sidebar (om/factory Sidebar))

(defui CodeDisplay
  Object
  (componentDidMount [this]
    (let [parent (gdom/getElement "code-display")
          code (gdom/htmlToDocumentFragment code-clojure)]
      (gdom/append parent code)))

  (render [this]
    (dom/div #js {:id "code-display"} "")))

(def code-display (om/factory CodeDisplay))

(defui Root
  static om/IQueryParams
  (params [this]
    {:face (om/get-query Face)
     :colors (om/get-query Color)})

  static om/IQuery
  (query [this]
    '[{:faces/list ?face}
      {:colors/list ?colors}])

  Object
  (componentDidMount [this]
    (om/set-state! this {}))

  (render [this]
    (let [props (om/props this)]
      (dom/div nil
        (sidebar props)
        (code-display props)))))

;;** Reconciler
(def reconciler
  (om/reconciler
    {:normalize true
     :state app-state
     :parser parser}))

;;** App Mounting
(om/add-root! reconciler
  Root (gdom/getElement "app"))
