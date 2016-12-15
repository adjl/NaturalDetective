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
     [[:or P Q] [:not _P]]
     (cond (= P _P) Q     ;; P ∨ Q, ¬P
           (= Q _P) P)    ;; Q ∨ P, ¬P
     [[:not _P] [:or P Q]]
     (cond (= P _P) Q     ;; ¬P, P ∨ Q
           (= Q _P) P)))) ;; ¬P, Q ∨ P

(defn hypothetical-syllogism
  "P → Q, Q → R ⊢ P → R"
  ([premise-0 premise-1]
   (hypothetical-syllogism [premise-0 premise-1]))
  ([premises]
   (match premises
     [[:impl P Q] [:impl _Q R]]
     (cond (= Q _Q) [:impl P R]      ;; P → Q, Q → R
           (= P R)  [:impl _Q Q])))) ;; Q → R, P → Q
