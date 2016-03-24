(ns komokio.core
  (:require [goog.array :as garray]
            [goog.dom :as gdom]
            [goog.style :as gstyle]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [clojure.string :as string]
            [cljs.pprint :as pprint]

            [devtools.core :as devtools]

            [komokio.config :as config]
            [komokio.parser :refer [parser]]
            [komokio.components.widgets :refer [Widgets]]
            [komokio.components.sidebar :refer [Sidebar sidebar]]
            [komokio.components.codedisplay :refer [code-display CodeChunk CodeDisplay]]
            [komokio.components.faceeditor :refer [Face]]
            [komokio.components.palette :refer [Color]]))

;; TODO separate out this dev stuff
(enable-console-print!)
                                        ; this enables additional features, :custom-formatters is enabled by default
(devtools/enable-feature! :sanity-hints :dirac)
(devtools/install!)
(.log js/console (range 0 200))

(defui Root
  static om/IQuery
  (query [this]
    [{:data [{:code-chunks/list (om/get-query CodeChunk)}
             {:colors/list (om/get-query Color)}
             {:faces/list (om/get-query Face)}]}
     {:widgets (om/get-query Widgets)}])

  Object
  (render [this]
    (.log js/console (om/props this))
    (let [{sidebar-data :sidebar code-display-data :code-display} (:widgets (om/props this))]
      (dom/div nil
        (code-display code-display-data)
        (sidebar sidebar-data)))))

(defonce reconciler
  (om/reconciler
    {:normalize true
     :state (atom config/app-state)
     :parser parser}))

(om/add-root! reconciler
  Root (gdom/getElement "app"))

;; (let [as (om/tree->db Root config/app-state true)
;;                         code (om/tree->db CodeDisplay {:code-chunks/list config/code-elisp} true)
;;                         code-by-line-chunk (:code-chunks/by-line-chunk (om/tree->db CodeDisplay {:code-chunks/list config/code-elisp} true))
;;                         code-norm (:code-chunks/list code)
;;                         ;; possibly get rid of
;;                         code-tables (:om.next/tables code)]
;;                     (assoc (merge-with merge
;;                              as
;;                              {:code-chunks/by-line-chunk code-by-line-chunk}
;;                              {:data {:code-chunks/list code-norm}})
;;                       :om.next/tables
;;                       #{:colors/by-name :faces/by-name :code-chunks/by-line-chunk}))
