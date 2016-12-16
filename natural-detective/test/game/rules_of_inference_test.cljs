(ns game.rules-of-inference-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [game.rules-of-inference :as i]))

(deftest test-conjunction-introduction
  (testing "Conjunction introduction"
    (is (= [:and :P :Q] (i/conjunction-introduction :P :Q)))))

(deftest test-conjunction-elimination
  (testing "Conjunction elimination"
    (is (= :P (i/conjunction-elimination [:and :P :Q] :first)))
    (is (= :Q (i/conjunction-elimination [:and :P :Q] nil)))))

(deftest test-disjunction-introduction
  (testing "Disjunction introduction"
    (is (= [:or :P :Q] (i/disjunction-introduction :P :Q)))))

(deftest test-disjunctive-syllogism
  (testing "Disjunctive syllogism"
    (is (= :Q (i/disjunctive-syllogism [:or :P :Q] [:not :P])))
    (is (= :Q (i/disjunctive-syllogism [:or :Q :P] [:not :P])))
    (is (= :Q (i/disjunctive-syllogism [:not :P] [:or :P :Q])))
    (is (= :Q (i/disjunctive-syllogism [:not :P] [:or :Q :P])))))

(deftest test-hypothetical-syllogism
  (testing "Hypothetical syllogism"
    (is (= [:impl :P :R]
           (i/hypothetical-syllogism [:impl :P :Q] [:impl :Q :R])))
    (is (= [:impl :P :R]
           (i/hypothetical-syllogism [:impl :Q :R] [:impl :P :Q])))))

(deftest test-modus-ponens
  (testing "Modus ponens"
    (is (= :Q (i/modus-ponens [:impl :P :Q] :P)))
    (is (= :Q (i/modus-ponens :P [:impl :P :Q])))))

(deftest test-modus-tollens
  (testing "Modus tollens"
    (is (= [:not :P] (i/modus-tollens [:impl :P :Q] [:not :Q])))
    (is (= [:not :P] (i/modus-tollens [:not :Q] [:impl :P :Q])))))
