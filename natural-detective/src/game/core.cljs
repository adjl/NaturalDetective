(ns ^:figwheel-always game.core
  (:require [cljsjs.phaser]))

(enable-console-print!)

(defonce game (js/Phaser.Game.))

(defn preload []
  (let [scale (.-scale game)]
    (set! (.-pageAlignHorizontally scale) true)
    (set! (.-pageAlignVertically scale) true))
  (.. game -load (image "logo" "phaser.png"))
  (.. game -state (start "create")))

(defn create []
  (let [center-x (.. game -world -centerX)
        center-y (.. game -world -centerY)
        logo (.. game -add (sprite center-x center-y "logo"))]
    (.. logo -anchor (setTo 0.5 0.5))))

(defn update-state []
  (.log js/console "Updating..."))

(defn render []
  (.log js/console "Rendering..."))

(defn -main []
  (let [state (.-state game)]
    (.add state "preload" (clj->js {:preload preload}))
    (.add state "create" (clj->js {:create create}))
    (.add state "update" (clj->js {:update update-state}))
    (.add state "render" (clj->js {:render render}))
    (.start state "preload")))

(-main)
