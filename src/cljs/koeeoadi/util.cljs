(ns koeeoadi.util
  (:require [goog.dom :refer [getElementsByTagNameAndClass $$]]
            [goog.style :refer [setStyle getClientPosition]]
            [goog.array :refer [forEach]]
            [om.next :as om]
            [om.dom :as dom]))

(defn profile-start []
  (.profile js/console "Profile Run"))

(defn profile-stop []
  (.profileEnd js/console))

(defn keycode [e]
  (.-keyCode e))

(defn target-checked [e]
  (.. e -target -checked))

(defn target-value [e]
  (.. e -target -value))

(defn option [name selected-name]
  (let [selected (= name selected-name)]
    (dom/option #js {:selected selected} name)))

(defn switch-coordinates [coords]
  {:x (aget coords "y")
   :y (aget coords "x")})

(defn palette-picker-show [comp face color-type e]
  (.preventDefault e)
  (.stopPropagation e)
  (let [coordinates (-> (getClientPosition e)
                      switch-coordinates)]
    (om/transact! comp `[(palette-picker/update
                           {:palette-picker/active-face ~face
                            :color-type                 ~color-type
                            :palette-picker/coordinates ~coordinates})])))

(defn palette-picker-hide [comp]
  (om/transact! comp `[(palette-picker/update
                         {:palette-picker/active-face nil
                          :palette-picker/coordinates nil}) :faces/list]))

(defn valid-face-name? [str]
  (re-matches #"^[0-9a-zA-Z\-]+$" str))

(defn valid-file-name? [str]
  (re-matches #"^[\w\-. ]+$" str))

(def code-class "code")

(defn code-face-class [face-name]
  (str code-class "-" (name face-name)))

(defn update-other-code-face-elements [face-name func]
  ;; TODO find the non deprecated $$ i should be using
  (let [code ($$ (str ".code:not(." (code-face-class face-name) ")"))]
    (forEach code #(func %))))

(defn update-code-face-elements [face-name func]
  (let [code (getElementsByTagNameAndClass nil (code-face-class face-name))]
    (forEach code #(func %))))

(defn update-code-elements [func]
  (let [code (getElementsByTagNameAndClass nil code-class)]
    (forEach code #(func %))))
