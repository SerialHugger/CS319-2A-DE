package org.openjfx.GameComponent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

public class Dodger extends Enemy {

    final private int teleport_cooldown = 1000;
    private boolean is_skill_active = true;
    private int countdown = 0;
    /**
     * constructor for enemy type dodger
     * @param width is the width of dodger
     * @param height is the height of dodger
     * @param assets is the image assets of dodger
     */
    Dodger(double width, double height, ImagePattern[] assets) {
        super(width, height, "dodger");

        this.height = magicConverter(60);
        this.width = magicConverter(60);
        animationFrames = new ImagePattern[14];
        for (int i = 0; i < 14; i++)
            animationFrames[i] = assets[i];
        super.initBody(assets[0], width, height);
        body.setFill(animationFrames[currentState]);
        delay = false;
        delayTimer = 0;
        setShootBehaviour(new ShootWithLaserBullet());
    }
    /**
     *
     * @param GCF needs gamecomponentfactory since it updates from there
     * @param gameRoot needs the pane
     * @param player needs the player to shoot it
     * @param left indicator if it is looking left
     * @param speedFactor an integer indicates the speed of the dividus
     */
    public void update(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left, int speedFactor) {
        // TODO: Why 10000? Explain with comments or make it a constant variable
        double random = Math.random() * 10000; // random for chance based updates
        if (delay) { // delay: a boolean value to delay shoots
            if (random < 150) { // %1.5 chance TODO: Constant problem for 150

                // TODO: explain or convert to a constant: 38.4 and -1
                boolean isObjectInScene = getX() <= gameRoot.getWidth() - gameRoot.getTranslateX() && getX() > gameRoot.getTranslateX() * -1;

                if (isObjectInScene) { // if the enemy is in the view of the player
                    String bulletType = "laserBullet"; // TODO: change to enemybullettype2 when implemented
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
        currentState++;
        if (currentState == 14)
            currentState = 0;
        body.setFill(animationFrames[currentState]);
        // get new coordinates and speed for the next frame
        int[] moveValues = getMoveValues(random);

        directionX = moveValues[0];
        directionY = moveValues[1];
        speed_x = moveValues[2] + (speedFactor/100);
        speed_y = moveValues[3];

        moveX(directionX, speed_x); // move X with given inputs
        moveY(directionY, speed_y); // move Y with given inputs

        //updates the space ships/enemyShips so they loop around map
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
                    if (is_skill_active) {
                        countdown = 0;
                        is_skill_active = false;
                        temp.dead = false;
                        int dodgeDirection;
                        if (Math.random() <= 0.5)
                            dodgeDirection = 1;
                        else
                            dodgeDirection = -1;
                        double dodgeAmount = (Math.random() * 200) + 100;
                        moveY(dodgeDirection, dodgeAmount);
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

        if (dead) {
            dropAbility(GCF);
            explode("explode", GCF);
        }
    }
}
