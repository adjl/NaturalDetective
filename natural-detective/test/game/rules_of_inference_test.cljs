(ns game.rules-of-inference-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [game.rules-of-inference :as i]))

(deftest test-disjunctive-syllogism
  (testing "Disjunctive syllogism"
    (is (= :Q (i/disjunctive-syllogism [:or :P :Q] [:not :P])))
    (is (= :Q (i/disjunctive-syllogism [:or :Q :P] [:not :P])))
    (is (= :Q (i/disjunctive-syllogism [:not :P] [:or :P :Q])))
    (is (= :Q (i/disjunctive-syllogism [:not :P] [:or :Q :P])))))
