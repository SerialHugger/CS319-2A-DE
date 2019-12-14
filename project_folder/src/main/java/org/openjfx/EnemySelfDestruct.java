package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class EnemySelfDestruct extends Enemy{

    private final int totalLifetime = 2;
    private int lifetime;

    EnemySelfDestruct (double width, double height, String assetLocation){
        super(width,height,assetLocation);
        hitBoxes = new Circle[1];
        speed = 7;
        hitBoxes[0] = new ComponentHitBoxCircle(75, "selfDestructHitbox");
        body = new Circle(75, Color.RED);
        //fillImage(assetLocation);
    }

    public void updateSelfDestruct() {

        if (lifetime > totalLifetime) {
            dead = true;
        }

        firstTime = System.nanoTime() / 1000000000.0; // get time
        passedTime = firstTime - lastTime; // calculate passedTime
        lastTime = firstTime; // reset last time.
        totalPassedTime += passedTime; // calculate total passed time
        if(totalPassedTime > 1.0) { // if 1 second is passed
            totalPassedTime = 0; // reset timer
            lifetime++;
        }

    }
}
