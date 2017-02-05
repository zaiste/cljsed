(defproject cljsed "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj"]
  :test-paths ["test/clj"]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [domina "1.0.2-SNAPSHOT"]
                 [hiccups "0.2.0"]
                 [shoreleave/shoreleave-remote-ring "0.3.0"]
                 [shoreleave/shoreleave-remote "0.3.0"]
                 [com.cemerick/valip "0.3.2"]
                 [enlive "1.1.1"]]

  :plugins [[lein-cljsbuild "0.3.2"]
            [lein-ring "0.8.5"]]

  :ring {:handler cljsed.core/app}

  :cljsbuild {:crossovers [valip.core valip.predicates cljsed.login.validators]
              :test-commands {"phantomjs-whitespace"
                              ["runners/phantomjs.js" "resources/public/js/cljsed.js"]}
              :builds
              [{:source-paths ["src/cljs"]

                ;; Google Closure (CLS) options configuration
                :compiler {:output-to "resources/public/js/cljsed.js"
                           :optimizations :whitespace
                           :pretty-print true}}]})
