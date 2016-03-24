(ns komokio.components.widgets
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [komokio.components.codedisplay :refer [CodeDisplay]]
            [komokio.components.sidebar :refer [Sidebar]]))

;; TODO if widgets doesn't fix the update issue then just use the root component for now
(defui Widgets
  static om/IQuery
  (query [this]
    [{:sidebar (om/get-query Sidebar)}
     {:code-display (om/get-query CodeDisplay)}]))

(def sidebar (om/factory Sidebar))
