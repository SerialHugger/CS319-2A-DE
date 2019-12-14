package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

//enemyType1

public class DivingWind extends Enemy {

    DivingWind(double width, double height, String assetLocation) {
        super(width / 60.95, height / 18, "divingWind");
        this.width = width / 60.95;
        this.height = height / 18;
        super.initBody(assetLocation, width, height);
    }

    public void moveDivingWind(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left) {

        // TODO: Why 10000? Explain with comments or make it a constant variable
        // double random = Math.random() * 10000; // random for chance based updates
        double xOfPlayer = player.getX() + player.getWidth() / 2;
        double yOfPlayer = player.getX() + player.height / 2;
        speed_x = 3;
        speed_y = 4;
        if (delay) { // delay: a boolean value to delay shoots
            // if (this.getX() > xOfPlayer && this.getY() < )
            /*if (random < 150) { // %1.5 chance TODO: Constant problem for 150

                // TODO: explain or convert to a constant: 38.4 and -1
                boolean isObjectInScene = getX() <= width * 38.4 - gameRoot.getTranslateX() && getX() > gameRoot.getTranslateX() * -1;

                if (isObjectInScene) { // if the enemy is in the view of the player
                    String bulletType = "laserBullet";
                    initBullet(GCF, bulletType);

                    delay = false; // make delay false
                    delayTimer = 500; // start delay timer TODO: Constant problem for 500
                }
            }*/
        } else { // if delay is on
            if (delayTimer == 0) // if delay timer finished
                delay = true; // make delay true
            delayTimer -= 5; // decrease delay timer TODO: Why? Could you explain
        }

        // get new coordinates and speed for the next frame
        /*int[] moveValues = getMoveValues(random);

        directionX = moveValues[0];
        directionY = moveValues[1];
        speed_x = moveValues[2];
        speed_y = moveValues[3];

        moveX(directionX, speed_x); // move X with given inputs
        moveY(directionY, speed_y); // move Y with given inputs
        */
        //updates the space ships so they loop around map
        loopAroundTheMap(GCF.width, player, left);

        // Actions when collision
        for (Shape hitBox : hitBoxes) {
            if (hitBox instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBox);
                if (temp.isDead())
                    dead = true;
            } else if (hitBox instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBox);
                if (temp.isDead()) {
                    dead = true;
                }
            }
        }
    }
}
