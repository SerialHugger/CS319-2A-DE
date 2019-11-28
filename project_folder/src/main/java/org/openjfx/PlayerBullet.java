package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerBullet extends PlayerEquipment {
    PlayerBullet(double width, double height, String assetLocation, boolean toLeft){
        super(width,height,assetLocation);
        this.toLeft = toLeft;
        hitBoxes = new Rectangle[1];
        speed = 20;
        hitBoxes[0] = new ComponentHitBoxRectangle(width, height, "playerBulletHitbox");
        body = new Rectangle(width,height, Color.ORANGE);
    }

    public void update(Player player) {
        if(toLeft){ // If player was looking at left go left
            hitBoxes[0].setTranslateX(hitBoxes[0].getTranslateX() + speed  + player.speed);
            body.setTranslateX(body.getTranslateX() + speed + player.speed);
        } else { // else go right
            hitBoxes[0].setTranslateX(hitBoxes[0].getTranslateX() - speed -  player.speed);
            body.setTranslateX(body.getTranslateX() - speed -  player.speed);
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
