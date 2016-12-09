(ns game.natural-deduction)

(defn tautology
  "P ∧ P ≡ P ∨ P ≡ P"
  ([premise]
   (if (keyword? premise)
     premise
     (apply tautology premise)))
  ([connective P Q]
   (let [P (tautology P)
         Q (tautology Q)]
     (if (= P Q) P [connective P Q])))
  #_([connective P Q & R] ; Flattening premises
     (->> (-> (into [P Q] R) flatten)
          (filter #(not= connective %))
          (reduce (fn [P Q] (tautology connective P Q))))))
