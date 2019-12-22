package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Bomb extends PlayerEquipment {
    Bomb(double width, double height, ImagePattern assets, Player player) {
        super(width, height, "bomb");
        hitBoxes = new Shape[1];
        speed = magicConverter(10);
        this.height = magicConverter(50);
        this.width = magicConverter(40);
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "playerEquipment", "bomb");
        speed_x = player.speed;
        body = new Rectangle(this.width, this.height);
        body.setFill(Color.RED);

    }

    public void moveBomb() {
        if (gameRoot.getHeight() - height <= getY())
            dead = true;
        moveX(1, speed_x);
        moveY(1, speed);
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
