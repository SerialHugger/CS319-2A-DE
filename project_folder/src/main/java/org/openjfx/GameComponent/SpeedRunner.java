package org.openjfx.GameComponent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

//enemyType1

public class SpeedRunner extends Enemy {
    private int rotate = 0;
    SpeedRunner(double width, double height, ImagePattern asset) {
        super(width, height, "speedRunner");
        this.height = magicConverter(108);
        this.width = magicConverter(64);
        super.initBody(asset, width, height);
        hitBoxes[0] = new ComponentHitBoxCircle(this.width / 2, "enemy", "speedRunner");
        body = new Circle(this.width/2);
        body.setTranslateX(hitBoxes[0].getTranslateX() + this.width/2);
        body.setTranslateY(hitBoxes[0].getTranslateY() + this.width/2);
        body.setFill(asset);
        hitBoxes[0].setTranslateX(body.getTranslateX());
        hitBoxes[0].setTranslateY(body.getTranslateY());
    }

    public void moveSpeedRunner(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left, int speedFactor) {

        // TODO: Why 10000? Explain with comments or make it a constant variable
        double random = Math.random() * 10000; // random for chance based updates

        if (delay) { // delay: a boolean value to delay shoots
            if (random < 150) { // %1.5 chance TODO: Constant problem for 150

                // TODO: explain or convert to a constant: 38.4 and -1
                boolean isObjectInScene = getX() <= gameRoot.getWidth() - gameRoot.getTranslateX() && getX() > gameRoot.getTranslateX() * -1;

                if (isObjectInScene) { // if the enemy is in the view of the player
                    String bulletType = "laserBullet";
                    //shootBehaviour.shoot(GCF , this , gameRoot);//speedrunner should not attack

                    delay = false; // make delay false
                    delayTimer = 50; // start delay timer TODO: Constant problem for 500
                }
            }
        } else { // if delay is on
            if (delayTimer == 0) // if delay timer finished
                delay = true; // make delay true
            delayTimer -= 5; // decrease delay timer TODO: Why? Could you explain
        }

        // get new coordinates and speed for the next frame
        int[] moveValues = getMoveValues(random);
        rotate += 5;
        body.setRotate(rotate);
        directionX = moveValues[0];
        directionY = moveValues[1];
        speed_x = moveValues[2];
        speed_y = moveValues[3];

        moveX(directionX, speed_x * 5); // move X with given inputs
        moveY(directionY, speed_y); // move Y with given inputs

        //updates the space ships so they loop around map
        loopAroundTheMap(GCF.width, player, left);

        // Actions when collision
        dead = updateDeath();
        if (dead) {
            dropAbility(GCF);
            explode("explode", GCF);
        }
    }
}
