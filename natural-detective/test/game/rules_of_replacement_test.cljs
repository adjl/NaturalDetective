(ns game.rules-of-replacement-test
  (:require [cljs.test :refer-macros [deftest is]]
            [game.rules-of-replacement :as r]))

(deftest test-double-negation-introduction
  (is (= [:not [:not :P]] (r/double-negation-introduction :P)))
  (is (= [:not [:not [:not :P]]]
         (r/double-negation-introduction [:not :P]))))

(deftest test-double-negation-elimination
  (is (= :P (r/double-negation-elimination [:not [:not :P]])))
  (is (= [:not :P]
         (r/double-negation-elimination [:not [:not [:not :P]]]))))

(deftest test-tautology
  (is (= :P (r/tautology [:and :P :P])))
  (is (= :P (r/tautology [:or :P :P]))))

(deftest test-commutativity
  (is (= [:and :Q :P] (r/commutativity [:and :P :Q])))
  (is (= [:or :Q :P] (r/commutativity [:or :P :Q]))))
