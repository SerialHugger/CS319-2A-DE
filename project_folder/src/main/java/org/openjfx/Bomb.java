package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Bomb extends PlayerEquipment {
    Bomb(double width, double height,  String assetLocation) {
        super(width, height, "bomb");
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxCircle(this.width, "playerEquipment", "bomb");
        body = new Circle(this.width);
        body.setFill(Color.RED);
    }

    public void moveBomb() {
        if (gameRoot.getHeight() - height <= getY())
            dead = true;

        moveY(1, 10);
        rotate(60);
    }

    public void explode(GameComponentFactory GCF) {
        Explosion explosion = (Explosion) GCF.createComponent("explosion");
        explosion.setX(this.getX());
        explosion.setY(this.getY());
        explosion.addShapes(gameRoot);
    }
}
