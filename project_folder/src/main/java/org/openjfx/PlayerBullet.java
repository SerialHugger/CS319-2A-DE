package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerBullet extends PlayerEquipment {
    boolean facingDown = false;
    boolean facingUp = false;
    boolean horizEq = false;
    PlayerBullet(double width, double height, String assetLocation, boolean toLeft,  double speed){
        super(width,height,assetLocation);
        this.speed = Math.abs(speed) + magicConverter(25);
        this.height = magicConverter(5);
        this.width = magicConverter(20);
        this.facingLeft = toLeft;
        hitBoxes = new Rectangle[1];
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "playerBulletHitbox");
        body = new Rectangle(this.width,this.height, Color.ORANGE);
    }

    public void movePlayerBullet() {
        if (horizEq) {
            if (facingDown) {
                moveY(1, speed);
            } else {
                moveY(-1, speed);
            }

        } else {
            if(facingLeft){ // If player was looking at left go left
                moveX(1, speed);

                if (facingDown)
                    moveY(1, speed);
                else if (facingUp)
                    moveY(-1, speed);

            } else { // else go right
                moveX(-1, speed);

                if (facingDown)
                    moveY(1, speed);
                else if (facingUp)
                    moveY(-1, speed);
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
    public void rotate(double rotateValue){
        hitBoxes[0].setRotate(rotateValue);
        body.setRotate(rotateValue);
    }
}
