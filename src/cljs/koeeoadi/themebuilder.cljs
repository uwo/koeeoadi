(ns koeeoadi.themebuilder
  (:require [koeeoadi.reconciler :refer [reconciler]]
            [cljs.pprint :as pprint]
            [cljs.reader :as reader]))

(defn face-to-emacs [face-name]
  (cond (= face-name "default")
        "default"
        :else
        (str "font-lock-" face-name "-face")))

(defn emacs-theme [name faces-by-name colors-by-id]
  (let [doc    (pr-str "A theme created with Koeeoadi")
        bg-rgb (pr-str (let [[_ color-id] (get-in faces-by-name ["background" :face/color])]
                         (get-in colors-by-id [color-id :color/rgb])))]
    (letfn [(spec
              ([face-name]
               (spec face-name :foreground))

              ([face-name property]
               (let [face-name-emacs (face-to-emacs face-name)
                     face            (get faces-by-name face-name)
                     fg-color-id     (last (:face/color-fg face))
                     bg-color-id     (last (:face/color-bg face))
                     fg-rgb          (pr-str (get-in colors-by-id [fg-color-id :color/rgb]))
                     bg-rgb          (pr-str (get-in colors-by-id [bg-color-id :color/rgb]))]
                 ;; TODO This is gross because cljs doesn't support interning
                 ;; Need to cleanup
                 (if (= face-name "default")
                   `(~'read ~(str
                               "(default  ((t (:foreground " fg-rgb " :background " bg-rgb ")))))"))
                   `(~'read ~(str
                               "(" face-name-emacs " ((t (" property " " fg-rgb "))))"))))))]

      `(~'progn
        (~'eval (~'read ~(str "(deftheme " name " " doc ")")))
        ;;(~'eval (~'deftheme ~name ~doc))
        ;; adds someproperties to that theme
        (~'put (~'read ~(str name)) (quote ~'theme-immediate) ~'t)
        ;; executes in color scope
        (~'custom-theme-set-faces (~'read ~(str name))
         ~(spec "default")
         ~(spec "builtin")
         ~(spec "comment-delimiter")
         ~(spec "comment")
         ~(spec "constant")
         ~(spec "doc")
         ~(spec "doc-string")
         ~(spec "function-name")
         ~(spec "keyword")
         ~(spec "negation-char")
         ~(spec "preprocessor")
         ~(spec "string")
         ~(spec "type")
         ~(spec "variable-name")
         (~'provide-theme (~'read ~(str name))))))))


;;'(font-lock-warning-face ((t (:foreground "yellow" :background ... :italic ... :weight ... ))))
(defn font-lock-faceify [[face-name props :as pair]]
  (let [face-name' (str "font-lock-" face-name "-face")]
    (if (= face-name "default")
      pair
      (vector face-name' (assoc props :face/name face-name')))))

(defn xform-prop-emacs [[k v]]
  (when v
    (let [rec @reconciler]
      (case (name k)
        "color-fg"  `(:foreground ~(:color/rgb (get-in rec v)))
        "color-bg"  `(:background ~(:color/rgb (get-in rec v)))
        "bold"      `(:weight     ~'bold)
        "italic"    `(:italic     ~'t)
        "underline" `(:underline  ~'t)
        nil))))

(defn xform-face-emacs [{:keys [face/name] :as face}]
  ;; TODO could use transducers here to optimize
  (let [plist (filter identity (map xform-prop-emacs face))]
    `(~(reader/read-string name) ((~'t ~(flatten plist))))))

(defn xform-faces-emacs [default-faces-map user-faces-map]
  (let [default-faces-map' (into {} (map font-lock-faceify default-faces-map))
        bg-color           (get-in default-faces-map ["background" :face/color-fg])]
    (map xform-face-emacs (-> default-faces-map'
                            ;; remove background face
                            (dissoc "background")
                            ;; inject original bg color into default face (emacs sets its background through the default face)
                            (assoc-in ["default" :face/color-bg] bg-color)
                            ;; to list
                            vals
                            ;; combine with the user defined emacs faces
                            (concat (filter #(= :emacs (:face/editor %)) (vals user-faces-map)))))))

;; (pprint/pprint (xform-default-faces-emacs (:faces/by-name @reconciler) (:user-faces/by-name @reconciler)))

;; TODO use a multimethod here
(defn build-emacs []
  (let [{current-theme      :theme/name
         faces-by-name      :faces/by-name
         user-faces-by-name :user-faces/by-name} @reconciler

        user-faces-by-name'  (collect-user-faces-emacs user-faces-by-name)
        xformed-faces-list   (xform-faces-emacs (merge user-faces-by-name faces-by-name))]
    (pr-str (emacs-theme current-theme faces-by-name' colors-by-id))))


(defn build-vim []
  "stub")

(defn build-sublime-text []
  "stub")
