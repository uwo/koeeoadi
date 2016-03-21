(ns komokio.config)

(def code-elisp [{:face [:faces/by-name :default], :string "", :line-chunk 5001} {:face [:faces/by-name :default], :string "", :line-chunk 4006} {:face [:faces/by-name :default], :string ")", :line-chunk 4005} {:face [:faces/by-name :default], :string ")", :line-chunk 4004} {:face [:faces/by-name :default], :string "+ a b", :line-chunk 4003} {:face [:faces/by-name :default], :string "(", :line-chunk 4002} {:face [:faces/by-name :default], :string "  ", :line-chunk 4001} {:face [:faces/by-name :default], :string "", :line-chunk 3009} {:face [:faces/by-name :default], :string ")", :line-chunk 3008} {:face [:faces/by-name :default], :string "a b", :line-chunk 3007} {:face [:faces/by-name :default], :string "(", :line-chunk 3006} {:face [:faces/by-name :default], :string " ", :line-chunk 3005} {:face [:faces/by-name :font-lock-function-name-face], :string "adder", :line-chunk 3004} {:face [:faces/by-name :default], :string " ", :line-chunk 3003} {:face [:faces/by-name :font-lock-keyword-face], :string "defun", :line-chunk 3002} {:face [:faces/by-name :default], :string "(", :line-chunk 3001} {:face [:faces/by-name :default], :string "", :line-chunk 2001} {:face [:faces/by-name :font-lock-comment-face], :string "", :line-chunk 1004} {:face [:faces/by-name :font-lock-comment-face], :string "this is a comment", :line-chunk 1003} {:face [:faces/by-name :font-lock-comment-delimiter-face], :string "; ", :line-chunk 1002} {:face [:faces/by-name :font-lock-comment-delimiter-face], :string ";", :line-chunk 1001}])


(def code-clojure "<pre><code><span class=\"code code-comment\">
  ;; this is a comment</span><span class=\"code code-default\">

  This is some default text</span><span class=\"code code-function\">

  This is what a function looks like</span><span class=\"code code-keyword\">

  This is what a keyword looks like</span></span></code></pre>")

(def app-state
  (atom {
         :code-chunks/list code-elisp
         ;;code widget and faces editor depend on this directly
         :faces/list [{:db/id 100
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
                                         :color/rgb  "#2e8b57"}}

                      ]

         ;; TODO rgb property name since they're not RGB values (hsv i think)
         :colors/list [{:db/id 230
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
                        :color/rgb  "#2e8b57"}
                       ;; {:db/id 204
                       ;;  :color/name "brown"
                       ;;  :color/rgb  "#2e8b57"}
                       ;; {:db/id 205
                       ;;  :color/name "gray"
                       ;;  :color/rgb  "#2e8b57"}
                       ;; {:db/id 206
                       ;;  :color/name "cyan"
                       ;;  :color/rgb  "#2e8b57"}
                       ;; {:db/id 207
                       ;;  :color/name "greal"
                       ;;  :color/rgb  "#2e8b57"}
                       ;; {:db/id 256
                       ;;  :color/name "breen"
                       ;;  :color/rgb  "#2e8b57"}
                       ;; {:db/id 2910
                       ;;  :color/name "broon"
                       ;;  :color/rgb  "#2e8b57"}
                       ;; {:db/id 21112
                       ;;  :color/name "bray"
                       ;;  :color/rgb  "#2e8b57"}
                       ;; {:db/id 21314
                       ;;  :color/name "brayon"
                       ;;  :color/rgb  "#2e8b57"}
                       ;; {:db/id 21516
                       ;;  :color/name "bronno"
                       ;;  :color/rgb  "#2e8b57"}
                       ]}))
