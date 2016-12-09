(ns game.title-screen)

(defn- add-logo [game]
  (let [center-x (.. game -world -centerX)
        logo-y (/ (.. game -world -height) 4)
        logo (.. game -add (image center-x logo-y "logo"))]
    (.. logo -anchor (setTo 0.5 0.5))))

(defn- add-buttons [game]
  (let [center-x (.. game -world -centerX)
        button-y (/ (.. game -world -height) 1.25)
        start-button (.. game -add (button center-x button-y "startButton"))]
    (.. start-button -anchor (setTo 0.5 0.5))
    (.. start-button -onInputDown
        (add #(.. game -state (start "levelSelect"))))))

(defn TitleScreen [game]
  (reify Object
    (preload [this]
      (let [loader (.-load game)]
        (.image loader "logo" "assets/logo.png")
        (.image loader "startButton" "assets/start-button.png")))
    (create [this]
      (.. game -stage (setBackgroundColor "#fff"))
      (add-logo game)
      (add-buttons game))))
