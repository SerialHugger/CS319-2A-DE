package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class EnemyType2 extends Enemy {

    final private int teleport_cooldown = 1000;
    private boolean is_skill_active = true;
    private int countdown = 0;

    EnemyType2(double width, double height, String assetLocation) {
        super(width, height, "player");

        String enemyType = "enemyHitBoxRectangle"; // todo change to 2
        super.initBody(assetLocation, enemyType);
    }

    public void update(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left) {
        // TODO: Why 10000? Explain with comments or make it a constant variable
        double random = Math.random() * 10000; // random for chance based updates

        if (delay) { // delay: a boolean value to delay shoots
            if (random < 150) { // %1.5 chance TODO: Constant problem for 150

                // TODO: explain or convert to a constant: 38.4 and -1
                boolean isObjectInScene = getX() <= width * 38.4 - gameRoot.getTranslateX() && getX() > gameRoot.getTranslateX() * -1;

                if (isObjectInScene) { // if the enemy is in the view of the player
                    String bulletType = "enemyBulletType1"; // TODO: change to enemybullettype2 when implemented
                    initBullet(GCF, bulletType);

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

        directionX = moveValues[0];
        directionY = moveValues[1];
        speed_x = moveValues[2];
        speed_y = moveValues[3];

        moveX(directionX, speed_x); // move X with given inputs
        moveY(directionY, speed_y); // move Y with given inputs

        //updates the space ships so they loop around map
        loopAroundTheMap(GCF.getWidth(), player, left);

        // Actions when collision
        for (Shape hitBox : hitBoxes) {
            if (hitBox instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBox);
                if (temp.isDead())
                    dead = true;
            } else if (hitBox instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBox);
                if (temp.isDead()) {
                    if (is_skill_active) {
                        countdown = 0;
                        is_skill_active = false;
                        temp.dead = false;
                        moveY(1, 50);
                    } else
                        dead = true;
                }
            }
        }

        if (!is_skill_active) {
            if (countdown == teleport_cooldown) {
                is_skill_active = true;
            } else if (countdown < teleport_cooldown)
                countdown += 5;
        }
    }
}
