package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.openjfx.SceneryManager.Scenery;

public class Barrier extends PlayerEquipment {

    Barrier(double width, double height, ImagePattern[] assets) {
        super(width, height, "shield");
        this.height = magicConverter(700);
        this.width = magicConverter(700);
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxCircle(magicConverter(width), "playerEquipment", "barrier");

        this.body = new Circle(magicConverter(width));
        this.height = magicConverter(width);
        this.width = magicConverter(height);
        this.body.setFill(Color.rgb(255, 204, 0, 0.4));
        // set X and Y
    }

    public void moveBarrier(Scenery scenery) {
        if (this.getY() <= scenery.getHeight()) {
            setY(this.getY() + 5);
        }
    }
}
