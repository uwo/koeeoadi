(ns komokio.components.widgets
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [komokio.components.palettepicker :refer [PalettePicker palette-picker]]
            [komokio.components.codedisplay :refer [CodeDisplay code-display]]
            [komokio.components.sidebar :refer [Sidebar sidebar]]))

(defui Widgets
  static om/IQuery
  (query [this]
    [{:sidebar (om/get-query Sidebar)}
     {:code-display (om/get-query CodeDisplay)}
     {:palette-picker (om/get-query PalettePicker)}])

  Object
  (render [this]
    (let [{sidebar-data :sidebar
           code-display-data :code-display
           palette-picker-data :palette-picker} (om/props this)]
      (dom/div nil
        (sidebar sidebar-data)
        (code-display code-display-data)
        (palette-picker palette-picker-data)))))

(def widgets (om/factory Widgets))
