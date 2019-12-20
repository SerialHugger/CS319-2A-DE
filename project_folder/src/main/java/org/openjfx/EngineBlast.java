package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class EngineBlast extends PlayerEquipment {

    private final int TOTAL_LIFETIME = 1;
    private double wantedTime = 0;
    private int lifetime = 0;
    EngineBlast(double width, double height, String assetLocation) {
        super(width, height, "engineBlast");
        hitBoxes = new Rectangle[1];
        wantedTime = 1;
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "playerEquipment", "engineBlast");
        body = new Rectangle(this.width, this.height);
        body.setFill(Color.RED);
    }

    public void moveEngineBlast(Player player) {

        if (lifetime > TOTAL_LIFETIME)
            dead = true;

        firstTime = System.nanoTime() / 1000000000.0; // get time
        passedTime = firstTime - lastTime; // calculate passedTime
        lastTime = firstTime; // reset last time.
        totalPassedTime += passedTime; // calculate total passed time
        if(totalPassedTime > wantedTime) { // if 1 second is passed
            totalPassedTime = 0; // reset timer
            lifetime++;
        }

        // move the blast according to the player's movement
        if (player.facingLeft)
            setX(player.body.getTranslateX() - player.width/2.7);
        else
            setX(player.body.getTranslateX() + player.width/0.9);

        setY(player.body.getTranslateY());
    }

}
