(ns komokio.config)

(def app-state
  (atom {
         ;;code widget and faces editor depend on this directly
         :faces/list [{:db/id 100
                       :face/name :default

                       :face/foreground {:db/id 201
                                         :color/name "blue"
                                         :color/rgb  "#1e90ff"}}

                      {:db/id 101
                       :face/name :comment

                       :face/foreground {:db/id 203
                                         :color/name "green"
                                         :color/rgb  "#2e8b57"}}]

         ;; TODO rgb property name since they're not RGB values (hsv i think)
         :colors/list [{:db/id 204
                        :color/name "black"
                        :color/rgb "#000000"}

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
                        :color/rgb  "#2e8b57"}]}))

(def code-clojure "<pre><code><span class=\"code code-comment\">
  ;; this is a comment</span><span class=\"code code-default\">
  This is some default text</span><span class=\"code code-default\">
  This is even more default text</span></code></pre>")
