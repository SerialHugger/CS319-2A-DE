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
    private int angerNumber;
    private ArrayList<GameComponent> componentList;

    /**
     * constructor for enemy type atlas
     * @param width is the width of Atlas
     * @param height is the height of atlas
     * @param assets is the image assets of atlas
     * @param gameComponents inndicates which number in the gamecomponent arraylist
     */
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
        angerNumber = -1;
    }

    /**
     * atlass catches a civilian and rises to abduct it
     * @param gameComponents gets the civilian from arraylist
     */
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

    /**
     * function for grabbing civilian
     */
    public void grabCivilian(){
        target.setGrabbed(true);
        ascending = true;
    }

    /**
     * function for situtations  when atlas reaches top with civilian
     */
    public void eatCivilian(){
        target.dead = true;
        ascending = false;
        chasing = true;
        angerNumber = 0;
    }

    /**
     * typical move function of enemy atlas includes move and shoot
     * @param GCF gets the Gamecomponent
     * @param gameRoot gets the game pane
     * @param player needs the player for moving to player
     * @param left boolean for moving left
     * @param speedfactor an integer which multiplies the speed of the enemy
     */
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
                moveX(1, 30);
                moveY(1, 50);
            }
            if (target.isSaved() && getY() > 0 ){
                target = null;
                ascending = false;
                chasing = false;
            }
        }
        else if (!ascending && chasing){
            System.out.println("chasing...");
            if (angerNumber == 0) {
                xCoord = this.getX() + width / 2;
                yCoord = this.getY() + height / 2;
                x_dist = Math.abs(player.getX() + player.getWidth() / 2 - xCoord);
                y_dist = Math.abs(player.getY() + player.getHeight() / 2 - yCoord);
                double hipo = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));
                speed = 20;
                speed_x = speed * x_dist / hipo;
                speed_y = speed * y_dist / hipo;
                if (player.getX() > xCoord) {
                    moveX(1, speed_x);
                    angerNumber = 1;
                } else {
                    moveX(-1, speed_x);
                    angerNumber = 2;
                }
                if (player.getY() > yCoord) {
                    moveY(1, speed_y);
                    angerNumber = 3;
                } else {
                    moveY(-1, speed_y);
                    angerNumber = 4;
                }
            }
            else if (angerNumber == 1){
                moveX(-1, speed_x / 1.2);
                angerNumber = 0;
            }
            else if (angerNumber == 2){
                moveX(1, speed_x / 1.2);
                angerNumber = 0;
            }
            else if (angerNumber == 3){
                moveY(-1, speed_y / 1.5);
                angerNumber = 0;
            }
            else if (angerNumber == 4){
                moveY(1, speed_y / 1.5);
                angerNumber = 0;
            }

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
