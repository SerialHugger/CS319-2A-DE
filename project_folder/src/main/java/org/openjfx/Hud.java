package org.openjfx;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public class Hud{
    private SceneComponent topHud;
    private ImagePattern[] topHudLight = new ImagePattern[26];
    private double width;
    private double height;
    private int currentState = 0;
    boolean delay = true;
    int delayTimer = 0;
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
    public void update(){
        if(!delay)
        {
            delayTimer += 25;
            if(delayTimer == 100)
            {
                delay = true;
                delayTimer = 0;
            }
        }
        if(delay)
        {
            currentState = currentState + 1;
            if(currentState == 25)
                currentState = 1;
            topHud.setFill(topHudLight[currentState]);
            delay = false;
        }
    }

}
