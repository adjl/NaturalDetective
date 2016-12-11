(ns game.natural-deduction)

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

(defn double-negation-introduction "P -> ¬¬P"
  [proposition]
  [:not [:not proposition]])

(defn double-negation-elimination "¬¬P -> P"
  [proposition]
  (let [proposition (flatten proposition)]
    (if (odd? (count proposition))
      (last proposition)
      (->> proposition (take-last 2) vec))))

;; References
;; https://en.wikipedia.org/wiki/List_of_logic_symbols
;; https://en.wikipedia.org/wiki/Proposition
;; https://en.wikipedia.org/wiki/Premise
;; https://en.wikipedia.org/wiki/Rule_of_inference
;; https://en.wikipedia.org/wiki/Rule_of_replacement
;; https://en.wikipedia.org/wiki/Double_negation
