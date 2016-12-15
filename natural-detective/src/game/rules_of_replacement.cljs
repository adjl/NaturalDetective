(ns game.rules-of-replacement
  (:require [cljs.core.match :refer-macros [match]]))

;; Rules of Replacement (Rules of Equivalence)
;; - Double Negation: Introduction, Elimination
;; - Tautology
;; - Commutativity
;; - Associativity
;; - Distributivity
;; - De Morgan's Laws (De Morgan's Rule)
;; - Material Implication
;; - Transposition
;; - Exportation

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
  "P ∧ P → P
   P ∨ P → P"
  [proposition]
  (match proposition
    [_ P _P]
    (if (= P _P) P)))

(defn commutativity
  "P ∧ Q ↔ Q ∧ P
   P ∨ Q ↔ Q ∨ P"
  [proposition]
  (match proposition
    [c P Q] [c Q P]))

(defn associativity
  "P ∧ (Q ∧ R) ↔ (P ∧ Q) ∧ R
   P ∨ (Q ∨ R) ↔ (P ∨ Q) ∨ R"
  [proposition]
  (match proposition
    ;; P∘(Q∘R) → (P∘Q)∘R
    [c P [_c Q R]]
    (if (= c _c)
      [c [c P Q] R])
    ;; (P∘Q)∘R → P∘(Q∘R)
    [c [_c P Q] R]
    (if (= c _c)
      [c P [c Q R]])))

(defn distributivity
  "P ∧ (Q ∨ R) ↔ (P ∧ Q) ∨ (Q ∧ R)
   P ∨ (Q ∧ R) ↔ (P ∨ Q) ∧ (Q ∨ R)
   (P ∨ Q) ∧ (R ∨ S) ↔ (P ∨ R) ∧ (P ∨ S) ∧ (Q ∨ R) ∧ (Q ∨ S)
   (P ∧ Q) ∨ (R ∧ S) ↔ (P ∧ R) ∨ (P ∧ S) ∨ (Q ∧ R) ∨ (Q ∧ S)"
  [proposition]
  (match proposition
    ;; (P∘R)∘(P∘S)∘(Q∘R)∘(Q∘S) → (P∘Q)∘(R∘S)
    [co
     [_co [ci P R] [_ci _P S]]
     [__co [__ci Q _R] [___ci _Q _S]]]
    (if (and (= co _co __co) (= ci _ci __ci ___ci)
             (= P _P) (= Q _Q) (= R _R) (= S _S))
      [ci [co P Q] [co R S]])

    ;; (P∘Q)∘(P|R∘R|S) → ...
    [co [ci P Q] [_ci R S]]
    (if (and (= ci _ci) (= P R))
      ;; (P∘Q)∘(P∘R) → P∘(Q∘R)
      [ci P [co Q S]]
      ;; (P∘Q)∘(R∘S) → (P∘R)∘(P∘S)∘(Q∘R)∘(Q∘S)
      [ci
       [ci [co P R] [co P S]]
       [ci [co Q R] [co Q S]]])

    ;; P∘(Q∘R) → (P∘Q)∘(P∘R)
    [co P [ci Q R]]
    [ci [co P Q] [co P R]]))

(defn de-morgans-law
  "¬(P ∧ Q) ↔ ¬P ∨ ¬Q
   ¬(P ∨ Q) ↔ ¬P ∧ ¬Q
   P ∧ Q ↔ ¬(¬P ∨ ¬Q)
   P ∨ Q ↔ ¬(¬P ∧ ¬Q)"
  [proposition]
  (letfn [(flip [c] (if (= c :and) :or :and))]
    (match proposition
      ;; ¬(¬P∘¬Q) → P∘Q
      [:not [c [:not P] [:not Q]]] [(flip c) P Q]
      ;; ¬(P∘Q) → ¬P∘¬Q
      [:not [c P Q]]               [(flip c) [:not P] [:not Q]]
      ;; ¬P∘¬Q → ¬(P∘Q)
      [c [:not P] [:not Q]]        [:not [(flip c) P Q]]
      ;; P∘Q → ¬(¬P∘¬Q)
      [c P Q]                      [:not [(flip c) [:not P] [:not Q]]])))

(defn material-implication
  "P → Q ↔ ¬P ∨ Q"
  [proposition]
  (match proposition
    ;; P→Q → ¬P∨Q
    [:impl P Q]      [:or [:not P] Q]
    ;; ¬P∨Q → P→Q
    [:or [:not P] Q] [:impl P Q]))

(defn transposition
  "P → Q ↔ ¬Q → ¬P"
  [proposition]
  (match proposition
    ;; ¬Q→¬P → P→Q
    [:impl [:not Q] [:not P]] [:impl P Q]
    ;; P→Q → ¬Q→¬P
    [:impl P Q]               [:impl [:not Q] [:not P]]))

(defn exportation
  "(P ∧ Q) → R ↔ P → (Q → R)"
  [proposition]
  (match proposition
    ;; (P∧Q)→R → P→(Q→R)
    [:impl [:and P Q] R]  [:impl P [:impl Q R]]
    ;; P→(Q→R) → (P∧Q)→R
    [:impl P [:impl Q R]] [:impl [:and P Q] R]))
