package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Arrays;

public class Player extends GameComponent{
    //Necessary attributes for teleport ability cooldown.
    private final int TELEPORT_COOLDOWN = 3;
    private boolean teleportAvailable = true;
    private int teleportCountdown = 0;
    private double teleportDistance = 0;
    private boolean bulletRainActive = false;
    private int totalBulletRainWave = 3;
    private boolean bulletRainOnGoing = false;
    private int bulletRainCount = 0;
    private final int TOTAL_BOMB_DROPS = 3;
    private boolean bombingOngoing = false;
    private boolean bombingActive = false;
    private int bombDropCount = 0;
    private final int TOTAL_ENGINE_BLASTS = 1;
    private boolean engineBlastActive = false;
    private boolean engineBlastOngoing = false;
    private int engineBlastCount = 0;
    private final int TOTAL_MELEES = 1;
    private boolean meleeActive = false;
    private boolean meleeOngoing = false;
    private int meleeCount = 0;
    private final int MAX_NO_OF_ABILITIES = 3;
    private int noOfAbilities;
    int maxAcc = 60;
    int accCount = 0;
    int dirX;
    int dirY;
    private String[] abilities;
    double acceleration;
    double innerAcc;
    double innerSpeed = 0;
    long score;
    //immortal mode //todo delete this when needed
    boolean toggleHealth = false;
    int attackDelayTimer = 0;
    boolean attackDelay = false;
    int lifeCount = 3;
    ImagePattern[] shipStatus = new ImagePattern[2]; // holds left and right

    boolean isShieldActive = false;

    Player(double givenWidth, double givenHeight, ImagePattern[] asset){
        super(givenWidth, givenHeight, "player");
        hitBoxes = new Shape[2];
        acceleration = magicConverter(0.3);
        maxSpeed = magicConverter(25);
        facingLeft = true;
        innerAcc = magicConverter(5);
        speed_x = 0;
        teleportDistance = magicConverter(110);
        //update width and height
        double tempWidth = magicConverter(110);
        double tempHeight = magicConverter(50);
        width = tempWidth;
        height = tempHeight;
        //do the calculations with width and height.
        hitBoxes[0] = new ComponentHitBoxRectangle(width,height/2.3,"player", "player"); // setup the Rectangle hit box
        hitBoxes[1] = new ComponentHitBoxCircle(width/6,"player", "player"); // setup the Circle hit box
        body = new Rectangle(width, height, null); //setup the body
        shipStatus[1] = asset[1];
        shipStatus[0] = asset[0];
        body.setFill(shipStatus[0]);
        body.setTranslateX(width*3 - givenWidth); // set X for body
        body.setTranslateY(height*7.5); // set Y for body
        hitBoxes[0].setTranslateX(width*3 - givenWidth); // set X for hit box
        hitBoxes[0].setTranslateY(height*7.5 + height/4.20); // set Y for hit box
        hitBoxes[1].setTranslateX(width*3 + width/4 - givenWidth); // set X for hit box
        hitBoxes[1].setTranslateY(height*7.5 + height/2.5); // set Y for hit box
        // initialize abilities
        noOfAbilities = 0;
        abilities = new String[MAX_NO_OF_ABILITIES];
        Arrays.fill(abilities, "empty");
    }
  
    public void movePlayer(BooleanProperty[] keyInputs, GameComponentFactory GCF){
        innerSpeed = 0;
        if(!teleportAvailable || bulletRainOnGoing || bombingOngoing || engineBlastOngoing || meleeOngoing) {
            firstTime = System.nanoTime() / 1000000000.0; // get time
            passedTime = firstTime - lastTime; // calculate passedTime
            lastTime = firstTime; // reset last time.
            totalPassedTime += passedTime; // calculate total passed time
            if (totalPassedTime > 1.0) { // if 1 second is passed
                totalPassedTime = 0; // reset timer
                if (!teleportAvailable) { // if teleport is on cooldown
                    teleportCountdown += 1; // increase teleport countdown
                    if (teleportCountdown >= TELEPORT_COOLDOWN) { // if the cooldown limit is reached
                        teleportAvailable = true; // make teleport available
                        teleportCountdown = 0; // set teleport cooldown to 0
                    }
                }
                if (bulletRainOnGoing) {
                    if (bulletRainCount >= totalBulletRainWave) {
                        bulletRainActive = false;
                        bulletRainCount = 0;
                        bulletRainOnGoing = false;
                    }
                    bulletRainCount += 1;
                    bulletRainActive = true;
                }

                if(bombingOngoing) {
                    if (bombDropCount >= TOTAL_BOMB_DROPS) {
                        bombingActive = false;
                        bombDropCount = 0;
                        bombingOngoing = false;
                    }
                    bombDropCount += 1;
                    bombingActive = true;
                }

                if(engineBlastOngoing) {
                    if (engineBlastCount >= TOTAL_ENGINE_BLASTS) {
                        engineBlastActive = false;
                        engineBlastCount = 0;
                        engineBlastOngoing = false;
                    }
                    engineBlastCount += 1;
                    engineBlastActive = true;
                }

                if (meleeOngoing) {
                    if (meleeCount >= TOTAL_MELEES) {
                        meleeActive = false;
                        meleeCount = 0;
                        meleeOngoing = false;
                    }
                    meleeCount += 1;
                    meleeActive = true;
                }
            }
        }
        if(keyInputs[3].get()) { // D is pressed
            //handle the acceleration with scenery!
            if(speed < maxSpeed)
                speed += acceleration;
            if(speed < 0)
                speed += acceleration;
            //handle inner acceleration
            if(accCount < maxAcc) {
                accCount += 1;
                innerSpeed += innerAcc;
            }
            if(accCount < 0) {
                accCount +=1;
                innerSpeed += innerAcc;
            }
//            moveX(1,speed); // move right
            if(!facingLeft) {
                body.setFill(shipStatus[0]); // make it face left in image form
                facingLeft = true; // make it face left
                hitBoxes[1].setTranslateX(body.getTranslateX() + width/4); // set X for hit box
            }
        }
        if(keyInputs[1].get()) { // a pressed
            //handle the acceleration with scenery!
            if(speed > -1*maxSpeed)
                speed -= acceleration;
            if(speed > 0)
                speed -= acceleration;
            //handle inner acceleration
            if(accCount > -1*maxAcc) {
                accCount -= 1;
                innerSpeed -= innerAcc;
            }
            if(accCount > 0) {
                accCount -=1;
                innerSpeed -= innerAcc;
            }
            // turn faces
            if(facingLeft) {
                body.setFill(shipStatus[1]); // make it face left in image form
                facingLeft = false; // make it face left
                speed_x = 1;
                hitBoxes[1].setTranslateX(body.getTranslateX() + width - width/4); // set X for hit box
            }
        }
        if(keyInputs[0].get() && getY() >= 0 + height/6) { // w pressed
            moveY(-1,10); // move up
            speed_y = -1;
        }
        if(keyInputs[2].get() && getY() <= gameRoot.getHeight() - height*1.3) { // s pressed
            moveY(1,10); // move down
            speed_y = 1;
        }
        if(keyInputs[4].get()) { // enter pressed

            //body.setTranslateX(body.getTranslateX() + 60);
            //delay = true;
        }
        if(keyInputs[5].get()) { // space pressed
            if(!attackDelay)
                shoot(GCF);
        }
        if(keyInputs[6].get()) { // Q pressed
            if(teleportAvailable) {
                teleport(keyInputs[0].get(), keyInputs[2].get());
                teleportAvailable = false;
                toggleHealth = !toggleHealth;
            } 
        }
        if(keyInputs[7].get()) { // E pressed
            if (!bombingOngoing) {
                bombingActive = true;
                bombingOngoing = true;
                bombDropCount = 0;
            }
        }
        if(keyInputs[8].get()) { // H pressed
            if (!meleeOngoing) {
                meleeOngoing = true;
                meleeActive = true;
                meleeCount = 0;
            }
        }
        if(keyInputs[9].get()) { // J pressed
            //todo add skill 1
            //  for now its shield
            useAbility(0, GCF);
        }
        if(keyInputs[10].get()) { // K pressed
            // todo some stuff
            useAbility(1, GCF);
        }
        if(keyInputs[11].get()) { // L pressed
            useAbility(2, GCF);
        }
        if(false)
            checkDeath();
        else
            lifeCount = 3;

        if(!keyInputs[1].get() && !keyInputs[3].get()){ //if keys are not pressed
            //handle the acceleration with scenery!
            if(speed > 0) {
                speed -= acceleration;
                if (speed < 0)
                    speed = 0;
            } else {
                speed += acceleration;
                if(speed > 0)
                    speed = 0;
            }
            //handle inner acceleration
            if(accCount > 0){
                innerSpeed -= innerAcc;
                accCount -= 1;
            } else if (accCount < 0){
                innerSpeed += innerAcc;
                accCount += 1;
            }
        }
        if(attackDelay) {
            if (attackDelayTimer == 0) // if timer ends
                attackDelay = false; // make delay false
            attackDelayTimer -= 25; // decrease delay
        }
        if(bulletRainOnGoing) {
            if (bulletRainActive){
                activateBulletRain(GCF);
                bulletRainActive = false;
            }
        }
        if (bombingOngoing) {
            if (bombingActive) {
                dropBomb(GCF);
                bombingActive = false;
            }
        }
        if (engineBlastOngoing) {
            if (engineBlastActive && engineBlastCount >= TOTAL_ENGINE_BLASTS) {
                activateEngineBlast(GCF);
                engineBlastActive = false;
            }
        }
        if (meleeOngoing) {
            if (meleeActive && meleeCount >= TOTAL_MELEES) {
                activateMelee(GCF);
                meleeActive = false;
            }
        }
        moveX(1,speed + innerSpeed); // move!
    }

    private void checkDeath() {
        for(int i = 0; i < hitBoxes.length; i++){
            if(hitBoxes[i] instanceof ComponentHitBoxCircle){
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle)hitBoxes[i]);
                if(temp.isDead()){
                    lifeCount -=1;
                    temp.dead = false;
                }
            } else if (hitBoxes[i] instanceof ComponentHitBoxRectangle){
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle)hitBoxes[i]);
                if(temp.isDead()){
                    lifeCount -=1;
                    temp.dead = false;
                }
            }
            if(lifeCount == 0){
                dead = true;

                break;
            }
        }
    }

    private void teleport(boolean up, boolean down){
        // currently it gives player ptsd
        if(up && !down){
            moveY(-1,teleportDistance);
        } else if (!up && down) {
            moveY(1,teleportDistance);
        } else {
            int random = (int) (Math.random() * 2);
            if (random < 1)
                moveY(1, teleportDistance);
            else
                moveY(-1, teleportDistance);
        }
    }

    private void shoot(GameComponentFactory GCF){
        String mainMenuMusicUrl = new File("Assets/Music/playerFire.mp3").toURI().toString();
        MediaPlayer mediaPlayer = new MediaPlayer( new Media(mainMenuMusicUrl));
        mediaPlayer.play();

        PlayerBullet playerBullet = (PlayerBullet) GCF.createComponent("playerBullet"); // create bullet
        playerBullet.facingLeft = facingLeft; // make it faceleft
        if(!facingLeft)
            playerBullet.setX(body.getTranslateX());
        else
            playerBullet.setX(body.getTranslateX() + width/1.1); // set X
        playerBullet.setY(body.getTranslateY() + height/2.5); // set Y
        playerBullet.addShapes(gameRoot); // add shapes of bullet to gameRoot
        attackDelay = true; // make delay true
        attackDelayTimer = 100; // start delay timer
    }

    public void activateShield(GameComponentFactory GCF, Player player){

        if (!isShieldActive) {
            Shield shield = (Shield) GCF.createComponent("shield");
            shield.setX(body.getTranslateX() + width/2);
            shield.setY(body.getTranslateY() + height/2);
            shield.addShapes(gameRoot);
            isShieldActive = true;
        }

    }

    public void activateGuidedRocket(GameComponentFactory GCF) {
        GuidedRocket gRocket = (GuidedRocket) GCF.createComponent("guidedRocket");
        gRocket.setX(body.getTranslateX() + this.width / 2);
        gRocket.setY(body.getTranslateY() + this.height / 2);
        gRocket.addShapes(gameRoot);
    }

        public void activateBarrier(GameComponentFactory GCF){
        Barrier barrier = (Barrier) GCF.createComponent("barrier");
        barrier.setX(this.body.getTranslateX() + this.width / 2);
        barrier.setY(this.body.getTranslateY() + this.height/ 2);
        barrier.addShapes(gameRoot);
    }

    private void activateBulletRain(GameComponentFactory GCF) {
        // creating bullets in north and south directions
        for (int i = 0; i < 2; i++) {
            PlayerBullet vertical = (PlayerBullet) GCF.createComponent("playerBullet");
            vertical.horizEq = true;

            if (i % 2 == 0)
                vertical.facingUp = true;
            else
                vertical.facingDown = true;

            vertical.setX(body.getTranslateX() + width/2);
            vertical.setY(body.getTranslateY() + height/2.5);
            vertical.addShapes(gameRoot);
        }
        // creating bullets in remaining 6 directions
        for (int j = 0; j < 2; j++) { // facing east or west
            for (int i = 3; i < 8; i+=2) { // vertical direction
                PlayerBullet bullet = (PlayerBullet) GCF.createComponent("playerBullet");

                double widthCoeff = j != 0 ? 1 / 1.1 : -1 / 5.0;
                double heightCoeff = j != 0 ? 1 / 2.5 : 1 / 3.5;

                bullet.facingLeft = j != 0;

                if (i % 3 == 0)
                    bullet.facingDown = true;
                else if (i % 5 == 0)
                    bullet.facingUp = true;

                bullet.setX(body.getTranslateX() + width * widthCoeff);
                bullet.setY(body.getTranslateY() + height * heightCoeff);
                bullet.addShapes(gameRoot);
            }
        }
    }

    private void dropBomb(GameComponentFactory GCF) {
        Bomb bomb = (Bomb) GCF.createComponent("bomb");
        bomb.setX(body.getTranslateX() + width/2);
        bomb.setY(body.getTranslateY() - height/2);
        bomb.addShapes(gameRoot);
    }

    private void activateEngineBlast(GameComponentFactory GCF) {
        EngineBlast blast = (EngineBlast) GCF.createComponent("engineBlast");

        if (facingLeft) {
            blast.setX(body.getTranslateX() - width/2.7);
            if (accCount - 10 > -1 * maxAcc) {
                accCount -= 10;
                innerSpeed = innerAcc * 10;
            }
        }
        else {
            blast.setX(body.getTranslateX() + width/0.9);
            if (accCount + 10 < maxAcc) {
                accCount += 10;
                innerSpeed = innerAcc * 10;
            }
        }

        blast.setY(body.getTranslateY());
        blast.addShapes(gameRoot);
    }

    private void activateMelee(GameComponentFactory GCF) {
        Melee melee = (Melee) GCF.createComponent("melee");

        // setting the initial position of the arm according to the player's position and movement
        if (facingLeft)
            melee.setX(body.getTranslateX() + width);
        else
            melee.setX(body.getTranslateX());

        melee.setY(body.getTranslateY() - height * 1.5);
        melee.addShapes(gameRoot);
    }

    private void activateHyperJump() {
        double chance = 100 * Math.random();

        /*if (chance <= 10.0)
            lifeCount--;*/

        int vertDist = (int) (Math.random() * magicConverter(100.0));
        int horizDist = (int) (Math.random() * magicConverter(1000.0));

        int vert = Math.random() > 0.5 ? 1 : -1;
        int horiz = Math.random() > 0.5 ? 1: -1;

        moveX(horiz, horizDist);
        moveY(vert, vertDist);
        gameRoot.setTranslateX(gameRoot.getTranslateX() + -1 * horiz * horizDist);
    }

    public boolean addAbility(String abilityType) {
        if (noOfAbilities < MAX_NO_OF_ABILITIES && !hasAbility(abilityType)) {
            for (int i = 0; i < MAX_NO_OF_ABILITIES; i++) {
                if ("empty".equals(abilities[i])) {
                    abilities[i] = abilityType;
                    break;
                }
            }
            noOfAbilities++;
            System.out.println("Added ability to inventory: " + abilityType);
            return true;
        }
        return false;
    }

    private boolean hasAbility(String abilityType) {
        for (String ability: abilities) {
            if (ability.equals(abilityType))
                return true;
        }
        return false;
    }

    private boolean useAbility(int index, GameComponentFactory GCF) {
        if (index < MAX_NO_OF_ABILITIES && index >= 0) {
            if (!"empty".equals(abilities[index])) {
                String abilityType = abilities[index];

                if (abilityType.equals("shield")) {
                    activateShield(GCF, this);
                } else if (abilityType.equals("hyperJump")) {
                    activateHyperJump();
                } else if (abilityType.equals("engineBlast")) {
                    if (!engineBlastOngoing) {
                        engineBlastOngoing = true;
                        engineBlastActive = true;
                        engineBlastCount = 0;
                    }
                } else if (abilityType.equals("barrier")) {
                    activateBarrier(GCF);

                } else if (abilityType.equals("bulletRain")) {
                    if(!bulletRainOnGoing) {
                        bulletRainOnGoing = true;
                        bulletRainActive = true;
                        bulletRainCount = 0;
                    }
                } else if (abilityType.equals("guidedRocket")) {
                    activateGuidedRocket(GCF);
                } else if (abilityType.equals("melee")) {
                    if (!meleeOngoing) {
                        meleeOngoing = true;
                        meleeActive = true;
                        meleeCount = 0;
                    }
                }
                abilities[index] = "empty";
                noOfAbilities--;
                return true;
            }
        }
        return false;
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public long getScore() {return score;}
    public void setScore(long score) {this.score = score;}
    public boolean isDead(){ return dead;}
}
