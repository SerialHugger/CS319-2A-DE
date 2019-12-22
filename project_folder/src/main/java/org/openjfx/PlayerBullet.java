package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class PlayerBullet extends PlayerEquipment {
    boolean facingDown = false;
    boolean facingUp = false;
    boolean horizEq = false;
    PlayerBullet(double width, double height, ImagePattern[] assets, boolean toLeft, double speed){
        super(width,height,"playerBullet");
        this.speed = Math.abs(speed) + magicConverter(25);
        this.height = magicConverter(20);
        this.width = magicConverter(60);
        this.facingLeft = toLeft;
        animationFrames = assets;
        hitBoxes = new Rectangle[1];
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "playerEquipment", "playerBullet");
        body = new Rectangle(this.width,this.height, animationFrames[0]);
    }

    public void movePlayerBullet() {
        counter += 1;
        counter = counter % 2;
        body.setFill(animationFrames[counter]);
        if (horizEq) {
            if (facingDown) {
                moveY(1, speed);
                rotate(90);
            } else {
                moveY(-1, speed);
                rotate(-90);
            }

        } else {
            if(facingLeft){ // If player was looking at left go left
                moveX(1, speed);
                if (facingDown) {
                    moveY(1, speed);
                    rotate(45);
                }
                else if (facingUp) {
                    moveY(-1, speed);
                    rotate(-45);
                }

            } else { // else go right
                moveX(-1, speed);
                rotate(180);
                if (facingDown) {
                    moveY(1, speed);
                    rotate(135);
                }
                else if (facingUp) {
                    moveY(-1, speed);
                    rotate(-135);
                }
            }
        }

        for(int i = 0; i < hitBoxes.length; i++){
            if(hitBoxes[i] instanceof ComponentHitBoxCircle){
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle)hitBoxes[i]);
                if(temp.isDead()) {
                    dead = true;
                }
            } else if (hitBoxes[i] instanceof ComponentHitBoxRectangle){
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle)hitBoxes[i]);
                if(temp.isDead()) {
                    dead = true;
                }
            }
        }

    }

}
