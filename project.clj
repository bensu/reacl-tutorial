(defproject reacl-tutorial "0.1.0-SNAPSHOT"
  :description "Reacl tutorial"
  :url "https://github.com/active-group/reacl-tutorial"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.107" #_"0.0-3269"]
                 [reacl "1.3.0"]]
  :plugins [[lein-cljsbuild "1.0.6"]
            [lein-ring "0.9.6"]
            [lein-figwheel "0.3.5"]]

  :hooks [leiningen.cljsbuild]
  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js" "resources/test/js"]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel {:load-warninged-code true}

                        :compiler {:main reacl-tutorial.core
                                   :asset-path "js/dev-out"
                                   :output-to "resources/public/js/cljs.js"
                                   :output-dir "resources/public/js/dev-out"
                                   :source-map-timestamp true}}
                       {:id "test"
                        :source-paths ["src/cljs" "test/cljs"]
                        :figwheel {:load-warninged-code true
                                   :on-jsload "reacl-tutorial.test-run/run"}

                        :compiler {:main reacl-tutorial.test-run
                                   :asset-path "js/test-out"
                                   :output-to "resources/public/js/test.js"
                                   :output-dir "resources/public/js/test-out"
                                   :source-map-timestamp true}}]}

  :figwheel {
             ;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"

             :css-dirs ["resources/public/css"] ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this
             ;; doesn't work for you just run your own server :)
             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             }

  :main reacl-tutorial.server
  :ring {:handler reacl-tutorial.server/app}

  :jvm-opts ^:replace ["-Xmx1g" "-server"])
