(ns game.preload)

(defn Preload [game]
  (let []
    (reify Object
      (preload [this]
        (.. game -load (image "logo" "phaser.png")))
      (create [this]
        (let [center-x (.. game -world -centerX)
              center-y (.. game -world -centerY)
              logo (.. game -add (sprite center-x center-y "logo"))]
          (.. logo -anchor (setTo 0.5 0.5)))))))
