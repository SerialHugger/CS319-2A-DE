package org.openjfx.SceneryManager;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileInputStream;

public class Hud {
    private SceneComponent healthHud; // top part of the Hud
    private SceneComponent[] skillsHud;
    private StackPane fpsPane; // stackpane for fps display
    private StackPane scorePane;
    private ImagePattern[] healthHudFrames = new ImagePattern[36]; // the imagepatterns for animation
    private Text fpsText;
    private String type;
    private double width; // width of the game
    private double height; // height of the game
    private int currentState = 0; // currentState of the animation
    boolean delay = true; // delay for animation
    int delayTimer = 0; // timer for delay
    Pane gameRoot;
    private Text scoreText;
    private ImagePattern guidedRocketHudImage = openAsset("Assets\\light_saber.png");

    ///////////////
    // Necessary attiributes for changing directions with the ship
    boolean toLeft = false; // if facing left true else false. Starts with false;
    ////////////////

    Hud(double width, double height, String type, Pane gameRoot) {
        this.gameRoot = gameRoot;
        healthHud = new SceneComponent(width / 3.96, height / 5.684, "topHud", "Assets\\Scenery\\hud\\hud_0.png");
        skillsHud = new SceneComponent[5];

        for (int i = 0; i < skillsHud.length; i++) {
            skillsHud[i] = new SceneComponent(50, 50, "", "empty");
            skillsHud[i].setTranslateX(width * -1 + i * 52 + 30);
            skillsHud[i].setTranslateY(height - 100);
            skillsHud[i].setFill(Color.GREEN);
            skillsHud[i].setStroke(Color.BLACK);
            skillsHud[i].setStrokeType(StrokeType.OUTSIDE);
            gameRoot.getChildren().add(skillsHud[i]);
        }

        skillsHud[0].setFill(Color.ORANGE);
        skillsHud[1].setFill(Color.ORANGE);

        this.width = width;
        this.type = type;
        this.height = height;
        toLeft = false;
        healthHud.setTranslateX(-1 * width);
        healthHud.setTranslateY(height / 200);

        fpsPane = new StackPane();
        fpsPane.setMaxHeight(height / 54);
        fpsPane.setMaxWidth(width / 48);
        fpsPane.setTranslateX(-1 * width / 24);
        fpsPane.setTranslateY(height / 200);
        fpsText = new Text("fps: ");
        fpsText.setFont(Font.font("Verdana", width / 128));
        fpsText.setFill(Color.ORANGERED);
        scorePane = new StackPane();
        scorePane.setMaxHeight(height / 54);
        scorePane.setMaxWidth(width / 48);
        scorePane.setTranslateX(-1 * width + width / 10);
        scorePane.setTranslateY(height / 200 + height / 10);
        scoreText = new Text("SCORE: ");
        scoreText.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, width / 64));
        scoreText.setFill(Color.DARKORANGE);
        try {
            for (int i = 0; i < 36; i++) {
                FileInputStream inputstream = new FileInputStream("Assets\\Scenery\\hud\\hud_" + i + ".png");
                Image image = new Image(inputstream);
                healthHudFrames[i] = new ImagePattern(image);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        gameRoot.getChildren().add(healthHud);
        fpsPane.getChildren().add(fpsText);
        gameRoot.getChildren().add(fpsPane);
        scorePane.getChildren().add(scoreText);
        gameRoot.getChildren().add(scorePane);
    }

    /*
     * Updates animation for the hud
     */
    public void update(double speed, int fps, long score) {
        fpsText.setText("FPS: " + fps);
        scoreText.setText("SCORE: " + score);
        if (!delay) {
            delayTimer += 25;
            if (delayTimer == 75) {
                delay = true;
                delayTimer = 0;
            }
        }
        if (delay) { // loops states
            currentState = currentState + 1;
            if (currentState == 36)
                currentState = 0;
            healthHud.setFill(healthHudFrames[currentState]);
            delay = false;
        }
        moveX(1, speed);
    }

    public void displaySkills(String[] abilities) {
        for (int i = 2; i < skillsHud.length; i++) {

            if (abilities[i - 2].equals("empty")) {
                skillsHud[i].setFill(Color.GREEN);
            } else if (abilities[i - 2] == "guidedRocket") {
                skillsHud[i].setFill(guidedRocketHudImage);/* TODO ADD SKILL IMAGES*/
            } else if (abilities[i - 2] == "barrier") {
                skillsHud[i].setFill(Color.RED);/* TODO ADD SKILL IMAGES*/
            } else if (abilities[i - 2] == "shield") {
                skillsHud[i].setFill(Color.RED);/* TODO ADD SKILL IMAGES*/
            } else if (abilities[i - 2] == "melee") {
                skillsHud[i].setFill(Color.RED);/* TODO ADD SKILL IMAGES*/
            }

        }
    }

    public void slide(boolean toLeft, double slidingSpeed) {
        if (toLeft) {
            moveX(-1, slidingSpeed);
        } else {
            moveX(1, slidingSpeed);
        }
    }

    private void moveX(int direction, double moveSpeed) {
        healthHud.setTranslateX(healthHud.getTranslateX() + (direction * moveSpeed));
        fpsText.setTranslateX(fpsText.getTranslateX() + (direction * moveSpeed));
        scoreText.setTranslateX(scoreText.getTranslateX() + (direction * moveSpeed));
        for (int i = 0; i < skillsHud.length; i++) {
            skillsHud[i].setTranslateX(skillsHud[i].getTranslateX() + (direction * moveSpeed));
        }
    }

    public ImagePattern openAsset(String assetLocation) {
        ImagePattern imagePattern;
        try {
            // set background image
            FileInputStream inputstream = new FileInputStream(assetLocation);
            Image image = new Image(inputstream);
            imagePattern = new ImagePattern(image);
            inputstream.close();
        } catch (Exception e) {
            try {
                FileInputStream inputstream = new FileInputStream(assetLocation.replace("\\", "/"));
                Image image = new Image(inputstream);
                imagePattern = new ImagePattern(image);
                inputstream.close();
            } catch (Exception e2) {
                System.out.println(e2.toString());
                return null;
            }
        }
        return imagePattern;
    }
}
