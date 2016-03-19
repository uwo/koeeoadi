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

(defn get-colors [state]
  (let [st @state]
    ;; TODO figure out how transducers work
    (into [] (map #(get-in st %)) (get st :colors/list))))

(defmethod read :colors/list
  [{:keys [state] :as env} _ _]
  {:value (get-colors state)})

(defmethod read :color-options/list
  [{:keys [state] :as env} _ _]
  {:value (get-colors state)})

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
  {:value (get-faces state k)})

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

(defmethod mutate 'face/update
  [{:keys [state ref] :as env} _ {:keys [name bg-or-fg color] :as args}]
  {:value {:keys [:faces/list]}
   :action
   (fn []
     (swap! state update-in [:faces/by-name name] assoc bg-or-fg [:colors/by-name (:color/name color)]))})

(def parser (om/parser {:read read :mutate mutate}))
