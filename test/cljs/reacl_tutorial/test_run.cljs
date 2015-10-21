(ns ^:figwheel-always reacl-tutorial.test-run
  (:require [cljs.test :refer-macros [run-tests run-all-tests]]
            [doo.runner :refer-macros [doo-tests]]
            [reacl-tutorial.core-test]))

(enable-console-print!)

(doo-tests 'reacl-tutorial.core-test)
