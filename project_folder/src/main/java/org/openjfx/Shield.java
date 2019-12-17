package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Shield extends PlayerEquipment {
    int shieldStatus = 0; // if 0 its
    Shield(double width, double height, String assetLocation) {
        super(width, height, "Shield");
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxCircle(width / 2,"shieldHitbox");

        this.body = new Circle(width / 2);
        this.body.setFill(Color.rgb(255, 204, 0, 0.4) );

        // set X and Y
    }
    public void moveShield(Player player){
        setX(player.body.getTranslateX() + player.width / 2);
        setY(player.body.getTranslateY() + player.height/ 2);
    }

    public void overCharge(){

    }
}
