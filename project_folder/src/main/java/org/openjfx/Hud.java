package org.openjfx;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public class Hud{
    private SceneComponent topHud; // top part of the Hud
    private ImagePattern[] topHudLight = new ImagePattern[26]; // the imagepatterns for animation
    private double width; // width of the game
    private double height; // height of the game
    private int currentState = 0; // currentState of the animation
    boolean delay = true; // delay for animation
    int delayTimer = 0; // timer for delay
    Hud (double width, double height, String type, Pane gameRoot){
        topHud = new SceneComponent(width/7.5,height/3.686, "topHud", "Assets\\Scenery\\hud\\hud_nolight.png");
        this.width = width;
        this.height = height;
        topHud.setTranslateX( width / 200);
        topHud.setTranslateY( height / 200);
        try {
            FileInputStream inputstream = new FileInputStream("Assets\\Scenery\\hud\\hud_nolight.png");
            Image image = new Image(inputstream);
            topHudLight[0] = new ImagePattern(image);
        } catch ( Exception e ) {
            System.out.println(e.toString());
        }
        try {
            for(int i = 1; i < 25; i++) {
                FileInputStream inputstream = new FileInputStream("Assets\\Scenery\\hud\\hud_" + (i-1) + ".png");
                Image image = new Image(inputstream);
                topHudLight[i] = new ImagePattern(image);
            }
            FileInputStream inputstream = new FileInputStream("Assets\\Scenery\\hud\\hud_light.png");
            Image image = new Image(inputstream);
            topHudLight[25] = new ImagePattern(image);
        } catch ( Exception e ) {
            System.out.println(e.toString());
        }
        gameRoot.getChildren().add(topHud);
    }
    /*
     * Updates animation for the hud
     */
    public void update(){
        if(!delay) {
            delayTimer += 25;
            if(delayTimer == 50)
            {
                delay = true;
                delayTimer = 0;
            }
        }
        if(delay) { // loops states
            currentState = currentState + 1;
            if(currentState == 25)
                currentState = 1;
            topHud.setFill(topHudLight[currentState]);
            delay = false;
        }
    }

}
