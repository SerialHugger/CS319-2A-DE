package org.openjfx.GameComponent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

//enemyType1

public class Boss extends Enemy {
    int totalHealth = 100;
    int widthMultiplier = 1;
    int shootFaster = 1;
    int loweringDelay = 0;
    boolean flagForDiwingWind90 = true;
    boolean flagForDiwingWind80 = true;
    boolean flagForDiwingWind70 = true;
    boolean flagForDiwingWind60 = true;
    boolean flagForDiwingWind50 = true;

    /**
     * constructor for enemy type boss
     * @param width  width of boss
     * @param height height of boss
     * @param assets for animation of boss
     */
    Boss(double width, double height, ImagePattern assets) {
        super(width, height, "boss");
        this.height = magicConverter(300);
        this.width = magicConverter(90 * widthMultiplier);
        //animationFrames = assets;
        super.initBody(assets, width, height);
        setShootBehaviour(new ShootWithLaserBullet());
    }

    /**
     * function for moving the enemy type boss, and shooting
     * @param GCF needs gamecomonentfactory for spawning enemies
     * @param gameRoot parameter for root of the game
     * @param player enemies need the player
     * @param left checking if it moves left
     * @param speedFactor an integer which multiplies the speed of the enemy
     */
    public void moveBoss(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left, int speedFactor) {

        // TODO: Why 10000? Explain with comments or make it a constant variable
        double random = Math.random() * 10000; // random for chance based updates
        spawnDivingWind(GCF);
        if (delay) { // delay: a boolean value to delay shoots
            if( random < (150 * shootFaster) ) { // %1.5 chance TODO: Constant problem for 150

                // TODO: explain or convert to a constant: 38.4 and -1
                boolean isObjectInScene = getX() <= gameRoot.getWidth() - gameRoot.getTranslateX() && getX() > gameRoot.getTranslateX() * -1;

                if (isObjectInScene) { // if the enemy is in the view of the player
                    shootBehaviour.shoot(GCF, this, gameRoot);
                    delay = false; // make delay false
                    delayTimer = 500 - loweringDelay ; // start delay timer TODO: Constant problem for 500
                }
            }
        }
        else { // if delay is on
            if (delayTimer <= 0) // if delay timer finished
                delay = true; // make delay true
            delayTimer -= 5; // decrease delay timer TODO: Why? Could you explain
        }
        if ( totalHealth == 90 || totalHealth == 80 || totalHealth == 70 || totalHealth == 60 ){
            shootFaster = shootFaster + 1000;
            loweringDelay = loweringDelay + 100;
        }
        if( totalHealth == 50 ){
            loweringDelay = 500;
            shootFaster = 10000;
        }
        System.out.println("totalHealth is " + totalHealth);
        System.out.println( " shootfaster " + shootFaster);
        System.out.println( " loweringdelay " + loweringDelay);
        // get new coordinates and speed for the next frame
        int[] moveValues = getMoveValues(random);

        directionX = moveValues[0];
        directionY = moveValues[1];
        speed_x = moveValues[2] + (speedFactor/100);
        speed_y = moveValues[3];

        moveX(directionX, speed_x); // move X with given inputs
        moveY(directionY, speed_y); // move Y with given inputs

        //updates the space ships so they loop around map
        loopAroundTheMap(GCF.width, player, left);

        // Actions when collision
        for (Shape hitBox : hitBoxes) {
            if (hitBox instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBox);
                if ( temp.isDead()){
                    temp.dead = false;
                    totalHealth--;
                    if(totalHealth == 0)
                        dead = true;
                }
            } else if (hitBox instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBox);
                if ( temp.isDead()){
                    temp.dead = false;
                    totalHealth--;
                    if(totalHealth == 0)
                        dead = true;
                }
            }
        }
        if (dead) {
            createDivingWinds(GCF, 10);
            dropAbility(GCF);
            explode("explode", GCF);
        }
    }

    /**
     * function for boss to create divingwinds
     * @param GCF gamecomponentfactory for creating divingwinds
     * @param numberOfEnemy indicates how many will be produced
     */
    public void createDivingWinds(GameComponentFactory GCF, int numberOfEnemy) {
            for( int i = 0; i < numberOfEnemy; i++ ){
            DivingWind temp1 = (DivingWind) GCF.createComponent("divingWind");
            temp1.addShapes(gameRoot);
            temp1.setX(body.getTranslateX());
            temp1.setY(body.getTranslateY());
        }
    }

    /**
     * special function for spawning divingwinds when boss's health reaches specific
     * healths
     * @param GCF needs gamecomponentfactory to create divingwind
     */
    public void spawnDivingWind(GameComponentFactory GCF ){
        if( totalHealth == 90 && flagForDiwingWind90 ){
            createDivingWinds(GCF , 2 );
            flagForDiwingWind90 = false;
        }
        else if( totalHealth == 80 && flagForDiwingWind80 ){
            createDivingWinds(GCF , 2 );
            flagForDiwingWind80 = false;
        }
        else if( totalHealth == 70 && flagForDiwingWind70 ){
            createDivingWinds(GCF , 3 );
            flagForDiwingWind70 = false;
        }
        else if( totalHealth == 60 && flagForDiwingWind60 ){
            createDivingWinds(GCF , 4 );
            flagForDiwingWind60 = false;
        }
        else if( totalHealth == 50 && flagForDiwingWind50 ){
            createDivingWinds(GCF , 8 );
            flagForDiwingWind50 = false;
        }

    }
}
