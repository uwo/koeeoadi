(require
  '[figwheel-sidecar.repl :as r]
  '[figwheel-sidecar.repl-api :as ra])

(ra/start-figwheel!
  {:figwheel-options {
                      :server-port 3000
                      :repl true
                      }
   :build-ids ["dev"]
   :all-builds
   [{:id "dev"
     :figwheel true
     :source-paths ["src/cljs"]
     :compiler {:main 'komokio.core
                :asset-path "js"
                :output-to "resources/public/js/main.js"
                :output-dir "resources/public/js"
                ;; TODO what does this mean
                :parallel-build true
                ;; TODO what does this mean
                :compiler-stats true
                :verbose true}}]})
