(ns koeeoadi.themebuilder
  (:require [koeeoadi.reconciler :refer [reconciler]]
            [koeeoadi.util :refer [dark?]]
            [cljs.reader :as reader]))

;;; General
(defn sub-color [{:keys [:face/color-bg :face/color-fg] :as face}]
  (let [rec @reconciler]
    (assoc face
      :face/color-bg (:color/hex (get-in rec color-bg))
      :face/color-fg (:color/hex (get-in rec color-fg)))))

(defn sub-colors [faces]
  (map sub-color faces))

(defn filter-by-editor [editor user-faces]
  (filter #(= editor (:face/editor %)) user-faces))

(defn inject-bg-color-ident [faces-by-name]
  "Injects the bg color ident from the background face into the
  default face/colo-rbg property"
  (let [bg-color-ident (get-in faces-by-name ["background" :face/color-fg])]
    (assoc-in faces-by-name ["default" :face/color-bg] bg-color-ident)))

(defmulti build-theme
  "Build the current theme file for a given editor"
  (fn [editor] editor))

;;; EMACS

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
(defmethod build-theme :emacs
  [_]
  (let [{current-theme      :theme/name
         faces-by-name      :faces/by-name
         user-faces-by-name :user-faces/by-name} @reconciler
        face-specs   (xform-faces-emacs faces-by-name user-faces-by-name)]
    (emacs-theme face-specs)))

;;; VIM
(defn vim-set-bg [hex]
  (str "set background=" (if (dark? hex) "dark" "light")))

(def vim-highlight-init
  ["if version > 580"
   "  hi clear"
   "  if exists(\"syntax_on\")"
   "    syntax reset"
   "  endif"
   "endif"
   ""])

(def vim-face-map
  {"Comment"     "comment-delimiter"
   "Conditional" "keyword"
   "Constant"    "constant"
   "Define"      "preprocessor"
   "Exception"   "keyword"
   "Function"    "function-name"
   "Identifier"  "variable-name"
   "Include"     "preprocessor"
   "Keyword"     "keyword"
   "Label"       "keyword"
   "Macro"       "preprocessor"
   "Normal"      "default"
   "PreCondit"   "preprocessor"
   "PreProc"     "preprocessor"
   "Repeat"      "keyword"
   "String"      "string"
   "Type"        "type"})

(defn vim-position-normal [face-names]
  "Moves the Normal syntax name to the beginning.  Needed for VIM
   to set colors properly"
  (sort-by #(not= "Normal" %) face-names))

(defn vim-build-style [style-type prop-separator styles]
  (let [styles' (filter identity styles)]
    (if (empty? styles')
      ""
      (str style-type prop-separator (clojure.string/join "," styles')))))

(defn vim-build-syntax-config-line
  [{:keys [face/name face/color-bg face/color-fg
           face/bold face/italic face/underline]}]
  (clojure.string/join " "
    [(vim-build-style "hi"    " " [name])
     (vim-build-style "gui"   "=" [(if bold "bold" nil)
                                   (if italic "italic" nil)
                                   (if underline "underline" nil)])
     (vim-build-style "guifg" "=" [color-fg])
     (vim-build-style "guibg" "=" [color-bg])]))

(defn vim-build-face [vim-face-name faces-by-name]
  (let [face (get faces-by-name (get vim-face-map vim-face-name))]
    (assoc face :face/name vim-face-name)))

(defn vim-theme-title []
  (let [name (get @reconciler :theme/name)]
    ;; TODO insert version number here and in emacs/st themes
    (str "\"" name " - Created with Koeeoadi")))

(defn vim-xform-faces []
  (let [rec            @reconciler
        faces-by-name  (:faces/by-name rec)
        faces-by-name' (inject-bg-color-ident faces-by-name)
        faces          (map #(vim-build-face % faces-by-name') (vim-position-normal (keys vim-face-map)))
        user-faces     (filter-by-editor :vim (vals (:user-faces/by-name rec)))
        all-faces      (sub-colors (concat faces user-faces))]
    (map vim-build-syntax-config-line all-faces)))

(defmethod build-theme :vim
  [_]
  (let [rec        @reconciler
        set-bg     (->> (get-in rec [:faces/by-name "background" :face/color-fg])
                     (get-in rec)
                     :color/hex
                     vim-set-bg)
        face-specs (vim-xform-faces)]
    (clojure.string/join "%0A"
      (concat
        [(vim-theme-title)
         ""
         set-bg
         ""]
        vim-highlight-init
        face-specs))))

(defn build-sublime-text []
  "stub")
