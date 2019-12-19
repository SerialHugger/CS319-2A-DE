package org.openjfx;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.swing.plaf.ColorUIResource;
import java.io.FileInputStream;

public class EnemySelfDestruct extends EnemyEquipment{

    private final int TOTAL_LIFETIME = 1;
    private double wantedTime = 0;
    private int lifetime = 0;
    private boolean harmful;
    EnemySelfDestruct (double width, double height, ImagePattern[] assets, boolean harmful){
        super(width,height,"enemySelfDestruct");
        hitBoxes = new Circle[1];

        this.harmful = harmful;
        if(harmful) {
            hitBoxes[0] = new ComponentHitBoxCircle(magicConverter(250), "enemySelfDestruct", "selfDestruct");
            this.height = magicConverter(350);
            this.width = magicConverter(350);
            wantedTime = 0.5;
        } else {
            hitBoxes[0] = new ComponentHitBoxCircle(magicConverter(30), "harmless", "selfDestruct");
            this.height = magicConverter( 50);
            this.width = magicConverter(50);
            wantedTime = 0.2;
        }

        body = new Circle(this.width);
        animationFrames = new ImagePattern[8];
        for(int i = 0; i < 8; i++) {
            animationFrames[i] = assets[i];
        }
        body.setFill(animationFrames[currentState]);
        delayTimer = 0;
        delay = false;
    }

    public void updateSelfDestruct() {

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
        if(harmful) {
            if (currentState == 8)
                currentState = 3;
            body.setFill(animationFrames[currentState]);
        } else {
            if(currentState >= 8) {
                body.setFill(null);
                currentState -=1;
            }
            else
                body.setFill(animationFrames[currentState]);
        }

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
