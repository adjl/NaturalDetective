(ns game.natural-deduction-test
  (:require [cljs.test :refer-macros [deftest is]]
            [game.natural-deduction :as nd]))

(deftest test-double-negation-introduction
  (is (= (nd/double-negation-introduction :P) [:not [:not :P]]))
  (is (= (nd/double-negation-introduction [:not :P])
         [:not [:not [:not :P]]])))

