package org.openjfx.GameComponent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

//enemyType1

public class Atlas extends Enemy {

    protected Civilian target;
    private boolean ascending;
    private boolean chasing;
    private double x_dest;
    private double y_dest;
    private double xCoord;
    private double yCoord;
    private double x_dist;
    private double y_dist;
    private ArrayList<GameComponent> componentList;
    Atlas(double width, double height, ImagePattern[] assets, ArrayList<GameComponent> gameComponents) {
        super(width, height, "atlas");
        this.height = magicConverter(90);
        this.width = magicConverter(45);
        animationFrames = assets;
        super.initBody(assets[0], width, height);
        setShootBehaviour(new ShootWithLaserBullet());
        target =  null;
        ascending = false;
        chasing = false;
        componentList = gameComponents;
    }

    public void chooseCivilian(ArrayList<GameComponent> gameComponents){
        double minDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < gameComponents.size(); i++){
            if (gameComponents.get(i) instanceof Civilian){
                Civilian civilian = (Civilian) gameComponents.get(i);
                double horizontalDist = this.getX() - civilian.getX();
                if (horizontalDist < minDist && !civilian.grabbed && !civilian.targeted) {
                    minDist = horizontalDist;
                    target = civilian;
                    civilian.targeted = true;
                }
            }
        }
    }

    public void grabCivilian(){
        target.setGrabbed(true);
        ascending = true;
    }

    public void eatCivilian(){
        target.dead = true;
        ascending = false;
        chasing = true;
    }
    public void moveAtlas(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left , int speedfactor) {
        // TODO: Why 10000? Explain with comments or make it a constant variable
        double random = Math.random() * 10000; // random for chance based updates
        if (delay) { // delay: a boolean value to delay shoots
            if (random < 150) { // %1.5 chance TODO: Constant problem for 150

                // TODO: explain or convert to a constant: 38.4 and -1
                boolean isObjectInScene = getX() <= gameRoot.getWidth() - gameRoot.getTranslateX() && getX() > gameRoot.getTranslateX() * -1;

                if (isObjectInScene) { // if the enemy is in the view of the player
                    String bulletType = "laserBullet";
                    shootBehaviour.shoot(GCF , this , gameRoot);

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
        speed_x = moveValues[2] + (speedfactor/100);
        speed_y = moveValues[3];
        if (directionX == 1)
            body.setFill(animationFrames[2]);
        else
            body.setFill(animationFrames[1]);
        // get new coordinates and speed for the next frame
        if (target == null) {
            int[] moveValues = getMoveValues(random);

            directionX = moveValues[0];
            directionY = moveValues[1];
            speed_x = moveValues[2] * speedfactor;
            speed_y = moveValues[3];
            moveX(directionX, speed_x); // move X with given inputs
            moveY(directionY, speed_y); // move Y with given inputs
            if (random <= 20)
                chooseCivilian(componentList);
        }
        else if (!ascending && !chasing){
            x_dest = target.getX() + target.width / 2;
            y_dest = target.getY() - target.height;
            x_dist = Math.abs(x_dest - xCoord);
            y_dist = Math.abs(y_dest - yCoord);
            double hipo = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));
            xCoord = this.getX() + this.width / 2;
            yCoord = this.getY();
            speed = 7;
            speed_x = speed * x_dist / hipo;
            speed_y = speed * y_dist / hipo;
            if (x_dest < xCoord)
                moveX(-1, speed_x);
            else
                moveX(1, speed_x);
            if (y_dest < yCoord)
                moveY(-1, speed_x);
            else
                moveY(1, speed_y);
            if (x_dist <= speed_x || y_dist <= speed_y){
                grabCivilian();
                speed_x = 0;
                speed_y = 5;
            }
        }
        else if (ascending && !chasing){
            moveY(-1, 5);
            if (target.grabbed && (int)getY() <= 0){
                eatCivilian();
                moveX(1, 300);
                moveY(1, 500);
            }
        }
        else if (!ascending && chasing){
            int[] moveValues = getMoveValues(random);

            directionX = moveValues[0];
            directionY = moveValues[1];
            speed_x = moveValues[2] * speedfactor;
            speed_y = moveValues[3];
            moveX(directionX, speed_x); // move X with given inputs
            moveY(directionY, speed_y); // move Y with given inputs
        }
        //updates the space ships so they loop around map
        loopAroundTheMap(GCF.width, player, left);

        // Actions when collision
        dead = updateDeath();
        if (dead) {
            dropAbility(GCF);
            explode("explode", GCF);
            if (target != null)
                target.setGrabbed(false);
        }
    }

}
