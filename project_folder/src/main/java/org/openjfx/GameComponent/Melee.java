package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Melee extends PlayerEquipment {

    // the arm is set to complete 5 full rotations around its axis (1080 degrees in total)
    private final int MAX_ROTATIONS = 108;
    private int rotations; // no. of rotations
    private final int ROTATION_INCREMENT = 10;
    private int currentAngle; // the amount that the arm will be rotated relative to its original position

    Melee(double width, double height, String assetLocation) {
        super(width, height, "melee");
        rotations = 0;
        System.out.println("Melee arm created...");
        currentAngle = ROTATION_INCREMENT;
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "playerEquipment", "melee");
        body = new Rectangle(this.width, this.height);
        body.setFill(Color.ORANGE);
    }

    public void moveMelee(Player player) {

        if (rotations == MAX_ROTATIONS - 1)
            dead = true; // when 5 full rotations around axis is completed, remove the arm

        // set the position of the arm according to the players movement
        if (player.facingLeft)
            setX(player.body.getTranslateX() + player.width);
        else
            setX(player.body.getTranslateX());

        setY(player.body.getTranslateY() - player.height);

        // rotate the arm
        currentAngle += ROTATION_INCREMENT;
        rotate(currentAngle);
        rotations += 1;
    }
}
