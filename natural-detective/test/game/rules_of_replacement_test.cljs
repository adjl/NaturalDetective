(ns game.rules-of-replacement-test
  (:require [cljs.test :refer-macros [deftest is]]
            [game.rules-of-replacement :as r]))

(deftest test-double-negation-introduction
  (is (= [:not [:not :P]] (r/double-negation-introduction :P)))
  (is (= [:not [:not [:not :P]]] (r/double-negation-introduction [:not :P]))))

(deftest test-double-negation-elimination
  (is (= :P (r/double-negation-elimination [:not [:not :P]])))
  (is (= [:not :P] (r/double-negation-elimination [:not [:not [:not :P]]]))))

(deftest test-tautology
  (is (= :P (r/tautology [:and :P :P])))
  (is (= :P (r/tautology [:or :P :P]))))

(deftest test-commutativity
  (is (= [:and :Q :P] (r/commutativity [:and :P :Q])))
  (is (= [:or :Q :P] (r/commutativity [:or :P :Q]))))

(deftest test-associativity
  (is (= [:and [:and :P :Q] :R] (r/associativity [:and :P [:and :Q :R]])))
  (is (= [:or [:or :P :Q] :R] (r/associativity [:or :P [:or :Q :R]])))
  (is (= [:and :P [:and :Q :R]] (r/associativity [:and [:and :P :Q] :R])))
  (is (= [:or :P [:or :Q :R]] (r/associativity [:or [:or :P :Q] :R]))))

(deftest test-distributivity
  (is (= [:and [:and :P :Q] [:and :P :R]]
         (r/distributivity [:and :P [:and :Q :R]])))
  (is (= [:or [:or :P :Q] [:or :P :R]]
         (r/distributivity [:or :P [:or :Q :R]])))
  (is (= [:or [:and :P :Q] [:and :P :R]]
         (r/distributivity [:and :P [:or :Q :R]])))
  (is (= [:and [:or :P :Q] [:or :P :R]]
         (r/distributivity [:or :P [:and :Q :R]])))
  (is (= [:and :P [:and :Q :R]]
         (r/distributivity [:and [:and :P :Q] [:and :P :R]])))
  (is (= [:or :P [:or :Q :R]]
         (r/distributivity [:or [:or :P :Q] [:or :P :R]])))
  (is (= [:or :P [:and :Q :R]]
         (r/distributivity [:and [:or :P :Q] [:or :P :R]])))
  (is (= [:and :P [:or :Q :R]]
         (r/distributivity [:or [:and :P :Q] [:and :P :R]])))
  (is (= [:and
          [:and [:and :P :R] [:and :P :S]]
          [:and [:and :Q :R] [:and :Q :S]]]
         (r/distributivity [:and [:and :P :Q] [:and :R :S]])))
  (is (= [:or
          [:or [:or :P :R] [:or :P :S]]
          [:or [:or :Q :R] [:or :Q :S]]]
         (r/distributivity [:or [:or :P :Q] [:or :R :S]])))
  (is (= [:or
          [:or [:and :P :R] [:and :P :S]]
          [:or [:and :Q :R] [:and :Q :S]]]
         (r/distributivity [:and [:or :P :Q] [:or :R :S]])))
  (is (= [:and
          [:and [:or :P :R] [:or :P :S]]
          [:and [:or :Q :R] [:or :Q :S]]]
         (r/distributivity [:or [:and :P :Q] [:and :R :S]])))
  (is (= [:and [:and :P :Q] [:and :R :S]]
         (r/distributivity [:and
                            [:and [:and :P :R] [:and :P :S]]
                            [:and [:and :Q :R] [:and :Q :S]]])))
  (is (= [:or [:or :P :Q] [:or :R :S]]
         (r/distributivity [:or
                            [:or [:or :P :R] [:or :P :S]]
                            [:or [:or :Q :R] [:or :Q :S]]])))
  (is (= [:or [:and :P :Q] [:and :R :S]]
         (r/distributivity [:and
                            [:and [:or :P :R] [:or :P :S]]
                            [:and [:or :Q :R] [:or :Q :S]]])))
  (is (= [:and [:or :P :Q] [:or :R :S]]
         (r/distributivity [:or
                            [:or [:and :P :R] [:and :P :S]]
                            [:or [:and :Q :R] [:and :Q :S]]]))))
