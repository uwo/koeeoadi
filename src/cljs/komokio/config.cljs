(ns komokio.config
  (:require
   [om.next :as om]
   [komokio.components.faceeditor :refer [FaceEditor]]))

(def code-elisp [{:code-chunk/face [:faces/by-name "comment-delimiter"], :code-chunk/string ";; ", :code-chunk/line-chunk 1001} {:code-chunk/face [:faces/by-name "comment"], :code-chunk/string "This is some example code you can use to create your color scheme", :code-chunk/line-chunk 1002} {:code-chunk/face [:faces/by-name "comment"], :code-chunk/string "", :code-chunk/line-chunk 1003} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 2001} {:code-chunk/face [:faces/by-name "comment-delimiter"], :code-chunk/string ";", :code-chunk/line-chunk 3001} {:code-chunk/face [:faces/by-name "comment-delimiter"], :code-chunk/string "; ", :code-chunk/line-chunk 3002} {:code-chunk/face [:faces/by-name "comment"], :code-chunk/string "Click on a syntax highlighted section of code to change the color of that type of syntax", :code-chunk/line-chunk 3003} {:code-chunk/face [:faces/by-name "comment"], :code-chunk/string "", :code-chunk/line-chunk 3004} {:code-chunk/face [:faces/by-name "comment-delimiter"], :code-chunk/string ";", :code-chunk/line-chunk 4001} {:code-chunk/face [:faces/by-name "comment-delimiter"], :code-chunk/string "; ", :code-chunk/line-chunk 4002} {:code-chunk/face [:faces/by-name "comment"], :code-chunk/string "Add or remove colors to the palette using the ", :code-chunk/line-chunk 4003} {:code-chunk/face [:faces/by-name "comment"], :code-chunk/string "\"Palette", :code-chunk/line-chunk 4004} {:code-chunk/face [:faces/by-name "comment"], :code-chunk/string "\" menu on the right.", :code-chunk/line-chunk 4005} {:code-chunk/face [:faces/by-name "comment"], :code-chunk/string "", :code-chunk/line-chunk 4006} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 5001} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "(", :code-chunk/line-chunk 6001} {:code-chunk/face [:faces/by-name "keyword"], :code-chunk/string "defvar", :code-chunk/line-chunk 6002} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string " ", :code-chunk/line-chunk 6003} {:code-chunk/face [:faces/by-name "variable-name"], :code-chunk/string "foo", :code-chunk/line-chunk 6004} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string " '", :code-chunk/line-chunk 6005} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "(", :code-chunk/line-chunk 6006} {:code-chunk/face [:faces/by-name "builtin"], :code-chunk/string ":str-1", :code-chunk/line-chunk 6007} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string " ", :code-chunk/line-chunk 6008} {:code-chunk/face [:faces/by-name "string"], :code-chunk/string "\"komo", :code-chunk/line-chunk 6009} {:code-chunk/face [:faces/by-name "string"], :code-chunk/string "\"", :code-chunk/line-chunk 6010} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string " ", :code-chunk/line-chunk 6011} {:code-chunk/face [:faces/by-name "builtin"], :code-chunk/string ":str-2", :code-chunk/line-chunk 6012} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string " ", :code-chunk/line-chunk 6013} {:code-chunk/face [:faces/by-name "string"], :code-chunk/string "\"kio", :code-chunk/line-chunk 6014} {:code-chunk/face [:faces/by-name "string"], :code-chunk/string "\"", :code-chunk/line-chunk 6015} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string ")", :code-chunk/line-chunk 6016} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string ")", :code-chunk/line-chunk 6017} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 6018} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 7001} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "(", :code-chunk/line-chunk 8001} {:code-chunk/face [:faces/by-name "keyword"], :code-chunk/string "defun", :code-chunk/line-chunk 8002} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string " ", :code-chunk/line-chunk 8003} {:code-chunk/face [:faces/by-name "function-name"], :code-chunk/string "concater", :code-chunk/line-chunk 8004} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string " ", :code-chunk/line-chunk 8005} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "(", :code-chunk/line-chunk 8006} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "a b", :code-chunk/line-chunk 8007} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string ")", :code-chunk/line-chunk 8008} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 8009} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "  ", :code-chunk/line-chunk 9001} {:code-chunk/face [:faces/by-name "doc"], :code-chunk/string "\"The `", :code-chunk/line-chunk 9002} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "concater", :code-chunk/line-chunk 9003} {:code-chunk/face [:faces/by-name "doc"], :code-chunk/string "' function concatenates two strings", :code-chunk/line-chunk 9004} {:code-chunk/face [:faces/by-name "doc"], :code-chunk/string "\"", :code-chunk/line-chunk 9005} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 9006} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "  ", :code-chunk/line-chunk 10001} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "(", :code-chunk/line-chunk 10002} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "concat a b", :code-chunk/line-chunk 10003} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string ")", :code-chunk/line-chunk 10004} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string ")", :code-chunk/line-chunk 10005} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 10006} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 11001} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "(", :code-chunk/line-chunk 12001} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "concat ", :code-chunk/line-chunk 12002} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "(", :code-chunk/line-chunk 12003} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "plist-get foo ", :code-chunk/line-chunk 12004} {:code-chunk/face [:faces/by-name "builtin"], :code-chunk/string ":str-1", :code-chunk/line-chunk 12005} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string ")", :code-chunk/line-chunk 12006} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 12007} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "        ", :code-chunk/line-chunk 13001} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "(", :code-chunk/line-chunk 13002} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "plist-get foo ", :code-chunk/line-chunk 13003} {:code-chunk/face [:faces/by-name "builtin"], :code-chunk/string ":str-", :code-chunk/line-chunk 13004} {:code-chunk/face [:faces/by-name "builtin"], :code-chunk/string "1", :code-chunk/line-chunk 13005} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string ")", :code-chunk/line-chunk 13006} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string ")", :code-chunk/line-chunk 13007} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 13008} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 14001} {:code-chunk/face [:faces/by-name "default"], :code-chunk/string "", :code-chunk/line-chunk 15001}])

(def colors-list [{:color/id 230
                   :color/rgb "#1a1a1a"}
                  {:color/id 200
                   :color/rgb  "#Ffd700"}
                  {:color/id 201
                   :color/rgb  "#1e90ff"}
                  {:color/id 202
                   :color/rgb  "#Cd0000"}
                  {:color/id 203
                   :color/rgb  "#2e8b57"}])

(def faces-list [{:db/id 0
                  :face/name "background"
                  :face/color {:color/id 230
                               :color/rgb  "#1a1a1a"}}
                 {:db/id 1
                  :face/name "default"
                  :face/color {:color/id 201
                               :color/rgb  "#1e90ff"}}
                 {:db/id 2
                  :face/name "builtin"}
                 {:db/id 3
                  :face/name "comment-delimiter"
                  :face/color {:color/id 202
                               :color/rgb  "#Cd0000"}}
                 {:db/id 4
                  :face/name "comment"
                  :face/color {:color/id 202
                               :color/rgb  "#Cd0000"}}
                 {:db/id 5
                  :face/name "constant"}
                 {:db/id 6
                  :face/name "doc"
                  :face/color {:color/id 202
                               :color/rgb  "#Cd0000"}}
                 {:db/id 7
                  :face/name "doc-string"}
                 {:db/id 8
                  :face/name "function-name"
                  :face/color {:color/id 203
                               :color/rgb  "#2e8b57"}}
                 {:db/id 9
                  :face/name "keyword"
                  :face/color {:color/id 200
                               :color/rgb  "Ffd700"}}
                 {:db/id 10
                  :face/name "negation-char"}
                 {:db/id 11
                  :face/name "preprocessor"}
                 {:db/id 12
                  :face/name "regexp-grouping-backslash"}
                 {:db/id 13
                  :face/name "regexp-grouping-construct"}
                 {:db/id 14
                  :face/name "string"}
                 {:db/id 15
                  :face/name "type"}
                 {:db/id 16
                  :face/name "variable-name"}
                 {:db/id 17
                  :face/name "warning"}])

;; TODO ok this works so far using partially normalized data
;; keep this in mind because this might cause trouble
(def app-state
  {:palette-picker {:palette-picker/id 1}
   :data {:faces/list faces-list
          :colors/list colors-list
          :code-chunks/list code-elisp}})
