package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Player extends GameComponent{
    //Necessary attiributes for teleport ability cooldown.
    private final int TELEPORT_COOLDOWN = 3;
    private boolean teleportAvailable = true;
    private int teleportCountdown = 0;
    private double teleportDistance = 0;
    private boolean bulletRainActive = false;
    private int totalBulletRainWave = 3;
    private final int TOTAL_BOMB_DROPS = 4;
    private boolean bombingOngoing = false;
    private boolean bombingActive = false;
    private int bombDropCount = 0;
    private int bulletRainCount = 0;
    int maxAcc = 60;
    int accCount = 0;
    double acceleration;
    double innerAcc;
    double innerSpeed = 0;
    //immortal mode //todo delete this when needed
    boolean toggleHealth = false;
    int attackDelayTimer = 0;
    boolean attackDelay = false;
    int lifeCount = 3;
    private boolean bulletRainOnGoing = false;
    ImagePattern[] shipStatus = new ImagePattern[2]; // holds left and right

    boolean isShieldActive = false;

    Player(double givenWidth, double givenHeight, ImagePattern[] asset){
        super(givenWidth, givenHeight, "player");
        hitBoxes = new Shape[2];
        acceleration = magicConverter(0.3);
        maxSpeed = magicConverter(25);
        facingLeft = true;
        innerAcc = 3;
        teleportDistance = magicConverter(150);
        //update width and height
        double tempWidth = magicConverter(150);
        double tempHeight = magicConverter(70);
        width = tempWidth;
        height = tempHeight;
        //do the calculations with width and height.
        hitBoxes[0] = new ComponentHitBoxRectangle(width,height/2.3,"playerHitBoxRectangle"); // setup the Rectangle hit box
        hitBoxes[1] = new ComponentHitBoxCircle(width/6,"playerHitBoxCircle"); // setup the Circle hit box
        body = new Rectangle(width, height, null); //setup the body
        shipStatus[1] = asset[1];
        shipStatus[0] = asset[0];
        body.setFill(shipStatus[0]);
        body.setTranslateX(width*1.5 - width*12.8); // set X for body
        body.setTranslateY(height*7.5); // set Y for body
        hitBoxes[0].setTranslateX(width*1.5 - width*12.8); // set X for hit box
        hitBoxes[0].setTranslateY(height*7.5 + height/4.20); // set Y for hit box
        hitBoxes[1].setTranslateX(width*1.5 + width/4 - width*12.8); // set X for hit box
        hitBoxes[1].setTranslateY(height*7.5 + height/2.5); // set Y for hit box
    }
  
    public void movePlayer(BooleanProperty[] keyInputs, GameComponentFactory GCF){
        innerSpeed = 0;
        if(!teleportAvailable || bulletRainOnGoing || bombingOngoing) {
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
                hitBoxes[1].setTranslateX(body.getTranslateX() + width - width/4); // set X for hit box
            }
        }
        if(keyInputs[0].get() && getY() >= 0 + height/6) { // w pressed
            moveY(-1,10); // move up
        }
        if(keyInputs[2].get() && getY() <= gameRoot.getHeight() - height*1.3) { // s pressed
            moveY(1,10); // move down
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
            if(!bulletRainOnGoing) {
                bulletRainOnGoing = true;
                bulletRainActive = true;
                bulletRainCount = 0;
            }
        }
        if(keyInputs[8].get()) { // H pressed
            activateHyperJump();
        }
        if(keyInputs[9].get()) { // J pressed
            //todo add skill 1
            //  for now its shield
            activateShield(GCF, this);
        }
        if(keyInputs[10].get()) { // K pressed
            // todo some stuff
        }
        if(keyInputs[11].get()) { // L pressed
            if (!bombingOngoing) {
                bombingActive = true;
                bombingOngoing = true;
                bombDropCount = 0;
            }
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
            Shield shield = (Shield) GCF.createComponent("Shield");
            shield.setX(body.getTranslateX() + width/2);
            shield.setY(body.getTranslateY() + height/2.5);
            shield.addShapes(gameRoot);
            isShieldActive = true;
        }

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
        bomb.setX(body.getTranslateX());
        bomb.setY(body.getTranslateY());
        bomb.addShapes(gameRoot);
    }

    private void activateHyperJump() {
        double chance = 100 * Math.random();

        /*if (chance <= 10.0)
            lifeCount--;*/

        int vertDist = (int) (Math.random() * magicConverter(100.0));
        int horizDist = (int) (Math.random() * magicConverter(1000.0));

        int vert = Math.random() > 0.5 ? 1 : -1;
        int horiz = Math.random() > 0.5 ? 1: -1;

        //facingLeft = horiz == 1;

        moveX(horiz, horizDist);
        moveY(vert, vertDist);
        gameRoot.setTranslateX(gameRoot.getTranslateX() + -1 * horiz * horizDist);
    }

    public double getWidth() { return width; }
}
