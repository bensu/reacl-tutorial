(ns ^:figwheel-always reacl-tutorial.core
  (:require [reacl.core :as reacl :include-macros true]
            [reacl.dom :as dom :include-macros true]))

(enable-console-print!)

(println "Hello world!")

(reacl/defclass string-display
  this s []
  render
  (dom/h1 s)
  handle-message
  (fn [new]
    (reacl/return :app-state new)))

(def top
  (reacl/render-component
   (.getElementById js/document "content")
   string-display "Hello world!" reacl/no-reaction))
