package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class EnemyType2 extends Enemy {

    final private int teleport_cooldown = 1000;
    private boolean is_skill_active = true;
    private int countdown = 0;

    EnemyType2(double width, double height, String assetLocation) {
        super(width, height, "player");
        hitBoxes = new Shape[1]; // there is only 1 hit box --- for now
        hitBoxes[0] = new ComponentHitBoxRectangle(width, height, "enemyHitBoxRectangleType1"); // setup the Rectangle hit box
        body = new Rectangle(width, height, null); //setup the body
        fillImage(assetLocation); // fill the body with image at assetLocation
        double startingX = (width * 38.4 * -2) + Math.random() * (width * 38.4 * 4); // starting X of the enemy, randomly selected
        double startingY = (Math.random() * ((height * 36) - height * 2)); // starting Y of the enemy, randomly selected
        body.setTranslateX(startingX); // setX
        body.setTranslateY(startingY); // setY
        hitBoxes[0].setTranslateX(startingX); // setX for hit box
        hitBoxes[0].setTranslateY(startingY); // setY for hit box
        speed_x = Math.random() * 7; // initial speed of x, random
        speed_y = Math.random() * 7; // initial speed of y, random
    }

    public void update(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left) {
        double random = Math.random() * 10000; // random for chance based updates
        if (delay) { // delay for shooting bullets pew pew
            if (random < 150) { // %1.5 chance
                if (getX() <= width * 38.4 - gameRoot.getTranslateX() && getX() > gameRoot.getTranslateX() * -1) { // if the enemy is in the view of the player
                    delay = false; // make delay false
                    EnemyBulletType1 enemyBullet = (EnemyBulletType1) GCF.createComponent("enemyBulletType2"); // create the bullet
                    enemyBullet.toLeft = facingLeft; // make it face left
                    enemyBullet.setX(body.getTranslateX()); // set its X
                    enemyBullet.setY(body.getTranslateY()); // set its Y
                    enemyBullet.addShapes(gameRoot); // add its shapes to Root
                    delayTimer = 500; // start delay timer
                }
            }
        } else { // if delay is on
            if (delayTimer == 0) // if delay timer finished
                delay = true; // make delay true
            delayTimer -= 5; // decrease delay timer
        }
        if (random < 30) { // delay for changing directions and speed, %0.3 chance
            speed_x = Math.random() * 6 + 1; // set speed x, randomly
            speed_y = Math.random() * 6 + 1; // set speed y, randomly
            directionCheckX = Math.random() * 2;
            directionCheckY = Math.random() * 2;
            if (directionCheckX < 1) // %50 chance
                directionX = -1; // it goes left
            if (directionCheckY < 1) // %50 chance
                directionY = -1; // it goes up
        }
        if (getY() >= gameRoot.getHeight() - height * 2 || getY() < 0) { // if the height boundaries reached
            directionY *= -1; // change direction
        }
        moveX(directionX, speed_x); // move X with given inputs
        moveY(directionY, speed_y); // move Y with given inputs

        //updates the space ships so they loop around map
        if (left) { // if button to go left, "A", is pressed
            if (body.getTranslateX() > player.getX() + (2 * GCF.getWidth())) { // if he enemy ship is width * 2 away from the ship teleport it to other side
                for (int i = 0; i < hitBoxes.length; i++) {
                    hitBoxes[i].setTranslateX(hitBoxes[i].getTranslateX() - (4 * GCF.getWidth()));
                }
                body.setTranslateX(body.getTranslateX() - (4 * GCF.getWidth()));
            }
        } else { // if button to go right, "D", is pressed
            if (body.getTranslateX() < player.getX() - (2 * GCF.getWidth())) { // if he enemy ship is width * 2 away from the ship teleport it to other side
                for (int i = 0; i < hitBoxes.length; i++) {
                    hitBoxes[i].setTranslateX(hitBoxes[i].getTranslateX() + (4 * GCF.getWidth()));
                }
                body.setTranslateX(body.getTranslateX() + (4 * GCF.getWidth()));
            }
        }
        for (int i = 0; i < hitBoxes.length; i++) {
            if (hitBoxes[i] instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBoxes[i]);
                if (temp.isDead()) {
                    if (is_skill_active) {
                        countdown = 0;
                        is_skill_active = false;
                    } else
                        dead = true;
                }

            } else if (hitBoxes[i] instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBoxes[i]);
                if (temp.isDead())
                    dead = true;
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
