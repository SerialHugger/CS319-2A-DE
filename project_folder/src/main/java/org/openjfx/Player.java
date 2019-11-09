package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Player extends GameComponent{


    int attackDelayTimer = 0;
    boolean attackDelay = false;
    Player(double width, double height, String url){
        super(width, height, "player");
        hitBoxes = new Shape[2];
        hitBoxes[0] = new ComponentHitBoxRectangle(width,height/2.3,"playerHitBoxRectangle"); // setup the Rectangle hitbox
        hitBoxes[1] = new ComponentHitBoxCircle(width/6,"playerHitBoxCircle"); // setup the Circle hitbox
        body = new Rectangle(width, height, null); //setup the body
        fillImage(url);
        speed = 25;
        body.setTranslateX(width*1.5);
        body.setTranslateY(height*7.5);
        hitBoxes[0].setTranslateX(width*1.5);
        hitBoxes[0].setTranslateY(height*7.5 + height/4.20);
        hitBoxes[1].setTranslateX(width*1.5 + width/4);
        hitBoxes[1].setTranslateY(height*7.5 + height/2.5);
    }
    public void update(BooleanProperty[] keyInputs, GameComponentFactory GCF, Pane gameRoot){
        if(keyInputs[3].get()) { // D is pressed
            updateHitBoxes(true, true); // update the hitboxes
            facingLeft = true; // make it face left
            body.setTranslateX(body.getTranslateX() + speed); // Move
        }
        if(keyInputs[1].get()) {
            updateHitBoxes(true, false);
            facingLeft = false;
            body.setTranslateX(body.getTranslateX() - speed);
        }
        if(keyInputs[0].get()) {
            updateHitBoxes(false, false);
            body.setTranslateY(body.getTranslateY() - speed);
        }
        if(keyInputs[2].get()) {
            updateHitBoxes(false, true);
            body.setTranslateY(body.getTranslateY() + speed);
        }
        if(keyInputs[4].get()) {
            //body.setTranslateX(body.getTranslateX() + 60);
            //delay = true;
        }
        if(keyInputs[5].get()) {
            if(!attackDelay) {
                PlayerBullet playerBullet = (PlayerBullet) GCF.createComponent("playerBullet");
                playerBullet.toLeft = facingLeft;
                playerBullet.setX(body.getTranslateX());
                playerBullet.setY(body.getTranslateY());
                playerBullet.addShapes(gameRoot);
                attackDelay = true;
                attackDelayTimer = 100;
            } else {
                if(attackDelayTimer == 0)
                    attackDelay = false;
                attackDelayTimer -= 25;
            }
        }

//        if(delay) {
//            delayTimer += 15;
//            if (delayTimer % 15 == 0){
//                body.setTranslateX(body.getTranslateX() + speed);
//                if(delayTimer > 2000 && speed > 300)
//                    speed -= 30;
//                else if(delayTimer > 2200 && speed > 100)
//                    speed -= 10;
//                else if (speed < 200)
//                    speed += 5;
//                else if(speed < 700)
//                    speed += 60;
//            }
//            if(delayTimer == 3000) {
//                delay = false;
//                speed = 60;
//                delayTimer = 0;
//            }
//        }
    }
    private void updateHitBoxes(boolean x, boolean positive){
        int movement = 25;
        for(int i = 0; i < hitBoxes.length; i ++) {
            if (!positive) {
                movement = -25;
            } else {
                movement = 25;
            }
            if (x) {
                    hitBoxes[i].setTranslateX(hitBoxes[i].getTranslateX() + movement);
            } else {
                    hitBoxes[i].setTranslateY(hitBoxes[i].getTranslateY() + movement);
            }
        }
    }
    public double getX(){
        return body.getTranslateX();
    }
    public double getY(){ return body.getTranslateY(); }
    public double getWidth() { return width; }
}
