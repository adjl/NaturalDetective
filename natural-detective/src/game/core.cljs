(ns game.core
  (:require [cljsjs.phaser]
            [game.game-stage :refer [GameStage]]
            [game.level-select :refer [LevelSelect]]
            [game.score-screen :refer [ScoreScreen]]
            [game.title-screen :refer [TitleScreen]]))

(enable-console-print!)

(defonce game (js/Phaser.Game. "100%" "100%"))

(defn -main []
  (let [state (.-state game)]
    (.add state "titleScreen" TitleScreen)
    (.add state "levelSelect" LevelSelect)
    (.add state "gameStage" GameStage)
    (.add state "scoreScreen" ScoreScreen)
    (.start state "titleScreen")))

(-main)
