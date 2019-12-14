package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Shield extends PlayerEquipment {
    int shieldStatus = 0; // if 0 its
    Shield(double width, double height, String assetLocation) {
        super(width, height, "Shield");
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxCircle(3,"shieldHitbox");
        body = new Circle(3, Color.BLUEVIOLET);

    }
    public void moveShield(){

    }
    public void overCharge(){

    }
}
