(ns koeeoadi.util
  (:require [cljs.reader :refer [read-string]]
            [goog.dom :refer [getElement getElementsByTagNameAndClass]]
            [goog.style :refer [getClientPosition getPageOffset]]
            [goog.array :refer [forEach]]
            [om.next :as om]
            [om.dom :as dom])
  (:import  [goog.dom query]))

(defn faces-to-colorize [faces color-id]
  (hash-map
    :bg-faces (map :face/name (filter #(= color-id (last (:face/color-bg %))) faces))
    :fg-faces (map :face/name (filter #(= color-id (last (:face/color-fg %))) faces))))

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

(defn color-picker-show [comp face color-type e]
  (.preventDefault e)
  (.stopPropagation e)
  (let [coords (getClientPosition e)
        coords-offset (getPageOffset (getElement "root"))
        coords' {:y (aget coords "x")
                 :x (+ (aget coords "y") (- (aget coords-offset "y")))}]
    (om/update-state! comp assoc
      :active-face face
      :color-type  color-type
      :coordinates coords')))

(defn color-picker-hide [comp]
  (om/update-state! comp assoc
    :active-face nil
    :coordinates nil))

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
