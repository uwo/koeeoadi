(ns koeeoadi.components.codepicker
  (:require [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]

            [koeeoadi.components.codedisplay :refer [CodeChunk code-display-comp]]))

(defn code-change [code-map e]
  (let [new-code  (.. e -target -value)
        code      (get code-map new-code)
        code-norm (om/tree->db [{:code-chunks/list (om/get-query CodeChunk)}] code true)]
    (om/transact! (code-display-comp) `[(code/change ~code-norm)])))

(defn code-option [{:code-option-name :code/name} code-name]
  (dom/option
    #js {:value    code-option-name
         :selected (= code-name code-option-name)}
    code-option-name))

(defn code-picker [{code-map  :code/map
                    code-name :code/name :as props}]
  (dom/div #js {:className "widget"
                :id        "code-picker"
                :onChange  #(code-change code-map %)}
    (dom/h5 #js {:className "widget-title"} "Language")
    (dom/label nil "Current language:")
    (apply dom/select nil
      (map #(code-option % code-name) (vals code-map)))))
