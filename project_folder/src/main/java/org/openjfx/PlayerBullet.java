package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerBullet extends PlayerEquipment {
    int rotation = 0;
    boolean facingDown = false;
    boolean facingUp = false;
    boolean horizEq = false;

    PlayerBullet(double width, double height, String assetLocation, boolean toLeft,  double speed){
        super(width,height,assetLocation);
        this.facingLeft = toLeft;
        hitBoxes = new Rectangle[1];
        if(!facingLeft)
            this.speed = speed - width;
        else
            this.speed = speed + width;
        hitBoxes[0] = new ComponentHitBoxRectangle(width, height, "playerBulletHitbox");
        body = new Rectangle(width,height, Color.ORANGE);
    }

    public void movePlayerBullet() {
        if (horizEq) {
            if (facingDown) {
                body.setTranslateY(body.getTranslateY() + 6);
            } else {
                body.setTranslateY(body.getTranslateY() - 6);
            }

        } else {
            if(facingLeft){ // If player was looking at left go left
                hitBoxes[0].setTranslateX(hitBoxes[0].getTranslateX() + speed);
                body.setTranslateX(body.getTranslateX() + speed);

                if (facingDown)
                    body.setTranslateY(body.getTranslateY() + 6);
                else if (facingUp)
                    body.setTranslateY(body.getTranslateY() - 6);

            } else { // else go right
                hitBoxes[0].setTranslateX(hitBoxes[0].getTranslateX() - speed);
                body.setTranslateX(body.getTranslateX() - speed);

                if (facingDown)
                    body.setTranslateY(body.getTranslateY() + 6);
                else if (facingUp)
                    body.setTranslateY(body.getTranslateY() - 6);
            }
        }

        body.setRotate(rotation);
        hitBoxes[0].setRotate(rotation*-1);
        rotation += 20;


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
