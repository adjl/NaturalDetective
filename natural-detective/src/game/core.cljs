(ns ^:figwheel-always game.core
  (:require [cljsjs.phaser]
            [game.boot :refer [Boot]]
            [game.preload :refer [Preload]]
            [game.title-screen :refer [TitleScreen]]
            [game.main-game :refer [MainGame]]))

(enable-console-print!)

(defonce game (js/Phaser.Game.))

(defn -main []
  (let [state (.-state game)]
    (.add state "boot" Boot)
    (.add state "preload" Preload)
    (.add state "titleScreen" TitleScreen)
    (.add state "mainGame" MainGame)
    (.start state "boot")))

(-main)
