(defproject reacl-tutorial "0.1.0-SNAPSHOT"
  :description "Reacl tutorial"
  :url "http://github.com/mikesperber/reacl-tutorial"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.48" :classifier "aot"]
                 [reacl "1.1.0"]]
  :plugins [[lein-cljsbuild "1.0.6"]
            [lein-ring "0.9.6"]]
  :hooks [leiningen.cljsbuild]
  :source-paths ["src/clj"]


  :cljsbuild { 
    :builds {
      :main {
        :source-paths ["src/cljs"]
        :compiler {:output-to "resources/public/js/cljs.js"
                   :optimizations :simple
                   :pretty-print true}
        :jar true}}}
  :main reacl-tutorial.server
  :ring {:handler reacl-tutorial.server/app}

  :jvm-opts ^:replace ["-Xmx1g" "-server"])

