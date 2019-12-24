package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Melee extends PlayerEquipment {

    // the arm is set to complete 5 full rotations around its axis (1080 degrees in total)
    private final int MAX_ROTATIONS = 108;
    private int rotations; // no. of rotations
    private final int ROTATION_INCREMENT = 10;
    private int currentAngle; // the amount that the arm will be rotated relative to its original position

    Melee(double width, double height, ImagePattern asset) {
        super(width, height, "playerEquipment");
        this.height = magicConverter(150);
        this.width = magicConverter(30);
        rotations = 0;
        currentAngle = ROTATION_INCREMENT;
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "playerEquipment", "melee");
        body = new Circle(this.height / 2);
        body.setFill(asset);
        body.setRotate(-45);
        body.setStroke(Color.PURPLE);
    }

    public void moveMelee(Player player) {

        if (rotations == MAX_ROTATIONS - 1)
            dead = true; // when 5 full rotations around axis is completed, remove the arm

        // set the position of the arm according to the players movement
        if (player.facingLeft) {
            setX(player.body.getTranslateX() + player.width);
            body.setTranslateX(player.body.getTranslateX() + player.width + 10);
        } else {
            setX(player.body.getTranslateX());
            body.setTranslateX(player.body.getTranslateX()+ 10);
        }

        setY(player.body.getTranslateY() - player.height);
        body.setTranslateY(player.body.getTranslateY()+player.getHeight()/2);


        // rotate the arm
        currentAngle += ROTATION_INCREMENT;
        rotate(currentAngle);
        body.setRotate(-45 + currentAngle);
        rotations += 1;
    }
}
