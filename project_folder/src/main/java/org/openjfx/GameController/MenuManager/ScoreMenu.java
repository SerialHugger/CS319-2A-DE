package org.openjfx.GameController.MenuManager;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.openjfx.SceneryManager.Scenery;

public class ScoreMenu extends Rectangle {

    private double sceneryWidth;
    private double sceneryHeight;
    private Rectangle backgroundRect;


    private StackPane scorePane;
    private Text scoreText;

    public ScoreMenu(Scenery scenery) {
        sceneryWidth = scenery.getWidth();
        sceneryHeight = scenery.getHeight();
    }

    public void createScoreScreen(Pane gameRoot) {
        backgroundRect = new Rectangle(sceneryWidth, sceneryHeight);
        scorePane = new StackPane();

        scorePane.setVisible(false);
        backgroundRect.setVisible(false);
        gameRoot.getChildren().add(backgroundRect);

        backgroundRect.setFill(Color.rgb(42, 44, 54, 0.9));
    }

    public void displayScoreScreen(Pane gameRoot, int score) {

        scorePane.setMaxHeight(50);
        scorePane.setMaxWidth(100);
        scoreText = new Text("LEVEl COMPLETED. SCORE: " + score);

        scorePane.setTranslateX(gameRoot.getTranslateX() * -1 + sceneryWidth / 2 - 150);
        scorePane.setTranslateY(sceneryHeight / 2 - 100);

        scorePane.getChildren().add(scoreText);
        gameRoot.getChildren().add(scorePane);

        backgroundRect.setVisible(true);
        scorePane.setVisible(true);

        backgroundRect.toFront();
        scorePane.toFront();
    }

    public void hideScoreScreen() {
        scorePane.setVisible(false);
        backgroundRect.setVisible(false);
    }
}
