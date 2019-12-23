package org.openjfx.GameController.MenuManager;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
        scoreText = new Text("LEVEL COMPLETED. SCORE: ");
        scoreText.setFont(Font.font("Verdana", 20));
        scoreText.setFill(Color.ORANGERED);

        scorePane.setVisible(false);
        backgroundRect.setVisible(false);

        backgroundRect.setFill(Color.rgb(42, 44, 54, 0.9));

        scorePane.setMaxHeight(50);
        scorePane.setMaxWidth(100);

        scorePane.getChildren().add(scoreText);
        gameRoot.getChildren().add(scorePane);
        gameRoot.getChildren().add(backgroundRect);

    }

    public void displayScoreScreen(Pane gameRoot, int score) {
        scoreText.setText("LEVEL COMPLETED. SCORE: " + score);

        backgroundRect.setTranslateX(gameRoot.getTranslateX() * -1);
        backgroundRect.setTranslateY(0);

        scorePane.setTranslateX(gameRoot.getTranslateX() * -1 + sceneryWidth / 2 - 150);
        scorePane.setTranslateY(sceneryHeight / 2 - 100);

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
