package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Player extends GameComponent{


    int attackDelayTimer = 0;
    boolean attackDelay = false;
    int lifeCount = 3;
    Player(double width, double height, String assetLocation){
        super(width, height, "player");
        hitBoxes = new Shape[2];
        hitBoxes[0] = new ComponentHitBoxRectangle(width,height/2.3,"playerHitBoxRectangle"); // setup the Rectangle hit box
        hitBoxes[1] = new ComponentHitBoxCircle(width/6,"playerHitBoxCircle"); // setup the Circle hit box
        body = new Rectangle(width, height, null); //setup the body
        fillImage(assetLocation); // insert image to body
        speed = 25; // set initial speed
        body.setTranslateX(width*1.5 - width*12.8); // set X for body
        body.setTranslateY(height*7.5); // set Y for body
        hitBoxes[0].setTranslateX(width*1.5 - width*12.8); // set X for hit box
        hitBoxes[0].setTranslateY(height*7.5 + height/4.20); // set Y for hit box
        hitBoxes[1].setTranslateX(width*1.5 + width/4 - width*12.8); // set X for hit box
        hitBoxes[1].setTranslateY(height*7.5 + height/2.5); // set Y for hit box
    }
    public void update(BooleanProperty[] keyInputs, GameComponentFactory GCF, Pane gameRoot){
        if(keyInputs[3].get()) { // D is pressed
            moveX(1,speed); // move right
            facingLeft = true; // make it face left
        }
        if(keyInputs[1].get()) { // a pressed
            moveX(-1,speed); // move left
            facingLeft = false; // make it face right
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
            if(!attackDelay) {
                PlayerBullet playerBullet = (PlayerBullet) GCF.createComponent("playerBullet"); // create bullet
                playerBullet.toLeft = facingLeft; // make it faceleft
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

    public double getWidth() { return width; }
}
