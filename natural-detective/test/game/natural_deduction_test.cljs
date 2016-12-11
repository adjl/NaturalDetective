(ns game.natural-deduction-test
  (:require [cljs.test :refer-macros [deftest is]]
            [game.natural-deduction :as nd]))

(deftest test-double-negation-introduction
  (is (= [:not [:not :P]] (nd/double-negation-introduction :P)))
  (is (= [:not [:not [:not :P]]]
         (nd/double-negation-introduction [:not :P]))))

(deftest test-double-negation-elimination
  (is (= :P (nd/double-negation-elimination [:not [:not :P]])))
  (is (= [:not :P]
         (nd/double-negation-elimination [:not [:not [:not :P]]]))))

(deftest test-tautology
  (is (= :P (nd/tautology [:and :P :P])))
  (is (= :P (nd/tautology [:or :P :P]))))
