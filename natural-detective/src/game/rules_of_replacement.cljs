(ns game.rules-of-replacement
  (:require [cljs.core.match :refer-macros [match]]))

;; Premise Backus-Naur Form
;; <Premise> ::= <Statement>
;; 						 | [<Not> <Premise>]
;; 						 | [<c> <Premise> <Premise>]
;; <Statement> ::= :A | :B | ...
;; <Not> ::= :not
;; <c> ::= :and | :or

;; Rules of Replacement (Rules of Equivalence)
;; - Double Negation: Introduction, Elimination
;; - Tautology
;; - Commutativity
;; - Associativity
;; - Distributivity
;; - De Morgan's Laws (De Morgan's Rule)
;; - Transposition
;; - Material Implication
;; - Exportation?
;; - Negation introduction?

(defn double-negation-introduction
  "P → ¬¬P"
  [proposition]
  [:not [:not proposition]])

(defn double-negation-elimination
  "¬¬P → P"
  [proposition]
  (let [proposition (flatten proposition)]
    (if (odd? (count proposition))
      (last proposition)
      (->> proposition (take-last 2) vec))))

(defn tautology
  "(P ∧ P) → P
   (P ∨ P) → P"
  [proposition]
  (match proposition
    [_ P _P]
    (if (= P _P) P)))

(defn commutativity
  "(P ∧ Q) ↔ (Q ∧ P)
   (P ∨ Q) ↔ (Q ∨ P)"
  [proposition]
  (match proposition
    [c P Q] [c Q P]))

(defn associativity
  "(P ∧ (Q ∧ R)) ↔ ((P ∧ Q) ∧ R)
   (P ∨ (Q ∨ R)) ↔ ((P ∨ Q) ∨ R)"
  [proposition]
  (match proposition
    ;; (P ∘ (Q ∘ R)) → ((P ∘ Q) ∘ R)
    [c P [_c Q R]]
    (if (= c _c)
      [c [c P Q] R])

    ;; ((P ∘ Q) ∘ R) → (P ∘ (Q ∘ R))
    [c [_c P Q] R]
    (if (= c _c)
      [c P [c Q R]])))

(defn distributivity
  "(P ∧ (Q ∧ R)) ↔ ((P ∧ Q) ∧ (Q ∧ R))
   (P ∨ (Q ∨ R)) ↔ ((P ∨ Q) ∨ (Q ∨ R))
   (P ∧ (Q ∨ R)) ↔ ((P ∧ Q) ∨ (Q ∧ R))
   (P ∨ (Q ∧ R)) ↔ ((P ∨ Q) ∧ (Q ∨ R))"
  [proposition]
  (match proposition
    ;; (((P ∘ R) ∘ (P ∘ S)) ∘ ((Q ∘ R) ∘ (Q ∘ S))) → ((P ∘ Q) ∘ (R ∘ S))
    [co
     [_co [ci P R] [_ci _P S]]
     [__co [__ci Q _R] [___ci _Q _S]]]
    (if (and (= co _co __co) (= ci _ci __ci ___ci)
             (= P _P) (= Q _Q) (= R _R) (= S _S))
      [ci [co P Q] [co R S]])

    ;; ((P ∘ Q) ∘ (P|R ∘ R|S)) → ...
    [co [ci P Q] [_ci R S]]
    (if (and (= ci _ci) (= P R))
      ;; ((P ∘ Q) ∘ (P ∘ R)) → (P ∘ (Q ∘ R))
      [ci P [co Q S]]
      ;; ((P ∘ Q) ∘ (R ∘ S)) → (((P ∘ R) ∘ (P ∘ S)) ∘ ((Q ∘ R) ∘ (Q ∘ S)))
      [ci
       [ci [co P R] [co P S]]
       [ci [co Q R] [co Q S]]])

    ;; (P ∘ (Q ∘ R)) → ((P ∘ Q) ∘ (P ∘ R))
    [co P [ci Q R]]
    [ci [co P Q] [co P R]]))

;; References
;; https://en.wikipedia.org/wiki/Rule_of_replacement, 11/12/16
;; https://en.wikipedia.org/wiki/Double_negation, 11/12/16
;; https://en.wikipedia.org/wiki/Tautology_(rule_of_inference), 11/12/16
;; https://en.wikipedia.org/wiki/Commutative_property, 11/12/16
;; https://en.wikipedia.org/wiki/Associative_property, 11/12/16
;; https://en.wikipedia.org/wiki/Distributive_property, 11/12/16

;; https://en.wikipedia.org/wiki/Rule_of_inference, 11/12/16
;; https://en.wikipedia.org/wiki/Premise, 11/12/16

;; https://en.wikipedia.org/wiki/List_of_mathematical_symbols, 11/12/16
;; https://en.wikipedia.org/wiki/List_of_logic_symbols, 11/12/16
;; https://en.wikipedia.org/wiki/Proposition, 11/12/16
