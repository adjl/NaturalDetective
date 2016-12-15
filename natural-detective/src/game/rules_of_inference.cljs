(ns game.rules-of-inference
  (:require [cljs.core.match :refer-macros [match]]))

;; Rules of Inference (Rules of Implication)
;; - Disjunctive syllogism (Modus tollendo ponens)
(defn disjunctive-syllogism
  "P ∨ Q, ¬P ⊢ Q"
  ([premise-0 premise-1]
   (disjunctive-syllogism [premise-0 premise-1]))
  ([premises]
   (match premises
     [[:or P Q] [:not _P]]
     (cond (= P _P) Q     ;; P ∨ Q, ¬P
           (= Q _P) P)    ;; Q ∨ P, ¬P
     [[:not _P] [:or P Q]]
     (cond (= P _P) Q     ;; ¬P, P ∨ Q
           (= Q _P) P)))) ;; ¬P, Q ∨ P
