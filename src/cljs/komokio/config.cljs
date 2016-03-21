(ns komokio.config)

(def code-elisp [{:face [:faces/by-name :default], :string "", :line 5, :chunk 1} {:face [:faces/by-name :default], :string "", :line 4, :chunk 6} {:face [:faces/by-name :default], :string ")", :line 4, :chunk 5} {:face [:faces/by-name :default], :string ")", :line 4, :chunk 4} {:face [:faces/by-name :default], :string "+ a b", :line 4, :chunk 3} {:face [:faces/by-name :default], :string "(", :line 4, :chunk 2} {:face [:faces/by-name :default], :string "  ", :line 4, :chunk 1} {:face [:faces/by-name :default], :string "", :line 3, :chunk 9} {:face [:faces/by-name :default], :string ")", :line 3, :chunk 8} {:face [:faces/by-name :default], :string "a b", :line 3, :chunk 7} {:face [:faces/by-name :default], :string "(", :line 3, :chunk 6} {:face [:faces/by-name :default], :string " ", :line 3, :chunk 5} {:face [:faces/by-name :font-lock-function-name-face], :string "heres-a-test-function", :line 3, :chunk 4} {:face [:faces/by-name :default], :string " ", :line 3, :chunk 3} {:face [:faces/by-name :font-lock-keyword-face], :string "defun", :line 3, :chunk 2} {:face [:faces/by-name :default], :string "(", :line 3, :chunk 1} {:face [:faces/by-name :default], :string "", :line 2, :chunk 1} {:face [:faces/by-name :font-lock-comment-face], :string "This is some test code", :line 1, :chunk 3} {:face [:faces/by-name :font-lock-comment-delimiter-face], :string "; ", :line 1, :chunk 2} {:face [:faces/by-name :font-lock-comment-delimiter-face], :string ";", :line 1, :chunk 1}])

(def code-clojure "<pre><code><span class=\"code code-comment\">
  ;; this is a comment</span><span class=\"code code-default\">

  This is some default text</span><span class=\"code code-function\">

  This is what a function looks like</span><span class=\"code code-keyword\">

  This is what a keyword looks like</span></span></code></pre>")



(def app-state
  (atom {
         :code code-elisp
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

                      {:db/id 101
                       :face/name :font-lock-comment-face

                       :face/foreground {:db/id 202
                                         :color/name "red"
                                         :color/rgb  "#Cd0000"}}

                      {:db/id 102
                       :face/name :font-lock-keyword-face

                       :face/foreground {:db/id 200
                                         :color/name "yellow"
                                         :color/rgb  "Ffd700"}}

                      {:db/id 103
                       :face/name :comment

                       :face/foreground {:db/id 203
                                         :color/name "green"
                                         :color/rgb  "#2e8b57"}}
                      
                      {:db/id 104
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
                       ;; {:db/id 203
                       ;;  :color/name "green"
                       ;;  :color/rgb  "#2e8b57"}
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
