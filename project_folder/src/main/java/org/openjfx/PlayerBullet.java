package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerBullet extends PlayerEquipment {
    PlayerBullet(double width, double height, String url, boolean toLeft){
        super(width,height,url);
        this.toLeft = toLeft;
        hitBoxes = new Rectangle[1];
        speed = 35;
        hitBoxes[0] = new ComponentHitBoxRectangle(width, height, "playerBullet");
        body = new Rectangle(width,height, Color.PURPLE);
    }

    public void update() {
        if(toLeft){
            hitBoxes[0].setTranslateX(hitBoxes[0].getTranslateX() + speed);
            body.setTranslateX(body.getTranslateX() + speed);
        } else {
            hitBoxes[0].setTranslateX(hitBoxes[0].getTranslateX() - speed);
            body.setTranslateX(body.getTranslateX() - speed);
        }
    }
}
