package org.openjfx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.File;

public class Enemy extends GameComponent{

    double directionCheckX; // X direction decider for this type of enemy
    double directionCheckY; // Y direction decider for this type of enemy
    int directionX = 1; // if this is 1 it goes right else left
    int directionY = 1; // if this is 1 it goes down else up


    /**
     * Constructor
     * @param width width of the enemy object
     * @param height height of the enemy object
     * @param type type of the enemy object
     */
    Enemy(double width, double height, String type){
        super(width, height, type);
    }


    /**
     * Initializes a body, its hitboxes for the enemy object.
     * @param asset The image of enemy
     * @param width Width of the screen, helps choosing starting location
     * @param height Height of the screen, helps choosing starting location
     */
    public void initBody(ImagePattern asset, double width, double height){
        // starting X of the enemy, randomly selected
        double startingX = (Math.random() * (width  * 4)) + (width * -2) ;

        // starting Y of the enemy, randomly selected
        double startingY = this.height*2 + ((Math.random() * height) - this.height*4);

        // TODO: there is only 1 hit box --- for now. Change it if necessary
        hitBoxes = new Shape[1];

        // setup the Rectangle hit box
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "enemyHitBox");
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
        if (getY() >= gameRoot.getHeight() - height * 2 || getY() < 0) { // if the height boundaries reached
            directionY *= -1; // change direction
        }

        return new int[]{directionX, directionY, (int)speed_x, (int)speed_y};
    }


    /**  
     * initialize bullets for the enemy
     * @param GCF TODO: explain what is this and what is its purpose
     * @param bulletType bullet type to create. Ex: enemyBulletType1 or enemyBulletType2
     */
    public void initBullet(GameComponentFactory GCF, String bulletType) {
        if(bulletType.equals("laserbullet")) {
            LaserBullet enemyBullet = (LaserBullet) GCF.createComponent(bulletType); // create the bullet
            enemyBullet.facingLeft = facingLeft; // make it face left
            enemyBullet.setX(body.getTranslateX()); // set its X
            enemyBullet.setY(body.getTranslateY()); // set its Y
            enemyBullet.addShapes(gameRoot); // add its shapes to Root
        } else if(bulletType.equals("guidedbullet")) {
            GuidedBullet enemyBullet = (GuidedBullet) GCF.createComponent(bulletType); // create the bullet
            enemyBullet.facingLeft = facingLeft; // make it face left
            enemyBullet.setX(body.getTranslateX()); // set its X
            enemyBullet.setY(body.getTranslateY()); // set its Y
            enemyBullet.addShapes(gameRoot); // add its shapes to Root
        }
    }


    /**
     * Transports the objects at one end of the map to the another map to simulate infinite map
     * Briefly, objects loop around the map when they reach an end of the map
     * @param bgWidth: TODO: explain // Width of the screen.
     * @param player: TODO: explain
     * @param left: TODO: explain
     */
    public void loopAroundTheMap(double bgWidth,Player player,boolean left) {
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

    void explode(String explodeType, GameComponentFactory GCF) {
        EnemySelfDestruct selfDest = (EnemySelfDestruct) GCF.createComponent(explodeType);
        selfDest.setX(this.getX() + width / 2);
        selfDest.setY(this.getY() + height / 2);
        selfDest.addShapes(gameRoot);
        if(explodeType.equals("explode")){
            String mainMenuMusicUrl = new File("Assets/Music/explodeNormal.mp3").toURI().toString();
            MediaPlayer mediaPlayer = new MediaPlayer( new Media(mainMenuMusicUrl));
            mediaPlayer.play();
        } else {
            String mainMenuMusicUrl = new File("Assets/Music/explodeSelfDestruct.mp3").toURI().toString();
            MediaPlayer mediaPlayer = new MediaPlayer( new Media(mainMenuMusicUrl));
            mediaPlayer.play();
        }
    }
}
