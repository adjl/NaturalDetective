(ns game.natural-deduction-test
  (:require [cljs.test :refer-macros [deftest is]]
            [game.natural-deduction :as nd]))

(deftest test-double-negation-introduction
  (is (= (nd/double-negation-introduction :P) [:not [:not :P]]))
  (is (= (nd/double-negation-introduction [:not :P])
         [:not [:not [:not :P]]])))

(deftest test-double-negation-elimination
  (is (= (nd/double-negation-elimination [:not [:not :P]]) :P))
  (is (= (nd/double-negation-elimination [:not [:not [:not :P]]])
         [:not :P])))
