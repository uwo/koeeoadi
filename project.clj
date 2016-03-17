(defproject komokio "0.1.0-SNAPSHOT"
  :description ""
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0-RC5"]
                 [org.clojure/core.async "0.2.374"]
                 [org.clojure/clojurescript "1.7.228"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [org.omcljs/om "1.0.0-alpha30"]
                 [com.cemerick/piggieback "0.2.1"]
                 [org.clojure/clojure "1.7.0"]
                 [figwheel-sidecar "0.5.0-2"]
                 ]

  :plugins [[lein-figwheel "0.5.0-1"]]

  :clean-targets ^{:protect false} ["resources/public/js/out"]

  :source-paths ["src/clj" "src/cljs"]

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :cljsbuild {:builds
              [{:id "dev"
                :figwheel true
                :source-paths ["src/cljs"]
                :compiler {:main komokio.core
                           :asset-path "js/out"
                           :output-to "resources/public/js/main.js"
                           :output-dir "resources/public/js/out"
                           :parallel-build true
                           :compiler-stats true
                           :verbose true}}]}

  :figwheel {:validate-config true
             :css-dirs ["resources/public/css"]
             :server-port 3000})
