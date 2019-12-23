package org.openjfx.GameComponent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

//enemyType1

public class Dienamite extends Enemy {
    Dienamite(double width, double height, ImagePattern[] assets) {
        super(width, height, "dienamite");
        this.height = magicConverter(60);
        this.width = magicConverter(90);
        animationFrames = assets;
        super.initBody(assets[0], width, height);
        setShootBehaviour(new ShootWithLaserBullet());
    }

    public void moveDienamite(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left, int speedFactor) {

        // TODO: Why 10000? Explain with comments or make it a constant variable
        double random = Math.random() * 10000; // random for chance based updates

        if (delay) { // delay: a boolean value to delay shoots
            if (random < 150) { // %1.5 chance TODO: Constant problem for 150

                // TODO: explain or convert to a constant: 38.4 and -1
                boolean isObjectInScene = getX() <= gameRoot.getWidth() - gameRoot.getTranslateX() && getX() > gameRoot.getTranslateX() * -1;

                if (isObjectInScene) { // if the enemy is in the view of the player
                    String bulletType = "laserBullet";
                    shootBehaviour.shoot(GCF, this, gameRoot);

                    delay = false; // make delay false
                    delayTimer = 500; // start delay timer TODO: Constant problem for 500
                }
            }
        } else { // if delay is on
            if (delayTimer == 0) // if delay timer finished
                delay = true; // make delay true
            delayTimer -= 5; // decrease delay timer TODO: Why? Could you explain
        }

        // get new coordinates and speed for the next frame
        int[] moveValues = getMoveValues(random);

        directionX = moveValues[0] * speedFactor;
        directionY = moveValues[1];
        speed_x = moveValues[2];
        speed_y = moveValues[3];

        moveX(directionX, speed_x); // move X with given inputs
        moveY(directionY, speed_y); // move Y with given inputs

        //updates the space ships so they loop around map
        loopAroundTheMap(GCF.width, player, left);

        // Actions when collision
        dead = updateDeath();

        if (dead) {
            dropAbility(GCF);
            explode("enemySelfDestruct", GCF);
        }

        counter += 1;
        if (counter % 10 == 0) {
            currentState += 1;
            counter = 0;
        }
        if (currentState == animationFrames.length)
            currentState = 0;
        body.setFill(animationFrames[currentState]);
    }
}