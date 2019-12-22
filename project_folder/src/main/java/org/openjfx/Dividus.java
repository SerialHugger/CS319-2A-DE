package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;


// enemy type 4

public class Dividus extends Enemy {

    Dividus(double width, double height, ImagePattern asset) {
        super(width, height, "dividus");
        this.height = magicConverter(120); // height of dividus
        this.width = magicConverter(200); // width of dividus
        super.initBody(asset, width, height);
        setShootBehaviour(new ShootWithGuidedBullet() );
    }

    public void update(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left , int speedFactor) {

        // TODO: Why 10000? Explain with comments or make it a constant variable
        double random = Math.random() * 10000; // random for chance based updates

        if (delay) { // delay: a boolean value to delay shoots
            if (random < 150) { // %1.5 chance TODO: Constant problem for 150

                // TODO: explain or convert to a constant: 38.4 and -1
                boolean isObjectInScene = getX() <= gameRoot.getWidth() - gameRoot.getTranslateX() && getX() > gameRoot.getTranslateX() * -1;

                if (isObjectInScene) { // if the enemy is in the view of the player
                    String bulletType = "guidedbullet";
                    shootBehaviour.shoot(GCF,this,gameRoot);

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
        speed_x = moveValues[2] * speedFactor;
        speed_y = moveValues[3];

        moveX(directionX, speed_x); // move X with given inputs
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
    /**
     * TODO: enter description
     * @param random TODO: enter description
     * @return new directionX directionY values with speed_x and speed_y values for the next frame
     */
    public int[] getMoveValues(double random) {
        // TODO: 30 value should be constant
        if (random < 30) { // delay for changing directions and speed, %0.3 chance
            speed_x = Math.random() * 2 + 1; // set speed x, randomly TODO: constant
            speed_y = Math.random() * 2 + 1; // set speed y, randomly TODO: constant
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

        return new int[]{directionX, directionY, (int)speed_x, (int)speed_y};
    }

    public void createAtlases(GameComponentFactory GCF){
        Atlas temp1 = (Atlas) GCF.createComponent("atlas");
        Atlas temp2 = (Atlas) GCF.createComponent("atlas");
        temp1.addShapes(gameRoot);
        temp2.addShapes(gameRoot);
        temp1.setX(body.getTranslateX());
        temp1.setY(body.getTranslateY());
        temp2.setX(body.getTranslateX());
        temp2.setY(body.getTranslateY());
    }

}
