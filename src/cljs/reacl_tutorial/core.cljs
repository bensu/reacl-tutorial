(ns reacl-tutorial.core
  (:require [reacl.core :as reacl :include-macros true]
            [reacl.dom :as dom :include-macros true]))

(enable-console-print!)

(println "Hello world!")

(reacl/defview demo
  this []
  render
  (dom/h1 "Reacl Tutorial"))

(reacl/render-component
 (.getElementById js/document "content")
 demo)