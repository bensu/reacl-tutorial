(ns ^:figwheel-always reacl-tutorial.core
  (:require [reacl.core :as reacl :include-macros true]
            [reacl.dom :as dom :include-macros true]
            [clojure.string :as string]))

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

(defn parse-contact [contact-str]
  (let [[first middle last :as parts] (string/split contact-str #"\s+")
        [first last middle] (if (nil? last) [first middle] [first last middle])
        middle (when middle (string/replace middle "." ""))
        c (if middle (count middle) 0)]
    (when (>= (count parts) 2)
      (cond-> {:first first :last last}
        (== c 1) (assoc :middle-initial middle)
        (>= c 2) (assoc :middle middle)))))

#_(reacl/defclass contact-display
  this contact [parent] ; parent later
  render
  (dom/li
   (dom/span (display-name contact))
   (dom/button {:onclick (fn [e] (reacl/send-message! parent contact))} "Delete"))) ; add later

(defrecord Delete [contact])

(reacl/defclass contact-display
  this contact [parent] ; parent later
  render
  (dom/li
   (dom/span (display-name contact))
   (dom/button {:onclick (fn [e] (reacl/send-message! parent (->Delete contact)))} "Delete")))

#_(reacl/defclass contacts-display
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

(defrecord NewText [text])
(defrecord Add [contact])

(reacl/defclass contacts-display
  this data new-text []
  initial-state ""
  render
  (dom/div
   (dom/h2 "Contact list")
   (dom/ul
    (map (fn [c] (contact-display c reacl/no-reaction this)) data))
   (dom/div
    (dom/input {:type "text" :value new-text
                :onchange (fn [e] (reacl/send-message! this
                                                       (->NewText (.. e -target -value))))})
    (dom/button {:onclick (fn [e] (reacl/send-message! this (->Add (parse-contact new-text))))} "Add contact")))
  handle-message
  (fn [msg]
    (println "Msg" msg)
    (cond
      (instance? Delete msg)
      (reacl/return :app-state
                    (vec (remove (fn [c] (= c (:contact msg))) data)))

      (instance? NewText msg)
      (reacl/return :local-state (:text msg))
      
      (instance? Add msg)
      (reacl/return :app-state (conj data (:contact msg))
                    :local-state ""))))
           
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
