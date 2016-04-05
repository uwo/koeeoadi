(ns koeeoadi.themebuilder
  (:require [koeeoadi.reconciler :refer [reconciler]]
            [cljs.reader :as reader]))

(defn face-to-emacs [face-name]
  (cond (= face-name "default")
        "default"
        :else
        (str "font-lock-" face-name "-face")))

(defn emacs-theme [face-specs]
  (let [rec           @reconciler
        name           (:theme/name rec)
        name-sym       (reader/read-string name)
        name-sym-quote (reader/read-string (str "'" name))
        doc      "A theme created with Koeeoadi"]
    `(~'progn
      (~'deftheme ~name-sym ~doc)
      (~'put ~name-sym-quote (~'quote ~'theme-immediate) ~'t)
      (~'custom-theme-set-faces ~name-sym-quote ~@face-specs)
      (~'provide-theme ~name-sym-quote))))

(defn font-lock-faceify [[face-name props :as pair]]
  (let [face-name' (str "font-lock-" face-name "-face")]
    (if (= face-name "default")
      pair
      (vector face-name' (assoc props :face/name face-name')))))

(defn xform-prop-emacs [[k v]]
  (when v
    (let [rec @reconciler]
      (case (name k)
        "color-fg"  `(:foreground ~(:color/hex (get-in rec v)))
        "color-bg"  `(:background ~(:color/hex (get-in rec v)))
        "bold"      `(:weight     ~'bold)
        "italic"    `(:italic     ~'t)
        "underline" `(:underline  ~'t)
        nil))))

(defn xform-face-emacs [{:keys [face/name] :as face}]
  ;; TODO could use transducers here to optimize
  (let [plist (filter identity (map xform-prop-emacs face))]
    (reader/read-string (str "'" (pr-str `(~(reader/read-string name) ((~'t ~(flatten plist)))))))))

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

;; TODO use a multimethod here
(defn build-emacs []
  (let [{current-theme      :theme/name
         faces-by-name      :faces/by-name
         user-faces-by-name :user-faces/by-name} @reconciler
        face-specs   (xform-faces-emacs faces-by-name user-faces-by-name)]
    (emacs-theme face-specs)))

(defn build-vim []
  "stub")

(defn build-sublime-text []
  "stub")
