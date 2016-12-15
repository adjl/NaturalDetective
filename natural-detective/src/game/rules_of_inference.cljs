(ns game.rules-of-inference
  (:require [cljs.core.match :refer-macros [match]]))

;; Rules of Inference (Rules of Implication)
;; - Conjunction introduction
;; - Disjunctive syllogism (Modus tollendo ponens)
;; - Hypothetical syllogism
;; - Modus ponens (Modus ponendo ponens)
;; - Modus tollens (Modus tollendo tollens)

(defn conjunction-introduction
  "P, Q ⊢ P ∧ Q"
  ([premise-0 premise-1]
   (conjunction-introduction [premise-0 premise-1]))
  ([[metavar-0 metavar-1]]
   [:and metavar-0 metavar-1]))

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

(defn modus-ponens
  "P → Q, P ⊢ Q"
  ([premise-0 premise-1]
   (modus-ponens [premise-0 premise-1]))
  ([premises]
   (match premises
     [[:impl metavar-0 metavar-1] metavar-2] ;; P → Q, P
     (if (= metavar-0 metavar-2) metavar-1)
     [metavar-2 [:impl metavar-0 metavar-1]] ;; P, P → Q
     (if (= metavar-0 metavar-2) metavar-1))))

(defn modus-tollens
  "P → Q, ¬Q ⊢ ¬P"
  ([premise-0 premise-1]
   (modus-tollens [premise-0 premise-1]))
  ([premises]
   (match premises
     [[:impl metavar-0 metavar-1] [:not metavar-2]] ;; P → Q, ¬Q
     (if (= metavar-1 metavar-2) [:not metavar-0])
     [[:not metavar-2] [:impl metavar-0 metavar-1]] ;; ¬Q, P → Q
     (if (= metavar-1 metavar-2) [:not metavar-0]))))
