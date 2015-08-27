(ns ^:figwheel-always reacl-tutorial.test-run
  (:require [cljs.test :refer-macros [run-tests run-all-tests]]))

(enable-console-print!)

(defn ^:export run
  []
  (run-all-tests #"reacl-tutorial\..*-test"))
