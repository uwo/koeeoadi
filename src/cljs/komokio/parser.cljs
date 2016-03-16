(ns komokio.parser
  (:require [om.next :as om]
            [cljs.pprint :as pprint]

            [komokio.components.palette :refer [Color]]))

;; Readers
(defmulti read om/dispatch)

(defmethod read :default
  [{:keys [state query] :as env} k params]
  (let [st @state] ;; CACHING!!!
    (if (contains? st k)
      {:value (get st k)}
      {:remote true})))

(defn get-colors [state key]
  (let [st @state]
    ;; TODO figure out how transducers work
    (into [] (map #(get-in st %)) (get st key))))

(defmethod read :colors/list
  [{:keys [state] :as env} k params]
  {:value (get-colors state k)})

(defn get-face [state-derefed face-ident]
  (let [face (get-in state-derefed face-ident)
        bg (get-in state-derefed (:face/background face))
        fg (get-in state-derefed (:face/foreground face))]
    (merge face {:face/background bg
                 :face/foreground fg})))

(defn get-faces [state key]
  (let [st @state]
    (into [] (map #(get-face st %)) (get st key))))

(defmethod read :faces/list
  [{:keys [state query] :as env} k params]
  {:value (get-faces state k)}
  )

;; (defmethod read :sidebar
;;   [{:keys [state query] :as env} k params]
;;   (let [faces (get-faces state :faces/list)
;;         colors (get-colors state :colors/list)]
;;     {:value {:faces faces
;;              :colors colors}}))

(defmethod read :faces/by-id
  [{:keys [state query] :as env} k params]
  )

(defmethod read :face/background
  [{:keys [state query] :as env} k params]
  {:value (get-in @state [:colors/by-name "yellow"])})

;;** Mutators
(defmulti mutate om/dispatch)

;; non-local
(defmethod mutate :default
  [_ _ _] {:remote true})

(defmethod mutate 'color/update
  [{:keys [state ref] :as env} _ {:keys [name rgb] :as args}]
  {:value {:keys [:faces/list]}
   :action
   (fn []
     (swap! state update-in [:colors/by-name name] assoc :color/rgb rgb))})

(def parser (om/parser {:read read :mutate mutate}))
