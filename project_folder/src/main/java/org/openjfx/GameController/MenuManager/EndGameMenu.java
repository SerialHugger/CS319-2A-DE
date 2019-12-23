package org.openjfx.GameController.MenuManager;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.openjfx.SceneryManager.Scenery;

public class EndGameMenu extends Rectangle {

    private Rectangle returnBackButton;
    private Rectangle backgroundRect;

    private double sceneryWidth;
    private double sceneryHeight;
    private double buttonWidth;
    private double buttonHeight;

    private StackPane gameOverPane;
    private Text gameOverText;

    public EndGameMenu(Scenery scenery) {
        sceneryWidth = scenery.getWidth();
        sceneryHeight = scenery.getHeight();
        buttonWidth = 200;
        buttonHeight = 50;
    }

    public void createButton(Pane gameRoot) {
        returnBackButton = new Rectangle(buttonWidth, buttonHeight);
        backgroundRect = new Rectangle(sceneryWidth, sceneryHeight);

        backgroundRect.setVisible(false);
        returnBackButton.setVisible(false);

        gameRoot.getChildren().add(backgroundRect);
        gameRoot.getChildren().add(returnBackButton);

        fillRectangles();
    }

    private void fillRectangles() {
        backgroundRect.setFill(Color.rgb(42, 44, 54, 0.9));
        returnBackButton.setFill(Color.rgb(255, 204, 0, 0.9));
    }

    public void displayMenu(Pane gameRoot) {

        gameOverPane = new StackPane();
        gameOverPane.setMaxHeight( 50);
        gameOverPane.setMaxWidth(100);

        gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Verdana", 50));
        gameOverText.setFill(Color.ORANGERED);

        gameOverPane.setTranslateX(gameRoot.getTranslateX() * -1 + sceneryWidth / 2 - 150);
        gameOverPane.setTranslateY(sceneryHeight / 2 - 100);

        gameOverPane.getChildren().add(gameOverText);
        gameRoot.getChildren().add(gameOverPane);

        backgroundRect.setVisible(true);
        returnBackButton.setVisible(true);

        returnBackButton.setTranslateX(gameRoot.getTranslateX() * -1 + sceneryWidth / 2 - buttonWidth / 2);
        returnBackButton.setTranslateY(sceneryHeight / 2);

        backgroundRect.setTranslateX(gameRoot.getTranslateX() * -1);
        backgroundRect.setTranslateY(0);

        backgroundRect.toFront();
        gameOverPane.toFront();
        returnBackButton.toFront();
    }
}
