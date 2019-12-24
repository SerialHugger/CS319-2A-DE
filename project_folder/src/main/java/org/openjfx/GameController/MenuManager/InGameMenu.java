package org.openjfx.GameController.MenuManager;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.openjfx.SceneryManager.Scenery;

import java.io.FileInputStream;

public class InGameMenu extends Rectangle {

    int activeButton;

    private double sceneryWidth;
    private double sceneryHeight;
    private double buttonWidth;
    private double buttonHeight;

    private Rectangle rectangle1;
    private Rectangle rectangle2;
    private Rectangle backgroundRect;

    /**
     * Constructor
     * @param scenery parent scenery
     */
    public InGameMenu(Scenery scenery) {
        activeButton = 0;

        sceneryWidth = scenery.getWidth();
        sceneryHeight = scenery.getHeight();
        buttonWidth = 200;
        buttonHeight = 50;
    }

    /**
     * create buttons for In Game menu with background
     * @param gameRoot root pane
     */
    public void createButtons(Pane gameRoot) {
        rectangle1 = new Rectangle(buttonWidth, buttonHeight);
        rectangle2 = new Rectangle(buttonWidth, buttonHeight);

        backgroundRect = new Rectangle(sceneryWidth, sceneryHeight);

        backgroundRect.setVisible(false);
        rectangle1.setVisible(false);
        rectangle2.setVisible(false);

        gameRoot.getChildren().add(backgroundRect);
        gameRoot.getChildren().add(rectangle1);
        gameRoot.getChildren().add(rectangle2);


        fillRectangles();
    }

    /**
     * Changes active button to 1 or 0. 0 is continue, 1 is exit to menu
     * @param buttonID integer ID for the button
     */
    public void changeActiveButton(int buttonID) {
        activeButton = buttonID;
        fillRectangles();
    }

    /**
     * fills all rectangle objects with colors
     */
    private void fillRectangles() {
        backgroundRect.setFill(Color.rgb(42, 44, 54, 0.7));
        ImagePattern continueActiveImage = openAsset("Assets\\continue_black.png");
        ImagePattern continueInactiveImage = openAsset("Assets\\continue_white.png");
        ImagePattern exitActiveImage = openAsset("Assets\\exit_black.png");
        ImagePattern exitInactiveImage = openAsset("Assets\\exit_white.png");
        if (activeButton == 0) {
            rectangle1.setFill(continueActiveImage);
            rectangle2.setFill(exitInactiveImage);
            //rectangle1.setFill(Color.rgb(255, 204, 0, 0.9));
            //rectangle2.setFill(Color.rgb(144, 3, 252, 0.4));

        } else {
            rectangle1.setFill(continueInactiveImage);
            rectangle2.setFill(exitActiveImage);
            //rectangle1.setFill(Color.rgb(144, 3, 252, 0.4));
            //rectangle2.setFill(Color.rgb(255, 204, 0, 0.9));
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

    /**
     * Displays menu over parent screen
     * @param gameRoot root pane
     */
    public void displayMenu(Pane gameRoot) {
        backgroundRect.setVisible(true);
        rectangle1.setVisible(true);
        rectangle2.setVisible(true);

        rectangle1.setTranslateX(gameRoot.getTranslateX() * -1 + sceneryWidth / 2 - buttonWidth - 5);
        rectangle1.setTranslateY(sceneryHeight / 2);

        rectangle2.setTranslateX(gameRoot.getTranslateX() * -1 + sceneryWidth / 2 + 5);
        rectangle2.setTranslateY(sceneryHeight / 2);

        backgroundRect.setTranslateX(gameRoot.getTranslateX() * -1);
        backgroundRect.setTranslateY(0);

        backgroundRect.toFront();
        rectangle1.toFront();
        rectangle2.toFront();
    }

    /**
     * hides menu
     */
    public void hideMenu() {
        rectangle1.setVisible(false);
        rectangle2.setVisible(false);
        backgroundRect.setVisible(false);
    }

    /**
     *
     * @return id of current active button
     */
    public int getActiveButton() {
        return activeButton;
    }

}
