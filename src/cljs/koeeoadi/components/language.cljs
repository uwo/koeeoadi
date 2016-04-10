(ns koeeoadi.components.language
  (:require [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]
            [koeeoadi.components.code :refer [CodeChunk]]
            [koeeoadi.util :as util]))

(defn code-change [comp code-map e]
  (let [new-code  (.. e -target -value)
        code      (get code-map new-code)
        code-norm (om/tree->db [{:code-chunks/list (om/get-query CodeChunk)}] code true)]
    (om/transact! comp `[(code/change ~(assoc code-norm :mutate/name 'code/change)) :palette])))

(defn code-option [{:code-option-name :code/name} code-name]
  (dom/option
    #js {:value    code-option-name
         :selected (= code-name code-option-name)}
    code-option-name))

(defui Language
  static om/IQuery
  (query [this]
    '[[:code/map _]
      [:code/name _]])

  Object
  (render [this]
    (let [{code-map  :code/map
           code-name :code/name :as props} (om/props this)]
      (dom/div #js {:className  "widget"
                    :id         "code-picker"
                    :onChange   #(code-change this code-map %)}
        (util/widget-title "Language")
        (dom/label nil "Choose language:")
        (apply dom/select nil
          (map #(code-option % code-name) (vals code-map)))))))

(def language (om/factory Language))
