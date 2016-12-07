(ns game.test
  (:require [game.core-test :as core-test]))

(def success 0)

(defn ^:export run []
  (.log js/console "Example test started.")
  (core-test/run)
  success)
