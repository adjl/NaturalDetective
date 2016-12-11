(ns game.rules-of-replacement
  (:require [cljs.core.match :refer-macros [match]]))

;; Premise Backus-Naur Form
;; <Premise> ::= <Statement>
;; 						 | [<Not> <Premise>]
;; 						 | [<Connective> <Premise> <Premise>]
;; <Statement> ::= :A | :B | ...
;; <Not> ::= :not
;; <Connective> ::= :and | :or

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
    [_ P (_ :guard #(= P))] P))

(defn commutativity
  "(P ∧ Q) ↔ (Q ∧ P)
   (P ∨ Q) ↔ (Q ∨ P)"
  [proposition]
  (match proposition
    [connective P Q] [connective Q P]))

(defn associativity
  "(P ∧ (Q ∧ R)) ↔ ((P ∧ Q) ∧ R)
   (P ∨ (Q ∨ R)) ↔ ((P ∨ Q) ∨ R)"
  [proposition]
  (match proposition
    ;; (P ∘ (Q ∘ R)) → ((P ∘ Q) ∘ R)
    [connective P [(_ :guard #(= connective)) Q R]]
    [connective [connective P Q] R]
    ;; ((P ∘ Q) ∘ R) → (P ∘ (Q ∘ R))
    [connective [(_ :guard #(= connective)) P Q] R]
    [connective P [connective Q R]]))

(defn distributivity
  "(P ∧ (Q ∧ R)) ↔ ((P ∧ Q) ∧ (Q ∧ R))
   (P ∨ (Q ∨ R)) ↔ ((P ∨ Q) ∨ (Q ∨ R))
   (P ∧ (Q ∨ R)) ↔ ((P ∧ Q) ∨ (Q ∧ R))
   (P ∨ (Q ∧ R)) ↔ ((P ∨ Q) ∧ (Q ∨ R))"
  [proposition]
  (match proposition
    ;; (((P ∘ R) ∘ (P ∘ S)) ∘ ((Q ∘ R) ∘ (Q ∘ S))) → ((P ∘ Q) ∘ (R ∘ S))
    [outer-connective
     [(_ :guard #(= outer-connective))
      [inner-connective P R]
      [(_ :guard #(= inner-connective)) (_ :guard #(= P)) S]]
     [(_ :guard #(= outer-connective))
      [(_ :guard #(= inner-connective)) Q (_ :guard #(= R))]
      [(_ :guard #(= inner-connective)) (_ :guard #(= Q)) (_ :guard #(= S))]]]
    [inner-connective [outer-connective P Q] [outer-connective R S]]
    ;; ((P ∘ Q) ∘ (P|R ∘ R|S)) → ...
    [outer-connective
     [inner-connective P Q]
     [(_ :guard #(= inner-connective)) R S]]
    (if (= P R)
      ;; ((P ∘ Q) ∘ (P ∘ R)) → (P ∘ (Q ∘ R))
      [inner-connective P [outer-connective Q S]]
      ;; ((P ∘ Q) ∘ (R ∘ S)) → (((P ∘ R) ∘ (P ∘ S)) ∘ ((Q ∘ R) ∘ (Q ∘ S)))
      [inner-connective
       [inner-connective [outer-connective P R] [outer-connective P S]]
       [inner-connective [outer-connective Q R] [outer-connective Q S]]])
    ;; (P ∘ (Q ∘ R)) → ((P ∘ Q) ∘ (P ∘ R))
    [outer-connective P [inner-connective Q R]]
    [inner-connective [outer-connective P Q] [outer-connective P R]]))

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
