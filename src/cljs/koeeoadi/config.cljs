(ns koeeoadi.config
  (:require
   [om.next :as om]

   [koeeoadi.code :refer [code]]
   [koeeoadi.themes :refer [themes]]))


(def editor-file-map
  {:emacs        "el"
   :vim          "vim"
   :sublime-text "sublime-theme"})

(def colors-list
  [{:color/id 0 :color/rgb "#6c71c4"}
   {:color/id 1 :color/rgb "#586e75"}
   {:color/id 2 :color/rgb "#268bd2"}
   {:color/id 3 :color/rgb "#d33682"}
   {:color/id 4 :color/rgb "#b58900"}
   {:color/id 5 :color/rgb "#859900"}
   {:color/id 6 :color/rgb "#2aa198"}
   {:color/id 7 :color/rgb "#dc322f"}
   {:color/id 8 :color/rgb "#073642"}
   {:color/id 9 :color/rgb "#93a1a1"}])

(def faces-list
  [{:face/id 0 :face/name "default"                     :face/color-fg {:color/id 9  :color/rgb  "#93a1a1"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 1 :face/name "background"                  :face/color-fg {:color/id 8  :color/rgb  "#073642"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 2 :face/name "builtin"                     :face/color-fg {:color/id 0  :color/rgb  "#6c71c4"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 3 :face/name "comment-delimiter"           :face/color-fg {:color/id 1  :color/rgb  "#586e75"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 4 :face/name "comment"                     :face/color-fg {:color/id 1  :color/rgb  "#586e75"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 5 :face/name "constant"                    :face/color-fg {:color/id 2  :color/rgb  "#268bd2"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 6 :face/name "doc"                         :face/color-fg {:color/id 3  :color/rgb  "#d33682"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 7 :face/name "doc-string"                  :face/color-fg {:color/id 0  :color/rgb  "#6c71c4"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 8 :face/name "function-name"               :face/color-fg {:color/id 4  :color/rgb  "#b58900"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 9 :face/name "keyword"                     :face/color-fg {:color/id 5  :color/rgb  "#859900"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 10 :face/name "negation-char"              :face/color-fg {:color/id 2  :color/rgb  "#268bd2"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 11 :face/name "preprocessor"               :face/color-fg {:color/id 3  :color/rgb  "#d33682"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 12 :face/name "regexp-grouping-backslash"  :face/color-fg {:color/id 0  :color/rgb  "#6c71c4"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 13 :face/name "regexp-grouping-construct"  :face/color-fg {:color/id 3  :color/rgb  "#d33682"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 14 :face/name "string"                     :face/color-fg {:color/id 0  :color/rgb  "#6c71c4"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 15 :face/name "type"                       :face/color-fg {:color/id 2  :color/rgb  "#268bd2"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 16 :face/name "variable-name"              :face/color-fg {:color/id 0  :color/rgb  "#6c71c4"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 17 :face/name "warning"                    :face/color-fg {:color/id 2  :color/rgb  "#268bd2"} :face/underline nil :face/bold nil :face/italic nil}])

(def initial-code "c")

;; TODO Initialize this using theme/data
(def app-state
  {:custom-faces/map  {}
   :code/name         initial-code
   :theme/name        "solarized-dark"
   :code-background   [:faces/by-name "background"]
   :theme/map         themes
   :code/map          code
   :faces/list        faces-list
   :colors/list       colors-list
   :code-chunks/list  (get-in code [initial-code :code-chunks/list])})
