(ns koeeoadi.emacs-helpers)

(defn plist-to-map [plist]
  (let [plist-to-map' (fn [x]
                        (if (coll? x)
                          (into {} (mapv #(into [] %) (partition 2 2 x)))
                          x))]
    (vec (reverse (mapv plist-to-map' plist)))))

