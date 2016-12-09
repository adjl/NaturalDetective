(ns game.level-select)

(defn LevelSelect [game]
  (reify Object
    (preload [this])
    (create [this]
      (.. game -state (start "gameStage"))))) ;; Work on levelSelect later
