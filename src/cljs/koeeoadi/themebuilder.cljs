(ns koeeoadi.themebuilder
  (:require [koeeoadi.config :refer [app-state]]
            [cljs.pprint :as pprint]))

(defn face-to-emacs [face-name]
  (cond (= face-name "default")
        "default"
        :else
        (str "font-lock-" face-name "-face")))

(defn emacs-theme [name faces-by-name colors-by-id]
  ;;'(font-lock-warning-face ((t (:foreground "yellow"))))
  (let [doc    (pr-str "A theme created with Koeeoadi")
        bg-rgb (pr-str (let [[_ color-id] (get-in faces-by-name ["background" :face/color])]
                         (get-in colors-by-id [color-id :color/rgb])))]
    (letfn [(spec
              ([face-name]
               (spec face-name :foreground))

              ([face-name property]
               (let [face-name-emacs (face-to-emacs face-name)
                     face            (get faces-by-name face-name)
                     face-color-id   (last (:face/color face))
                     fg-rgb          (pr-str (get-in colors-by-id [face-color-id :color/rgb]))]
                 ;; TODO This is gross because cljs doesn't support interning
                 ;; Need to cleanup
                 (if (= face-name "default")
                   `(~'read ~(str
                               "(default  ((t (:foreground " fg-rgb " :background " bg-rgb ")))))"))
                   `(~'read ~(str
                               "(" face-name-emacs " ((t (" property " " fg-rgb "))))"))))))]

      `(~'progn
        (~'eval (~'read ~(str "(deftheme " name " " doc ")")))
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
         ~(spec "regexp-grouping-backslash")
         ~(spec "regexp-grouping-construct")
         ~(spec "string")
         ~(spec "type")
         ~(spec "variable-name")
         ~(spec "warning"))
        (~'provide-theme (~'read ~(str name)))))))

;; TODO use a multimethod here
(defn build-emacs []
  (let [{current-theme :theme/name
         faces-by-name :faces/by-name
         colors-by-id  :colors/by-id} @app-state]
    (pr-str (emacs-theme current-theme faces-by-name colors-by-id))))

(defn build-vim []
  "stub")

(defn build-sublime-text []
  "stub")
