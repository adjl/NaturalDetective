(ns game.game-stage)

(defn GameStage [game]
  (reify Object
    (preload [this])
    (create [this])))
