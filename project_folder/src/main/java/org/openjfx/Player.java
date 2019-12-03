package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Player extends GameComponent{
    //Necessary attiributes for teleport ability cooldown.
    private final int TELEPORT_COOLDOWN = 3;
    private boolean teleportAvailable = true;
    private int teleportCountdown = 0;
    //immortal mode //todo delete this when needed
    boolean toggleHealth = false;

    int attackDelayTimer = 0;
    boolean attackDelay = false;
    int lifeCount = 3;
    ImagePattern[] shipStatus = new ImagePattern[2]; // holds left and right

    Player(double width, double height, String assetLocation){
        super(width, height, "player");
        hitBoxes = new Shape[2];
        hitBoxes[0] = new ComponentHitBoxRectangle(width,height/2.3,"playerHitBoxRectangle"); // setup the Rectangle hit box
        hitBoxes[1] = new ComponentHitBoxCircle(width/6,"playerHitBoxCircle"); // setup the Circle hit box
        body = new Rectangle(width, height, null); //setup the body
        shipStatus[1] = fillImage(assetLocation + "_left.png"); // insert facing left image to body
        shipStatus[0] = fillImage(assetLocation + "_right.png"); // insert facing right image to body
        speed = 5; // set initial speed
        acceleration = 0.4;
        maxSpeed = 25;
        body.setTranslateX(width*1.5 - width*12.8); // set X for body
        body.setTranslateY(height*7.5); // set Y for body
        facingLeft = true;
        hitBoxes[0].setTranslateX(width*1.5 - width*12.8); // set X for hit box
        hitBoxes[0].setTranslateY(height*7.5 + height/4.20); // set Y for hit box
        hitBoxes[1].setTranslateX(width*1.5 + width/4 - width*12.8); // set X for hit box
        hitBoxes[1].setTranslateY(height*7.5 + height/2.5); // set Y for hit box
    }
    public void movePlayer(BooleanProperty[] keyInputs, GameComponentFactory GCF){

        firstTime = System.nanoTime() / 1000000000.0; // get time
        passedTime = firstTime - lastTime; // calculate passedTime
        lastTime = firstTime; // reset last time.
        totalPassedTime += passedTime; // calculate total passed time
        if(totalPassedTime > 1.0) { // if 1 second is passed
            totalPassedTime = 0; // reset timer
            if(!teleportAvailable){ // if teleport is on cooldown
                teleportCountdown += 1; // increase teleport countdown
                if(teleportCountdown >= TELEPORT_COOLDOWN) { // if the cooldown limit is reached
                    teleportAvailable = true; // make teleport available
                    teleportCountdown = 0; // set teleport cooldown to 0
                }
            }
        }
        if(keyInputs[3].get()) { // D is pressed
            moveX(1,speed); // move right
            if(!facingLeft) {
                body.setFill(shipStatus[0]); // make it face left in image form
                facingLeft = true; // make it face left
                hitBoxes[1].setTranslateX(body.getTranslateX() + width/4); // set X for hit box
            }
        }
        if(keyInputs[1].get()) { // a pressed
            moveX(-1,speed); // move left
            if(facingLeft) {
                body.setFill(shipStatus[1]); // make it face left in image form
                facingLeft = false; // make it face left
                hitBoxes[1].setTranslateX(body.getTranslateX() + width - width/4); // set X for hit box
            }
        }
        if(keyInputs[0].get() && getY() >= 0 + height/6) { // w pressed
            moveY(-1,speed/2); // move up
        }
        if(keyInputs[2].get() && getY() <= gameRoot.getHeight() - height*1.3) { // s pressed
            moveY(1,speed/2); // move up
        }
        if(keyInputs[4].get()) { // enter pressed
            //body.setTranslateX(body.getTranslateX() + 60);
            //delay = true;
        }
        if(keyInputs[5].get()) { // space pressed
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
            //todo add hyperjump here
        }
        if(keyInputs[8].get()) { // H pressed
            //todo add bomb here
        }
        if(keyInputs[9].get()) { // J pressed
            //todo add skill 1
            //  for now its shield
            activateShield(GCF);
        }
        if(keyInputs[10].get()) { // K pressed
            //todo add skill 2
        }
        if(keyInputs[11].get()) { // L pressed
            //todo add skill 3
        }
        if(!toggleHealth)
            checkDeath();
        else
            lifeCount = 3;
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
            moveY(-1,150);
        } else if (!up && down) {
            moveY(1,150);
        } else {
            int random = (int) (Math.random() * 2);
            if (random < 1)
                moveY(1, 150);
            else
                moveY(-1, 150);
        }
    }

    private void shoot(GameComponentFactory GCF){
        if(!attackDelay) {
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
        } else {
            if(attackDelayTimer == 0) // if timer ends
                attackDelay = false; // make delay false
            attackDelayTimer -= 25; // decrease delay
        }
    }
    public void activateShield(GameComponentFactory GCF){
    }
    public double getWidth() { return width; }
}
