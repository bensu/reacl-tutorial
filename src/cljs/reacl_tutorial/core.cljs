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

(reacl/defclass list-display
  this lis []
  render
  (dom/ul {:class "animals"} ;; check CSS
   (map (fn [t] (dom/li t))
        lis)))

;; string-display
#_ (def top
  (reacl/render-component
   (.getElementById js/document "content")
   string-display "Hello world!" reacl/no-reaction))

;; list-display
(def top
  (reacl/render-component
   (.getElementById js/document "content")
   list-display ["Lion" "Zebra" "Buffalo" "Antelope"] reacl/no-reaction))
