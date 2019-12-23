package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.openjfx.SceneryManager.Scenery;

public class Barrier extends PlayerEquipment {

    /**
     * constructor for barrier, an equipment of player which declines barriers
     * @param width width of thee barrier's effective area
     * @param height height oof the barrier's effective area
     * @param assets images for animation
     */
    Barrier(double width, double height, String assets) {
        super(width, height, "shield");
        this.height = magicConverter(700);
        this.width = magicConverter(700);
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxCircle(magicConverter(width), "playerEquipment", "barrier");

        this.body = new Circle(magicConverter(width));
        this.body.setFill(Color.rgb(255, 204, 0, 0.4));
        // set X and Y
    }

    /**
     * for moving barrier to the ground because of the gravity
     * @param scenery gets the scene
     */
    public void moveBarrier(Scenery scenery) {
        if (this.getY() <= scenery.getHeight()) {
            setY(this.getY() + 5);
        }
    }
}
