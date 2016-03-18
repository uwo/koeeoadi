(ns komokio.config)

(def app-state
  (atom {
         
         ;;code widget and faces editor depend on this directly
         :faces/list [{:db/id 100
                       :face/name :default

                       :face/foreground {:db/id 201
                                         :color/name "blue"
                                         :color/rgb  "#1e90ff"}

                       :face/background {:db/id 204
                                         :color/name "black"
                                         :color/rgb  "#1a1a1a"}}
                      {:db/id 101
                       :face/name :keyword

                       :face/foreground {:db/id 202
                                         :color/name "red"
                                         :color/rgb  "#Cd0000"}

                       :face/background {:db/id 204
                                         :color/name "black"
                                         :color/rgb  "#1a1a1a"}}
                      {:db/id 102
                       :face/name :function

                       :face/foreground {:db/id 200
                                         :color/name "yellow"
                                         :color/rgb  "Ffd700"}

                       :face/background {:db/id 204
                                         :color/name "black"
                                         :color/rgb  "#1a1a1a"}}

                      {:db/id 103
                       :face/name :comment

                       :face/foreground {:db/id 203
                                         :color/name "green"
                                         :color/rgb  "#2e8b57"}

                       :face/background {:db/id 204
                                         :color/name "black"
                                         :color/rgb  "#1a1a1a"}}]

         ;; TODO rgb property name since they're not RGB values (hsv i think)
         :colors/list [{:db/id 204
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
                       {:db/id 204
                        :color/name "brown"
                        :color/rgb  "#2e8b57"}
                       {:db/id 205
                        :color/name "gray"
                        :color/rgb  "#2e8b57"}
                       {:db/id 206
                        :color/name "cyan"
                        :color/rgb  "#2e8b57"}
                       {:db/id 207
                        :color/name "teal"
                        :color/rgb  "#2e8b57"}
                       ]}))

(def code-clojure "<pre><code><span class=\"code code-comment\">
  ;; this is a comment</span><span class=\"code code-default\">

  This is some default text</span><span class=\"code code-function\">

  This is what a function looks like</span><span class=\"code code-keyword\">

  This is what a keyword looks like</span></span></code></pre>")
