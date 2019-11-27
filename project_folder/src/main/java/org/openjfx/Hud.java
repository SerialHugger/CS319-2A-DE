package org.openjfx;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;

public class Hud{
    private SceneComponent healthHud; // top part of the Hud
    private ImagePattern[] healthHudFrames = new ImagePattern[36]; // the imagepatterns for animation
    private double width; // width of the game
    private double height; // height of the game
    private int currentState = 0; // currentState of the animation
    boolean delay = true; // delay for animation
    int delayTimer = 0; // timer for delay
    Hud (double width, double height, String type, Pane gameRoot){
        healthHud = new SceneComponent(width/3.96,height / 5.684, "topHud", "Assets\\Scenery\\hud\\hud_0.png");
        this.width = width;
        this.height = height;
        healthHud.setTranslateX( -1 *width);
        healthHud.setTranslateY( height / 200);

        try {
            for(int i = 0; i < 36; i++) {
                FileInputStream inputstream = new FileInputStream("Assets\\Scenery\\hud\\hud_" + i + ".png");
                Image image = new Image(inputstream);
                healthHudFrames[i] = new ImagePattern(image);
            }
        } catch ( Exception e ) {
            System.out.println(e.toString());
        }
        gameRoot.getChildren().add(healthHud);
        System.out.println("Hud added");
    }
    /*
     * Updates animation for the hud
     */
    public void update(){
        if(!delay) {
            delayTimer += 25;
            if(delayTimer == 250)
            {
                delay = true;
                delayTimer = 0;
            }
        }
        if(delay) { // loops states
            currentState = currentState + 1;
            if(currentState == 36)
                currentState = 0;
            healthHud.setFill(healthHudFrames[currentState]);
            delay = false;
        }
    }

}
