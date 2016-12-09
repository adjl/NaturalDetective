(ns game.test
  (:require [cljs.test :refer-macros [run-all-tests]]))

(enable-console-print!)

(defmethod cljs.test/report
  [:cljs.test/default :end-run-tests] [status]
  (if (cljs.test/successful? status)
    (println "SUCCESS")
    (println "FAILURE")))

(defn ^:export run []
  (run-all-tests #"game.*-test"))
