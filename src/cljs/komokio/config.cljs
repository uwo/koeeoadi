(ns komokio.config
  (:require
   [om.next :as om]
   [komokio.components.faceeditor :refer [FaceEditor]]))

(def code-elisp [{:code-chunk/face [:faces/by-name :font-lock-comment-delimiter-face], :code-chunk/string ";", :code-chunk/line-chunk 1001}
                 {:code-chunk/face [:faces/by-name :font-lock-comment-delimiter-face], :code-chunk/string "; ", :code-chunk/line-chunk 1002}
                 {:code-chunk/face [:faces/by-name :font-lock-comment-face], :code-chunk/string "this is a comment", :code-chunk/line-chunk 1003}
                 {:code-chunk/face [:faces/by-name :font-lock-comment-face], :code-chunk/string "", :code-chunk/line-chunk 1004}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string "", :code-chunk/line-chunk 2001}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string "(", :code-chunk/line-chunk 3001}
                 {:code-chunk/face [:faces/by-name :font-lock-keyword-face], :code-chunk/string "defun", :code-chunk/line-chunk 3002}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string " ", :code-chunk/line-chunk 3003}
                 {:code-chunk/face [:faces/by-name :font-lock-function-name-face], :code-chunk/string "adder", :code-chunk/line-chunk 3004}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string " ", :code-chunk/line-chunk 3005}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string "(", :code-chunk/line-chunk 3006}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string "a b", :code-chunk/line-chunk 3007}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string ")", :code-chunk/line-chunk 3008}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string "", :code-chunk/line-chunk 3009}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string "  ", :code-chunk/line-chunk 4001}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string "(", :code-chunk/line-chunk 4002}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string "+ a b", :code-chunk/line-chunk 4003}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string ")", :code-chunk/line-chunk 4004}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string ")", :code-chunk/line-chunk 4005}
                 {:code-chunk/face [:faces/by-name :default], :code-chunk/string "", :code-chunk/line-chunk 5001}])

(def faces-list [{:db/id 100
                        :face/name :default

                        :face/foreground {:db/id 201
                                          :color/name "blue"
                                          :color/rgb  "#1e90ff"}}

                       {:db/id 101
                        :face/name :font-lock-comment-delimiter-face

                        :face/foreground {:db/id 202
                                          :color/name "red"
                                          :color/rgb  "#Cd0000"}}

                       {:db/id 102
                        :face/name :font-lock-comment-face

                        :face/foreground {:db/id 202
                                          :color/name "red"
                                          :color/rgb  "#Cd0000"}}

                       {:db/id 103
                        :face/name :font-lock-keyword-face

                        :face/foreground {:db/id 200
                                          :color/name "yellow"
                                          :color/rgb  "Ffd700"}}

                       {:db/id 105
                        :face/name :font-lock-function-name-face

                        :face/foreground {:db/id 203
                                          :color/name "green"
                                          :color/rgb  "#2e8b57"}}])

(def colors-list [{:db/id 230
                   :color/name "black"
                   :color/rgb "#1a1a1a"}
                  {:db/id 200
                   :color/name "yellow"
                   :color/rgb  "#Ffd700"}
                  {:db/id 201
                   :color/name "blue"
                   :color/rgb  "#1e90ff"}
                  {:db/id 202
                   :color/name "red"
                   :color/rgb  "#Cd0000"}
                  {:db/id 203
                   :color/name "green"
                   :color/rgb  "#2e8b57"}])

;; TODO ok this works so far using partially normalized data
;; keep this in mind because this might cause trouble
(def app-state
  {:palette-picker {:palette-picker/id 1}
   :data {:faces/list faces-list
          :colors/list colors-list
          :code-chunks/list code-elisp}})
