package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Player extends GameComponent{


    int attackDelayTimer = 0;
    boolean attackDelay = false;
    int lifeCount = 3;
    ImagePattern[] shipStatus = new ImagePattern[2];
    Player(double width, double height, String assetLocation){
        super(width, height, "player");
        hitBoxes = new Shape[2];
        hitBoxes[0] = new ComponentHitBoxRectangle(width,height/2.3,"playerHitBoxRectangle"); // setup the Rectangle hit box
        hitBoxes[1] = new ComponentHitBoxCircle(width/6,"playerHitBoxCircle"); // setup the Circle hit box
        body = new Rectangle(width, height, null); //setup the body
        shipStatus[1] = fillImage(assetLocation + "_left.png"); // insert facing left image to body
        shipStatus[0] = fillImage(assetLocation + "_right.png"); // insert facing right image to body
        speed = 25; // set initial speed
        body.setTranslateX(width*1.5 - width*12.8); // set X for body
        body.setTranslateY(height*7.5); // set Y for body
        facingLeft = true;
        hitBoxes[0].setTranslateX(width*1.5 - width*12.8); // set X for hit box
        hitBoxes[0].setTranslateY(height*7.5 + height/4.20); // set Y for hit box
        hitBoxes[1].setTranslateX(width*1.5 + width/4 - width*12.8); // set X for hit box
        hitBoxes[1].setTranslateY(height*7.5 + height/2.5); // set Y for hit box
    }
    public void update(BooleanProperty[] keyInputs, GameComponentFactory GCF, Pane gameRoot){
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
        if(keyInputs[6].get()) { // enter pressed
            teleport();
        }
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

    private void teleport(){
        // gives player ptsd
        int rndm = (int)(Math.random() * 2);
        if(rndm < 1)
            moveY(1, 150);
        else
            moveY(-1,150);
    }

    private void shoot(GameComponentFactory GCF){
        if(!attackDelay) {
            PlayerBullet playerBullet = (PlayerBullet) GCF.createComponent("playerBullet"); // create bullet
            playerBullet.toLeft = facingLeft; // make it faceleft
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
    public double getWidth() { return width; }
}
