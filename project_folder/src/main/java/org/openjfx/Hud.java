package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.FileInputStream;

public class Hud{
    private SceneComponent healthHud; // top part of the Hud
    private SceneComponent[] skillsHud;
    private StackPane fpsPane; // stackpane for fps display
    private ImagePattern[] healthHudFrames = new ImagePattern[36]; // the imagepatterns for animation
    private Text fpsText;
    private String type;
    private double width; // width of the game
    private double height; // height of the game
    private int currentState = 0; // currentState of the animation
    boolean delay = true; // delay for animation
    int delayTimer = 0; // timer for delay
    Pane gameRoot;

    ///////////////
    // Necessary attiributes for changing directions with the ship
    boolean toLeft = false; // if facing left true else false. Starts with false;
    ////////////////

    Hud (double width, double height, String type, Pane gameRoot){
        this.gameRoot = gameRoot;
        healthHud = new SceneComponent(width/3.96,height / 5.684, "topHud", "Assets\\Scenery\\hud\\hud_0.png");
        skillsHud = new SceneComponent[3];

        for (int i = 0; i < skillsHud.length; i++) {
            skillsHud[i] = new SceneComponent(50, 50, "", "empty");
            skillsHud[i].setTranslateX(-(skillsHud.length - i) * 52 - 100);
            skillsHud[i].setTranslateY(height - 40);
            skillsHud[i].setFill(Color.GREEN);

            skillsHud[i].setStroke(Color.BLACK);
            skillsHud[i].setStrokeType(StrokeType.OUTSIDE);
            gameRoot.getChildren().add(skillsHud[i]);
        }

        this.width = width;
        this.type = type;
        this.height = height;
        toLeft = false;
        healthHud.setTranslateX( -1 * width );
        healthHud.setTranslateY( height / 200);

        fpsPane = new StackPane();
        fpsPane.setMaxHeight(height/54);
        fpsPane.setMaxWidth(width/48);
        fpsPane.setTranslateX(-1 * width/24);
        fpsPane.setTranslateY(height/200);
        fpsText = new Text("fps: ");
        fpsText.setFont(Font.font ("Verdana", width/128));
        fpsText.setFill(Color.ORANGERED);

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
        fpsPane.getChildren().add(fpsText);
        gameRoot.getChildren().add(fpsPane);
    }

    /*
     * Updates animation for the hud
     */
    public void update(double speed, int fps){
        fpsText.setText("FPS: " + fps);
        if(!delay) {
            delayTimer += 25;
            if(delayTimer == 75)
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
        moveX(1, speed);
    }

    public void displaySkills(String[] abilities) {
        for (int i = 0; i < skillsHud.length; i++ ) {
            if (abilities[i].equals("empty")) {
                skillsHud[i].setFill(Color.GREEN);
            } else {
                skillsHud[i].setFill(Color.RED);
            }
        }
    }

    public void slide(boolean toLeft,double slidingSpeed) {
        if(toLeft){
            moveX(-1,slidingSpeed);
        } else {
            moveX(1,slidingSpeed);
        }
    }

    private void moveX(int direction, double moveSpeed){
        healthHud.setTranslateX(healthHud.getTranslateX() + (direction * moveSpeed));
        fpsText.setTranslateX(fpsText.getTranslateX() + (direction * moveSpeed));
        for (int i = 0; i < skillsHud.length; i++) {
            skillsHud[i].setTranslateX(skillsHud[i].getTranslateX() + (direction * moveSpeed));
        }
    }
}
