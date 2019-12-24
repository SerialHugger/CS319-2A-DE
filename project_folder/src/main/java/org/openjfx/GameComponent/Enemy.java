package org.openjfx.GameComponent;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.File;

public class Enemy extends GameComponent {

    private boolean canDropItem;
    double directionCheckX; // X direction decider for this type of enemy
    double directionCheckY; // Y direction decider for this type of enemy
    int directionX = 1; // if this is 1 it goes right else left
    int directionY = 1; // if this is 1 it goes down else up
    Shootable shootBehaviour;

    /**
     * Constructor
     *
     * @param width  width of the enemy object
     * @param height height of the enemy object
     * @param type   type of the enemy object
     */
    Enemy(double width, double height, String type) {
        super(width, height, type);
        canDropItem = ((10000 * Math.random()) < 2000) ? true : false;
    }


    /**
     * Initializes a body, its hitboxes for the enemy object.
     *
     * @param asset  The image of enemy
     * @param width  Width of the screen, helps choosing starting location
     * @param height Height of the screen, helps choosing starting location
     */
    public void initBody(ImagePattern asset, double width, double height) {
        // starting X of the enemy, randomly selected
        double startingX = (Math.random() * (width * 4)) + (width * -2);

        // starting Y of the enemy, randomly selected
        double startingY = height / 2;

        // TODO: there is only 1 hit box --- for now. Change it if necessary
        hitBoxes = new Shape[1];

        // setup the Rectangle hit box
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "enemy", type);
        //setup the body
        body = new Rectangle(this.width, this.height, null);

        // fill the body with image at assetLocation
        body.setFill(asset);

        // set X and Y
        body.setTranslateX(startingX);
        body.setTranslateY(startingY);

        // set X and Y for hit box
        hitBoxes[0].setTranslateX(startingX);
        hitBoxes[0].setTranslateY(startingY);

        // TODO: 7 must be constant parameter
        // set initial speed of x and y with random values
        speed_x = Math.random() * 7;
        speed_y = Math.random() * 7;
    }


    /**
     * TODO: enter description
     *
     * @param random TODO: enter description
     * @return new directionX directionY values with speed_x and speed_y values for the next frame
     */
    public int[] getMoveValues(double random) {
        // TODO: 30 value should be constant
        if (random < 30) { // delay for changing directions and speed, %0.3 chance
            speed_x = Math.random() * magicConverter(7) + magicConverter(1); // set speed x, randomly TODO: constant
            speed_y = Math.random() * magicConverter(7) + magicConverter(1); // set speed y, randomly TODO: constant
            directionCheckX = Math.random() * 2;
            directionCheckY = Math.random() * 2;
            if (directionCheckX < 1) // %50 chance
                directionX = -1; // it goes left
            if (directionCheckY < 1) // %50 chance
                directionY = -1; // it goes up
        }
        if (getY() >= gameRoot.getHeight() - height || getY() < 0) { // if the height boundaries reached
            directionY *= -1; // change direction
        }

        return new int[]{directionX, directionY, (int) speed_x, (int) speed_y};
    }





    /**
     * Transports the objects at one end of the map to the another map to simulate infinite map
     * Briefly, objects loop around the map when they reach an end of the map
     *
     * @param bgWidth: the width needed for looping the map
     * @param player:  player that user controls
     * @param left:    if player is going left
     */
    public void loopAroundTheMap(double bgWidth, Player player, boolean left) {
        //updates the space ships so they loop around map
        if (left) { // if button to go left, "A", is pressed
            if (body.getTranslateX() > player.getX() + (2 * bgWidth)) { // if he enemy ship is width * 2 away from the ship teleport it to other side
                for (Shape hitBox : hitBoxes) {
                    hitBox.setTranslateX(hitBox.getTranslateX() - (4 * bgWidth));
                }
                body.setTranslateX(body.getTranslateX() - (4 * bgWidth));
            }
        } else { // if button to go right, "D", is pressed
            if (body.getTranslateX() < player.getX() - (2 * bgWidth)) { // if he enemy ship is width * 2 away from the ship teleport it to other side
                for (Shape hitBox : hitBoxes) {
                    hitBox.setTranslateX(hitBox.getTranslateX() + (4 * bgWidth));
                }
                body.setTranslateX(body.getTranslateX() + (4 * bgWidth));
            }
        }
    }

    /**
     * function for exploding enemies when they die
     * @param explodeType the type of explosion
     * @param GCF the gamecomponentfactory
     */
    public void explode(String explodeType, GameComponentFactory GCF) {
        EnemySelfDestruct selfDest = (EnemySelfDestruct) GCF.createComponent(explodeType);
        selfDest.setX(this.getX() + width / 2);
        selfDest.setY(this.getY() + height / 2);
        selfDest.addShapes(gameRoot);
        if (explodeType.equals("explode")) {
            String mainMenuMusicUrl = new File("Assets/Music/explodeNormal.mp3").toURI().toString();
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(mainMenuMusicUrl));
            mediaPlayer.play();
        } else {
            String mainMenuMusicUrl = new File("Assets/Music/explodeSelfDestruct.mp3").toURI().toString();
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(mainMenuMusicUrl));
            mediaPlayer.play();
        }
    }

    /**
     * function for dropping abilities when enemies die
     * @param GCF gamecomponentfactory for creating abilities in game
     */
    public void dropAbility(GameComponentFactory GCF) {
        if (canDropItem) {
            Collectible item = (Collectible) GCF.createComponent("collectible");
            item.setX(this.body.getTranslateX());
            item.setY(this.body.getTranslateY());
            item.addShapes(gameRoot);
        }
    }

    /**
     * setting the shootbehaviour of the enemy of the strategy pattern
     * @param shootBehaviour shootbehaviour ( laser , guided etc )
     */
    public void setShootBehaviour(Shootable shootBehaviour) {
        this.shootBehaviour = shootBehaviour;
    }

    /**
     * for performin the shooting
     * @param GCF gets gamecomponentfactory to initiliaze bullets
     */
    public void performShoot(GameComponentFactory GCF) {
        shootBehaviour.shoot(GCF, this, gameRoot);
    }

    /**
     * getter for width
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * getter for height
     * @return height
     */
    public double getHeight() {
        return height;
    }
}
