(ns game.tautology-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [game.natural-deduction :as nd]))

(deftest test-single-statements
  (is (= :P (nd/tautology :P))))

(deftest test-simple-premises-reducible
  (is (= :P (nd/tautology [:and :P :P])))
  (is (= :P (nd/tautology [:or :P :P]))))

(deftest test-simple-premises-nonreducible
  (is (= [:and :P :Q] (nd/tautology [:and :P :Q])))
  (is (= [:or :P :Q] (nd/tautology [:or :P :Q]))))

(deftest test-simple-premises-preserved-order
  (is (= [:and :Q :P] (nd/tautology [:and :Q :P])))
  (is (= [:or :Q :P] (nd/tautology [:or :Q :P]))))

(deftest test-semi-nested-premises-reducible
  (is (= :P (nd/tautology [:and :P [:and :P :P]])))
  (is (= :P (nd/tautology [:or :P [:or :P :P]])))
  (is (= [:and :Q :P] (nd/tautology [:and :Q [:and :P :P]])))
  (is (= [:or :Q :P] (nd/tautology [:or :Q [:or :P :P]]))))

(deftest test-semi-nested-premises-nonreducible
  (is (= [:and :P [:and :P :Q]] (nd/tautology [:and :P [:and :P :Q]])))
  (is (= [:and :P [:and :Q :R]] (nd/tautology [:and :P [:and :Q :R]])))
  (is (= [:or :P [:or :P :Q]] (nd/tautology [:or :P [:or :P :Q]])))
  (is (= [:or :P [:or :Q :R]] (nd/tautology [:or :P [:or :Q :R]]))))

(deftest test-nested-premises-reducible-conjunction
  (is (= :P (nd/tautology [:and [:and :P :P] [:and :P :P]])))
  (is (= [:and :P [:and :P :Q]] (nd/tautology [:and [:and :P :P] [:and :P :Q]])))
  (is (= [:and :P :Q] (nd/tautology [:and [:and :P :P] [:and :Q :Q]])))
  (is (= [:and :P :Q] (nd/tautology [:and [:and :P :Q] [:and :P :Q]])))
  (is (= [:and :P [:and :Q :R]] (nd/tautology [:and [:and :P :P] [:and :Q :R]]))))

(deftest test-nested-premises-reducible-disjunction
  (is (= :P (nd/tautology [:or [:or :P :P] [:or :P :P]])))
  (is (= [:or :P [:or :P :Q]] (nd/tautology [:or [:or :P :P] [:or :P :Q]])))
  (is (= [:or :P :Q] (nd/tautology [:or [:or :P :P] [:or :Q :Q]])))
  (is (= [:or :P :Q] (nd/tautology [:or [:or :P :Q] [:or :P :Q]])))
  (is (= [:or :P [:or :Q :R]] (nd/tautology [:or [:or :P :P] [:or :Q :R]]))))

(deftest test-nested-premises-nonreducible-conjunction
  (is (= [:and [:and :P :Q] [:and :Q :P]]
         (nd/tautology [:and [:and :P :Q] [:and :Q :P]])))
  (is (= [:and [:and :P :Q] [:and :P :R]]
         (nd/tautology [:and [:and :P :Q] [:and :P :R]])))
  (is (= [:and [:and :P :Q] [:and :R :S]]
         (nd/tautology [:and [:and :P :Q] [:and :R :S]]))))

(deftest test-nested-premises-nonreducible-disjunction
  (is (= [:or [:or :P :Q] [:or :Q :P]] (nd/tautology [:or [:or :P :Q] [:or :Q :P]])))
  (is (= [:or [:or :P :Q] [:or :P :R]] (nd/tautology [:or [:or :P :Q] [:or :P :R]])))
  (is (= [:or [:or :P :Q] [:or :R :S]] (nd/tautology [:or [:or :P :Q] [:or :R :S]]))))
