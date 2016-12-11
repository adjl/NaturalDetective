(ns game.rules-of-replacement-test
  (:require [cljs.test :refer-macros [deftest is]]
            [game.rules-of-replacement :as rr]))

(deftest test-double-negation-introduction
  (is (= [:not [:not :P]] (rr/double-negation-introduction :P)))
  (is (= [:not [:not [:not :P]]]
         (rr/double-negation-introduction [:not :P]))))

(deftest test-double-negation-elimination
  (is (= :P (rr/double-negation-elimination [:not [:not :P]])))
  (is (= [:not :P]
         (rr/double-negation-elimination [:not [:not [:not :P]]]))))

(deftest test-tautology
  (is (= :P (rr/tautology [:arr :P :P])))
  (is (= :P (rr/tautology [:or :P :P]))))
