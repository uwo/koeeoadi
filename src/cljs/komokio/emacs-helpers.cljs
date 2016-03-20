(ns komokio.emacs-helpers)

(defn plist-to-map [plist]
  (let [plist-to-map' (fn [x]
                        (if (coll? x)
                          (into {} (mapv #(into [] %) (partition 2 2 x)))
                          x))]

    (mapv plist-to-map' plist)))




