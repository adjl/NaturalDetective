(ns game.rules-of-replacement-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [game.rules-of-replacement :as r]))

(deftest test-double-negation-introduction
  (testing "Double negation introduction"
    (is (= [:not [:not :P]] (r/double-negation-introduction :P)))
    (is (= [:not [:not [:not :P]]] (r/double-negation-introduction [:not :P])))))

(deftest test-double-negation-elimination
  (testing "Double negation elimination"
    (is (= :P (r/double-negation-elimination [:not [:not :P]])))
    (is (= [:not :P] (r/double-negation-elimination [:not [:not [:not :P]]])))))

(deftest test-tautology
  (testing "Idempotency of conjunction"
    (is (= :P (r/tautology [:and :P :P]))))
  (testing "Idempotency of disjunction"
    (is (= :P (r/tautology [:or :P :P])))))

(deftest test-commutativity
  (testing "Commutativity of conjunction"
    (is (= [:and :Q :P] (r/commutativity [:and :P :Q]))))
  (testing "Commutativity of disjunction"
    (is (= [:or :Q :P] (r/commutativity [:or :P :Q])))))

(deftest test-associativity
  (testing "Associativity of conjunction"
    (is (= [:and [:and :P :Q] :R] (r/associativity [:and :P [:and :Q :R]])))
    (is (= [:and :P [:and :Q :R]] (r/associativity [:and [:and :P :Q] :R]))))
  (testing "Associativity of disjunction"
    (is (= [:or [:or :P :Q] :R] (r/associativity [:or :P [:or :Q :R]])))
    (is (= [:or :P [:or :Q :R]] (r/associativity [:or [:or :P :Q] :R])))))

(deftest test-distributivity
  (testing "Distribution of conjunction over conjunction"
    (is (= [:and [:and :P :Q] [:and :P :R]]
           (r/distributivity [:and :P [:and :Q :R]])))
    (is (= [:and :P [:and :Q :R]]
           (r/distributivity [:and [:and :P :Q] [:and :P :R]]))))
  (testing "Distribution of disjunction over disjunction"
    (is (= [:or [:or :P :Q] [:or :P :R]]
           (r/distributivity [:or :P [:or :Q :R]])))
    (is (= [:or :P [:or :Q :R]]
           (r/distributivity [:or [:or :P :Q] [:or :P :R]]))))
  (testing "Distribution of conjunction over disjunction"
    (is (= [:or [:and :P :Q] [:and :P :R]]
           (r/distributivity [:and :P [:or :Q :R]])))
    (is (= [:or :P [:and :Q :R]]
           (r/distributivity [:and [:or :P :Q] [:or :P :R]]))))
  (testing "Distribution of disjunction over conjunction"
    (is (= [:and [:or :P :Q] [:or :P :R]]
           (r/distributivity [:or :P [:and :Q :R]])))
    (is (= [:and :P [:or :Q :R]]
           (r/distributivity [:or [:and :P :Q] [:and :P :R]]))))
  (testing "Double distribution of conjunction over conjunction"
    (is (= [:and
            [:and [:and :P :R] [:and :P :S]]
            [:and [:and :Q :R] [:and :Q :S]]]
           (r/distributivity [:and [:and :P :Q] [:and :R :S]])))
    (is (= [:and [:and :P :Q] [:and :R :S]]
           (r/distributivity [:and
                              [:and [:and :P :R] [:and :P :S]]
                              [:and [:and :Q :R] [:and :Q :S]]]))))
  (testing "Double distribution of disjunction over disjunction"
    (is (= [:or
            [:or [:or :P :R] [:or :P :S]]
            [:or [:or :Q :R] [:or :Q :S]]]
           (r/distributivity [:or [:or :P :Q] [:or :R :S]])))
    (is (= [:or [:or :P :Q] [:or :R :S]]
           (r/distributivity [:or
                              [:or [:or :P :R] [:or :P :S]]
                              [:or [:or :Q :R] [:or :Q :S]]]))))
  (testing "Double distribution of conjunction over disjunction"
    (is (= [:or
            [:or [:and :P :R] [:and :P :S]]
            [:or [:and :Q :R] [:and :Q :S]]]
           (r/distributivity [:and [:or :P :Q] [:or :R :S]])))
    (is (= [:or [:and :P :Q] [:and :R :S]]
           (r/distributivity [:and
                              [:and [:or :P :R] [:or :P :S]]
                              [:and [:or :Q :R] [:or :Q :S]]]))))
  (testing "Double distribution of disjunction over conjunction"
    (is (= [:and
            [:and [:or :P :R] [:or :P :S]]
            [:and [:or :Q :R] [:or :Q :S]]]
           (r/distributivity [:or [:and :P :Q] [:and :R :S]])))
    (is (= [:and [:or :P :Q] [:or :R :S]]
           (r/distributivity [:or
                              [:or [:and :P :R] [:and :P :S]]
                              [:or [:and :Q :R] [:and :Q :S]]])))))

(deftest test-de-morgans-law
  (testing "Negation of conjunction"
    (is (= [:or [:not :P] [:not :Q]] (r/de-morgans-law [:not [:and :P :Q]])))
    (is (= [:not [:and :P :Q]] (r/de-morgans-law [:or [:not :P] [:not :Q]]))))
  (testing "Negation of disjunction"
    (is (= [:and [:not :P] [:not :Q]] (r/de-morgans-law [:not [:or :P :Q]])))
    (is (= [:not [:or :P :Q]] (r/de-morgans-law [:and [:not :P] [:not :Q]]))))
  (testing "Negation of conjunction substitution form"
    (is (= [:not [:or [:not :P] [:not :Q]] (r/de-morgans-law [:and :P :Q])]))
    (is (= [:and :P :Q] (r/de-morgans-law [:not [:or [:not :P] [:not :Q]]]))))
  (testing "Negation of disjunction substitution form"
    (is (= [:not [:and [:not :P] [:not :Q]] (r/de-morgans-law [:or :P :Q])]))
    (is (= [:or :P :Q] (r/de-morgans-law [:not [:and [:not :P] [:not :Q]]])))))

(deftest test-material-implication
  (testing "Material implication"
    (is (= [:or [:not :P] :Q] (r/material-implication [:impl :P :Q])))
    (is (= [:impl :P :Q] (r/material-implication [:or [:not :P] :Q])))))
