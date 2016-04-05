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
  [{:color/id 0 :color/hex "#6c71c4"}
   {:color/id 1 :color/hex "#586e75"}
   {:color/id 2 :color/hex "#268bd2"}
   {:color/id 3 :color/hex "#d33682"}
   {:color/id 4 :color/hex "#b58900"}
   {:color/id 5 :color/hex "#859900"}
   {:color/id 6 :color/hex "#2aa198"}
   {:color/id 7 :color/hex "#dc322f"}
   {:color/id 8 :color/hex "#073642"}
   {:color/id 9 :color/hex "#93a1a1"}])

(def faces-list
  [{:face/id 0 :face/name "background"                  :face/color-fg {:color/id 8  :color/hex  "#073642"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 1 :face/name "default"                     :face/color-fg {:color/id 9  :color/hex  "#93a1a1"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 2 :face/name "builtin"                     :face/color-fg {:color/id 0  :color/hex  "#6c71c4"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 3 :face/name "comment"                     :face/color-fg {:color/id 1  :color/hex  "#586e75"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 4 :face/name "comment-delimiter"           :face/color-fg {:color/id 1  :color/hex  "#586e75"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 5 :face/name "constant"                    :face/color-fg {:color/id 2  :color/hex  "#268bd2"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 6 :face/name "doc"                         :face/color-fg {:color/id 3  :color/hex  "#d33682"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 7 :face/name "function-name"               :face/color-fg {:color/id 4  :color/hex  "#b58900"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 8 :face/name "keyword"                     :face/color-fg {:color/id 5  :color/hex  "#859900"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 9 :face/name "preprocessor"                :face/color-fg {:color/id 3  :color/hex  "#d33682"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 10 :face/name "string"                     :face/color-fg {:color/id 0  :color/hex  "#6c71c4"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 11 :face/name "type"                       :face/color-fg {:color/id 2  :color/hex  "#268bd2"} :face/underline nil :face/bold nil :face/italic nil}
   {:face/id 12 :face/name "variable-name"              :face/color-fg {:color/id 0  :color/hex  "#6c71c4"} :face/underline nil :face/bold nil :face/italic nil}])

(def user-faces-list
  [{:face/id 0
    :face/name "user-face-1"
    :face/color-fg nil
    :face/color-bg nil
    :face/underline true
    :face/bold true
    :face/italic true
    :face/editor :emacs}])

(def initial-code "c")

;; TODO Initialize this using theme/data
(def app-state
  {:code/name         initial-code
   :theme/name        "solarized-dark"
   :code-background   [:faces/by-name "background"]
   :theme/map         themes
   :code/map          code
   :faces/list        faces-list
   :user-faces/list   user-faces-list
   :colors/list       colors-list
   :code-chunks/list  (get-in code [initial-code :code-chunks/list])})
