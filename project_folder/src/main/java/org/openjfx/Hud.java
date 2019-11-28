package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;

public class Hud{
    private SceneComponent healthHud; // top part of the Hud
    private StackPane fpsPane; // stackpane for fps display
    private ImagePattern[] healthHudFrames = new ImagePattern[36]; // the imagepatterns for animation
    private Text fpsText;
    private double width; // width of the game
    private double height; // height of the game
    private int currentState = 0; // currentState of the animation
    boolean delay = true; // delay for animation
    int delayTimer = 0; // timer for delay
    Pane gameRoot;

    ///////////////
    // Necessary attiributes for changing directions with the ship
    boolean toLeft = false; // if facing left true else false. Starts with false;
    boolean startSlidingLeft = false; // slides background to left
    boolean startSlidingRight = false; // slides background to right
    double slidingLimit; // sliding limit for bacground
    double slidingCounter; // sliding counter for background
    double slidingSpeed; // sliding speed for background
    ////////////////

    Hud (double width, double height, String type, Pane gameRoot){
        this.gameRoot = gameRoot;
        healthHud = new SceneComponent(width/3.96,height / 5.684, "topHud", "/Users/cemalardakizilkaya/Desktop/Assets/Scenery/hud/hud_0.png");
        this.width = width;
        this.height = height;
        healthHud.setTranslateX( -1 * width);
        healthHud.setTranslateY( height / 200);
        fpsPane = new StackPane();
        fpsPane.setMaxHeight(height/54);
        fpsPane.setMaxWidth(width/48);
        fpsPane.setTranslateX(-1 * width/48);
        fpsPane.setTranslateY(height/200);
        fpsText = new Text("fps: ");
        fpsText.setFont(Font.font ("Verdana", 10));
        fpsText.setFill(Color.ORANGERED);

        try {
            for(int i = 0; i < 36; i++) {
                FileInputStream inputstream = new FileInputStream("/Users/cemalardakizilkaya/Desktop/Assets/Scenery/hud/hud_" + i + ".png");
                Image image = new Image(inputstream);
                healthHudFrames[i] = new ImagePattern(image);
            }
        } catch ( Exception e ) {
            System.out.println(e.toString());
        }
        gameRoot.getChildren().add(healthHud);
        fpsPane.getChildren().add(fpsText);
        gameRoot.getChildren().add(fpsPane);
        System.out.println("Hud added");
    }

    /*
     * Updates animation for the hud
     */
    public void update(BooleanProperty[] keyInputs, double speed, int fps){
        fpsText.setText("FPS: " + fps);
        if (keyInputs[3].get()) { // if the key D pressed
            if (toLeft) { // if it was toLeft, change camera and bring it to limit x.
                startSlidingLeft = true;
                startSlidingRight = false;
                toLeft = false;
            } else // if it was already not toLeft, just move it.
                moveX(1, speed);

        }
        if (keyInputs[1].get()) { // if the key A pressed
            if (!toLeft) { // if it was not toLeft, change camera and bring it to limit x.
                startSlidingRight = true;
                startSlidingLeft = false;
                toLeft = true;
            } else // if it was already to left, just move it
                moveX(-1, speed);
        }
        if (startSlidingLeft) { // if the background sliding left
            if (slidingLimit * -1 != slidingCounter) {// until sliding limit is reached
                moveX(1, slidingSpeed); // change background with sliding speed
                slidingCounter -= slidingSpeed;
            } else {
                startSlidingLeft = false; // finish the execution when the limit is reached.
            }
        }
        if (startSlidingRight) { // if the background sliding right
            if (slidingCounter != 0) {// until counter hits the 0
                moveX(-1, slidingSpeed); // change background with sliding speed
                slidingCounter += slidingSpeed;
            } else {
                startSlidingRight = false; // finish the execution when the limit is reached.
            }
        }
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
    }

    public void setSliding(double slidingLimit, double slidingCounter, double slidingSpeed){
        this.slidingLimit = slidingLimit;
        this.slidingCounter = slidingCounter;
        this.slidingSpeed = slidingSpeed;
    }

    private void moveX(int direction, double moveSpeed){
        healthHud.setTranslateX(healthHud.getTranslateX() + ( direction * moveSpeed));
        fpsText.setTranslateX(fpsText.getTranslateX() + ( direction * moveSpeed));
    }
}
