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

(defn stripe
  [bgc text]
  (dom/li {:style {:background-color bgc}} text))

(reacl/defclass list-display
  this lis []
  render
  (dom/ul {:class "animals"}
   (map stripe (cycle ["#ff0" "#fff"]) lis)))

(def contacts
  [{:first "Ben" :last "Bitdiddle" :email "benb@mit.edu"}
   {:first "Alyssa" :middle-initial "P" :last "Hacker" :email "aphacker@mit.edu"}
   {:first "Eva" :middle "Lu" :last "Ator" :email "eval@mit.edu"}
   {:first "Louis" :last "Reasoner" :email "prolog@mit.edu"}
   {:first "Cy" :middle-initial "D" :last "Effect" :email "bugs@mit.edu"}
   {:first "Lem" :middle-initial "E" :last "Tweakit" :email "morebugs@mit.edu"}])

(defn middle-name [{:keys [middle middle-initial]}]
  (cond
    middle (str " " middle)
    middle-initial (str " " middle-initial ".")))

(defn display-name
  [{:keys [first last] :as contact}]
  (str last ", " first (middle-name contact)))

(reacl/defclass contact-display
  this contact [parent] ; parent later
  render
  (dom/li
   (dom/span (display-name contact))
   (dom/button {:onclick (fn [e] (reacl/send-message! parent contact))} "Delete"))) ; add later

(reacl/defclass contacts-display
  this data []
  render
  (dom/div
   (dom/h2 "Contact list")
   (dom/ul
    (map (fn [c] (contact-display c reacl/no-reaction this)) data)))
  handle-message
  (fn [msg]
    (reacl/return :app-state
                  (vec (remove (fn [c] (= c msg)) data)))))
           
;; string-display
#_ (def top
  (reacl/render-component
   (.getElementById js/document "content")
   string-display "Hello world!" reacl/no-reaction))

;; list-display
#_(def top
  (reacl/render-component
   (.getElementById js/document "content")
   list-display ["Lion" "Zebra" "Buffalo" "Antelope"] reacl/no-reaction))

(def top
  (reacl/render-component
   (.getElementById js/document "content")
   contacts-display contacts reacl/no-reaction))
