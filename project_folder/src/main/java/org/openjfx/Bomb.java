package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Bomb extends PlayerEquipment {
    Bomb(double width, double height,  String assetLocation) {
        super(width, height, "bomb");
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "playerEquipment", "bomb");
        System.out.println("Some bomb cheese!!!");
        body = new Rectangle(this.width, this.height);
        body.setFill(Color.RED);

    }

    public void moveBomb() {
        if (gameRoot.getHeight() - height <= getY())
            dead = true;

        moveY(1, 10);
        rotate(60);
    }

    public void explode(GameComponentFactory GCF) {
        EnemySelfDestruct selfDest = (EnemySelfDestruct) GCF.createComponent("enemySelfDestruct");
        selfDest.setX(this.getX());
        selfDest.setY(this.getY());
        selfDest.addShapes(gameRoot);
    }

    public void rotate(double rotateValue){
        hitBoxes[0].setRotate(rotateValue);
        body.setRotate(rotateValue);
    }
}
