(ns koeeoadi.util
  (:require [cljs.reader :refer [read-string]]
            [goog.dom :refer [getElementsByTagNameAndClass]]
            [goog.style :refer [getClientPosition]]
            [goog.array :refer [forEach]]
            [om.next :as om]
            [om.dom :as dom])
  (:import  [goog.dom query]))

(defn widget-title [title]
  (dom/h5 #js {:className "widget-title"} title))

(defn hex-to-rgb [hex]
  "Converts a 6 digit hex number or color string to an RGB map"
  (let [hex'       (if (string? hex) hex (str "#" hex))
        xform-pair (fn [byte]
                     (.parseInt js/window (apply str "0x" byte)))]
    (zipmap
      [:r :g :b]
      (map xform-pair (partition 2 (vec (subs hex 1)))))))

(defn brightness [hex]
  (let [{:keys [r g b]} (hex-to-rgb hex)]
    (.sqrt js/Math (+ (* .299 r) (* .587 g) (* .114 b)))))

;;from: http://alienryderflex.com/hsp.html
(defn dark? [hex]
  (< (brightness hex) 8))

(defn display [pred]
  (when-not pred #js {:display "none"}))

(defn profile-start []
  (.profile js/console "Profile Run"))

(defn profile-stop []
  (.profileEnd js/console))

(defn keycode [e]
  (.-keyCode e))

(defn no-keycode-or-keycode-is-valid? [keycode]
  (if keycode
    (= 13 keycode)
    true))

(defn target-checked [e]
  (.. e -target -checked))

(defn target-value [e]
  (.. e -target -value))

(defn target-color [e]
  (.. e -target -color))

(defn option [name selected-name]
  (let [selected (= name selected-name)]
    (dom/option #js {:selected selected} name)))

(defn switch-coordinates [coords]
  {:x (aget coords "y")
   :y (aget coords "x")})

(defn color-picker-show [comp face color-type e]
  (.preventDefault e)
  (.stopPropagation e)
  (let [coordinates (-> (getClientPosition e)
                      switch-coordinates)]
    (om/transact! comp `[(color-picker/update
                           {:color-picker/active-face ~face
                            :color-type               ~color-type
                            :color-picker/coordinates ~coordinates})])))

(defn color-picker-hide [comp]
  (om/transact! comp `[(color-picker/update
                         {:color-picker/active-face nil
                          :color-picker/coordinates nil}) :faces/list]))

(defn valid-face-name? [str]
  (re-matches #"^[0-9a-zA-Z\-]+$" str))

(defn valid-file-name? [str]
  (re-matches #"^[\w\-. ]+$" str))

(def code-class "code")

(defn code-face-class [face-name]
  (str "code-" (name face-name)))

(defn update-elements-by-class [class func]
  (let [elements (getElementsByTagNameAndClass nil class)]
    (forEach elements func)))

(defn update-elements-by-selector [selector func]
  (let [elements (query selector)]
    (forEach elements func)))
