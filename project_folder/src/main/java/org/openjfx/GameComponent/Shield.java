package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Shield extends PlayerEquipment {
    int shieldStatus = 0; // if 0 its

    Shield(double width, double height, String assetLocation) {
        super(width, height, "shield");
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxCircle(magicConverter(55), "shield", "shield");

        this.body = new Circle(magicConverter(55));
        this.height = magicConverter(110);
        this.width = magicConverter(110);
        this.body.setFill(Color.rgb(255, 204, 0, 0.4));

        // set X and Y
    }

    public void moveShield(Player player) {
        setX(player.body.getTranslateX() + player.width / 2);
        setY(player.body.getTranslateY() + player.height / 2);
        // Actions when collision
        for (Shape hitBox : hitBoxes) {
            if (hitBox instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBox);
                if (temp.isDead())
                    dead = true;
            } else if (hitBox instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBox);
                if (temp.isDead()) {
                    dead = true;
                }
            }
        }
        if (dead)
            player.isShieldActive = false;
    }

    public void overCharge() {

    }
}
