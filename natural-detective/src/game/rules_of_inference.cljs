(ns game.rules-of-inference
  (:require [cljs.core.match :refer-macros [match]]))

;; Rules of Inference (Rules of Implication)
;; - Disjunctive syllogism (Modus tollendo ponens)
;; - Hypothetical syllogism

(defn disjunctive-syllogism
  "P ∨ Q, ¬P ⊢ Q"
  ([premise-0 premise-1]
   (disjunctive-syllogism [premise-0 premise-1]))
  ([premises]
   (match premises
     [[:or metavar-0 metavar-1] [:not metavar-2]]
     (cond (= metavar-0 metavar-2) metavar-1     ;; P ∨ Q, ¬P
           (= metavar-1 metavar-2) metavar-0)    ;; Q ∨ P, ¬P
     [[:not metavar-2] [:or metavar-0 metavar-1]]
     (cond (= metavar-0 metavar-2) metavar-1     ;; ¬P, P ∨ Q
           (= metavar-1 metavar-2) metavar-0)))) ;; ¬P, Q ∨ P

(defn hypothetical-syllogism
  "P → Q, Q → R ⊢ P → R"
  ([premise-0 premise-1]
   (hypothetical-syllogism [premise-0 premise-1]))
  ([premises]
   (match premises
     [[:impl metavar-0 metavar-1] [:impl metavar-2 metavar-3]]
     (cond (= metavar-1 metavar-2) ;; P → Q, Q → R
           [:impl metavar-0 metavar-3]
           (= metavar-0 metavar-3) ;; Q → R, P → Q
           [:impl metavar-2 metavar-1]))))
