package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Explosion extends PlayerEquipment {

    private final int TOTAL_LIFETIME = 1;
    private double wantedTime;
    private int lifetime;

    Explosion(double width, double height, ImagePattern[] assets, String assetLocation) {
        super(width, height, "explosion");
        wantedTime = 1;
        lifetime = 0;
        hitBoxes = new Circle[1];
        hitBoxes[0] = new ComponentHitBoxCircle(height, "playerEquipment", "explosion");
        body = new Circle(height);

        animationFrames = new ImagePattern[8];
        for(int i = 0; i < 8; i++) {
            animationFrames[i] = assets[i];
        }

        body.setFill(animationFrames[currentState]);
        delayTimer = 0;
        delay = false;
    }

    public void updateExplosion() {

        if (lifetime > TOTAL_LIFETIME)
            dead = true;

        if (lifetime > TOTAL_LIFETIME) {
            dead = true;
        }
        if(delay)
            currentState += 1;
        else{
            delayTimer += 25;
            if(delayTimer > 100)
                currentState += 1;
        }

        if(currentState >= 8) {
            body.setFill(null);
            currentState -=1;
        }
        else
            body.setFill(animationFrames[currentState]);


        firstTime = System.nanoTime() / 1000000000.0; // get time
        passedTime = firstTime - lastTime; // calculate passedTime
        lastTime = firstTime; // reset last time.
        totalPassedTime += passedTime; // calculate total passed time
        if(totalPassedTime > wantedTime) { // if 1 second is passed
            totalPassedTime = 0; // reset timer
            lifetime++;
        }
    }
}
