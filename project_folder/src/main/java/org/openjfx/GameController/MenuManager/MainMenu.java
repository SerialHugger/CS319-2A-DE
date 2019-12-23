package org.openjfx.GameController.MenuManager;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.openjfx.Game;

import java.io.FileInputStream;

public class MainMenu {
    private Pane menuRoot;
    private double width;
    private double height;
    // set menu controller
    private MenuController menuController;
    private int shipSelected;

    /*
     * todo -> Insert setting,  make it good to go before start coding the game part.
     */
    public MainMenu(Pane root, double width, double height) {
        menuRoot = root; // get root
        this.width = width; // get width
        this.height = height; // get height
    }

    //Create content for main menu here.
    public Parent createContent() {
        ////////////////////////////////////////////////////////////
        /////////////creating buttons and its holders///////////////
        ////////////////////////////////////////////////////////////
        Rectangle[] buttons = new Rectangle[7]; // will hold start/howToPlay/setting/credits/quit buttons
        Rectangle[] settingButtons = new Rectangle[5]; // will hold videoResolution/fullScreen/sound/changeShip/goBack buttons
        Rectangle[] levelButtons = new Rectangle[3];
        Rectangle[] shipSelectButtons = new Rectangle[3];
        ImagePattern[] buttonImages = new ImagePattern[buttons.length * 2];// Image patterns to store asset information, and make it easier to use dynamically.
        ImagePattern[] settingsButtonImages = new ImagePattern[settingButtons.length * 2]; // same purpose but for settings
        ImagePattern[] levelButtonImages = new ImagePattern[levelButtons.length * 2];
        ImagePattern[] shipSelectImages = new ImagePattern[shipSelectButtons.length * 2];
        Rectangle background;
        Rectangle startGameButton;
        Rectangle howToPlayButton;
        Rectangle settingsButton;
        Rectangle creditsButton;
        Rectangle quitButton;
        Rectangle buttonHighlight; // for background of the buttons.
        Rectangle controlImage; // for how to play button
        Rectangle selectLevelButton;
        Rectangle selectShipButton;
        //for settings buttons
        Rectangle videoResolutionButton;
        Rectangle fullScreenButton;
        Rectangle soundButton;
        Rectangle changeShipButton;
        Rectangle goBackButton;
        Rectangle settingsButtonHighlight; // for background of the buttons at settings.

        // for level select buttons
        Rectangle levelButton1;
        Rectangle levelButton2;
        Rectangle levelButton3;
        Rectangle levelButtonHighlight;
        // for change ship buttons
        Rectangle selectShip1;
        Rectangle selectShip2;
        Rectangle selectShip3;
        Rectangle selectShipHighlight;
        // set root width and height
        menuRoot.setPrefSize(width, height);
        ////////////////////////////////////////////////////////////
        //////////////setting the main menu buttons/////////////////
        ////////////////////////////////////////////////////////////
        // for main elements
        background = new Rectangle(width, height, Color.GRAY); // create rectangle to insert image.
        controlImage = new Rectangle(width, height, Color.GRAY);
        // set start game button
        startGameButton = new Rectangle(width / 3.5, height / 18, Color.PURPLE);
        startGameButton.setTranslateX(width / 16);
        startGameButton.setTranslateY(height / 3.5);
        // set howToPlayButton
        howToPlayButton = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        howToPlayButton.setTranslateX(width / 16);
        howToPlayButton.setTranslateY(height / 2.8);
        // set settingsButton
        settingsButton = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        settingsButton.setTranslateX(width / 16);
        settingsButton.setTranslateY(height / 2.8 - ((height / 3.5) - (height / 2.8)));
        // set creditsButton
        creditsButton = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        creditsButton.setTranslateX(width / 16);
        creditsButton.setTranslateY(height / 2.8 - (2 * ((height / 3.5) - (height / 2.8))));
        // set quitButton
        quitButton = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        quitButton.setTranslateX(width / 16);
        quitButton.setTranslateY(height / 2.8 - (3 * ((height / 3.5) - (height / 2.8))));
        //set selectlevelButton
        selectLevelButton = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        selectLevelButton.setTranslateX(width / 16);
        selectLevelButton.setTranslateY(height / 2.8 - (4 * ((height / 3.5) - (height / 2.8))));

        selectShipButton = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        selectShipButton.setTranslateX(width / 16);
        selectShipButton.setTranslateY(height / 2.8 - (5 * ((height / 3.5) - (height / 2.8))));
        // set buttons array to make life easier.
        buttons[0] = startGameButton;
        buttons[1] = howToPlayButton;
        buttons[2] = settingsButton;
        buttons[3] = creditsButton;
        buttons[4] = quitButton;
        buttons[5] = selectLevelButton;
        buttons[6] = selectShipButton;
        // set backgroundButton for cool effect.
        buttonHighlight = new Rectangle(width / 3.5, height / 18, null);
        buttonHighlight.setTranslateX(width / 16);
        buttonHighlight.setTranslateY(height / 3.5);
        /////////////////////
        // for settings menu
        /////////////////////
        // set video resolution button
        videoResolutionButton = new Rectangle(width / 3.5, height / 18, Color.PURPLE);
        videoResolutionButton.setTranslateX(width / 16);
        videoResolutionButton.setTranslateY(height / 3.5);
        // set fullScreen
        fullScreenButton = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        fullScreenButton.setTranslateX(width / 16);
        fullScreenButton.setTranslateY(height / 2.8);
        // set sound button
        soundButton = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        soundButton.setTranslateX(width / 16);
        soundButton.setTranslateY(height / 2.8 - ((height / 3.5) - (height / 2.8)));
        // set change ship button
        changeShipButton = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        changeShipButton.setTranslateX(width / 16);
        changeShipButton.setTranslateY(height / 2.8 - (2 * ((height / 3.5) - (height / 2.8))));
        // set go back button
        goBackButton = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        goBackButton.setTranslateX(width / 16);
        goBackButton.setTranslateY(height / 2.8 - (3 * ((height / 3.5) - (height / 2.8))));
        // set buttons array to make life easier.
        settingButtons[0] = videoResolutionButton;
        settingButtons[1] = fullScreenButton;
        settingButtons[2] = soundButton;
        settingButtons[3] = changeShipButton;
        settingButtons[4] = goBackButton;
        // set button highlight for cool effect.
        settingsButtonHighlight = new Rectangle(width / 3.5, height / 18, null);
        settingsButtonHighlight.setTranslateX(width / 16);
        settingsButtonHighlight.setTranslateY(height / 3.5);
        ///////////// level select button //////////////////////////
        // level 1 button
        levelButton1 = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        levelButton1.setTranslateX(width / 16);
        levelButton1.setTranslateY(height / 3.5);
        // level 2 button
        levelButton2 = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        levelButton2.setTranslateX(width / 16);
        levelButton2.setTranslateY(height / 2.8);
        // level 3 button
        levelButton3 = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        levelButton3.setTranslateX(width / 16);
        levelButton3.setTranslateY(height / 2.8 - ((height / 3.5) - (height / 2.8)));
        // set buttons array to make life easier.
        levelButtons[0] = levelButton1;
        levelButtons[1] = levelButton2;
        levelButtons[2] = levelButton3;
        // background
        levelButtonHighlight = new Rectangle(width / 3.5, height / 18, null);
        levelButtonHighlight.setTranslateX(width / 16);
        levelButtonHighlight.setTranslateY(height / 3.5);
        // ship selection
        selectShip1 = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        selectShip1.setTranslateX(width / 16);
        selectShip1.setTranslateY(height / 3.5);

        selectShip2 = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        selectShip2.setTranslateX(width / 16);
        selectShip2.setTranslateY(height / 2.8 - (2 * ((height / 3.5) - (height / 2.8))));

        selectShip3 = new Rectangle(width / 3.5, height / 18, Color.WHITE);
        selectShip3.setTranslateX(width / 16);
        selectShip3.setTranslateY(height / 2.8 - (3 * ((height / 3.5) - (height / 2.8))));
        // set buttons to array to make life easier
        shipSelectButtons[0] = levelButton1;
        shipSelectButtons[1] = levelButton2;
        shipSelectButtons[2] = levelButton3;

        selectShipHighlight = new Rectangle(width / 3.5, height / 18, null);
        selectShipHighlight.setTranslateX(width / 16);
        selectShipHighlight.setTranslateY(height / 3.5);
        ////////////////////////////////////////////////////////////
        ///////////////setting images for buttons///////////////////
        ////////////////////////////////////////////////////////////
        // for main menu
        insertImage(background, "Assets\\mainMenu\\Main_Menu.jpg", true);
        insertImage(buttonHighlight, "Assets\\mainMenu\\buttonHighlight.png", true);
        insertImage(controlImage, "Assets\\mainMenu\\controls.jpg", true);
        buttonImages[0] = insertImage(startGameButton, "Assets\\mainMenu\\startButton_White.png", false);
        buttonImages[1] = insertImage(startGameButton, "Assets\\mainMenu\\startButton_Black.png", true);
        buttonImages[2] = insertImage(howToPlayButton, "Assets\\mainMenu\\howToPlayButton_White.png", true);
        buttonImages[3] = insertImage(howToPlayButton, "Assets\\mainMenu\\howToPlayButton_Black.png", false);
        buttonImages[4] = insertImage(settingsButton, "Assets\\mainMenu\\settingsButton_White.png", true);
        buttonImages[5] = insertImage(settingsButton, "Assets\\mainMenu\\settingsButton_Black.png", false);
        buttonImages[6] = insertImage(creditsButton, "Assets\\mainMenu\\creditsButton_White.png", true);
        buttonImages[7] = insertImage(creditsButton, "Assets\\mainMenu\\creditsButton_Black.png", false);
        buttonImages[8] = insertImage(quitButton, "Assets\\mainMenu\\quitButton_White.png", true);
        buttonImages[9] = insertImage(quitButton, "Assets\\mainMenu\\quitButton_Black.png", false);
        buttonImages[10] = insertImage(selectLevelButton, "Assets\\mainMenu\\howToPlayButton_White.png", true);
        buttonImages[11] = insertImage(selectLevelButton, "Assets\\mainMenu\\howToPlayButton_White.png", false);
        buttonImages[12] = insertImage(selectShipButton, "Assets\\mainMenu\\changeShipButton_White.png", true);
        buttonImages[13] = insertImage(selectShipButton, "Assets\\mainMenu\\changeShipButton_Black.png", false);
        // for settings menu
        insertImage(settingsButtonHighlight, "Assets\\mainMenu\\buttonHighlight.png", true);
        settingsButtonImages[0] = insertImage(videoResolutionButton, "Assets\\mainMenu\\videoResolution_White.png", false);
        settingsButtonImages[1] = insertImage(videoResolutionButton, "Assets\\mainMenu\\videoResolution_Black.png", true);
        settingsButtonImages[2] = insertImage(fullScreenButton, "Assets\\mainMenu\\fullscreenButton_White.png", true);
        settingsButtonImages[3] = insertImage(fullScreenButton, "Assets\\mainMenu\\fullScreenButton_Black.png", false);
        settingsButtonImages[4] = insertImage(soundButton, "Assets\\mainMenu\\soundButton_White.png", true);
        settingsButtonImages[5] = insertImage(soundButton, "Assets\\mainMenu\\soundButton_Black.png", false);
        settingsButtonImages[6] = insertImage(changeShipButton, "Assets\\mainMenu\\changeShipButton_White.png", true);
        settingsButtonImages[7] = insertImage(changeShipButton, "Assets\\mainMenu\\changeShipButton_Black.png", false);
        settingsButtonImages[8] = insertImage(goBackButton, "Assets\\mainMenu\\goBackButton_White.png", true);
        settingsButtonImages[9] = insertImage(goBackButton, "Assets\\mainMenu\\goBackButton_Black.png", false);
        // for level selection menu
        insertImage(levelButtonHighlight, "Assets\\mainMenu\\buttonHighlight.png", true);
        levelButtonImages[0] = insertImage(levelButton1, "Assets\\mainMenu\\howToPlayButton_White.png", false);
        levelButtonImages[1] = insertImage(levelButton1, "Assets\\mainMenu\\level1-removebg-preview.png", true);
        levelButtonImages[2] = insertImage(levelButton2, "Assets\\mainMenu\\howToPlayButton_White.png", true);
        levelButtonImages[3] = insertImage(levelButton2, "Assets\\mainMenu\\level2-removebg-preview.png", false);
        levelButtonImages[4] = insertImage(levelButton3, "Assets\\mainMenu\\howToPlayButton_White.png", true);
        levelButtonImages[5] = insertImage(levelButton3, "Assets\\mainMenu\\level3-removebg-preview.png", false);
        // for ship selection
        insertImage(selectShipHighlight, "Assets\\mainMenu\\buttonHighlight.png", true);
        shipSelectImages[0] = insertImage(levelButton1, "Assets\\mainMenu\\changeShipButton_White.png", false);
        shipSelectImages[1] = insertImage(levelButton1, "Assets\\mainMenu\\changeShip1_black.png", true);
        shipSelectImages[2] = insertImage(levelButton1, "Assets\\mainMenu\\changeShipButton_White.png", true);
        shipSelectImages[3] = insertImage(levelButton1, "Assets\\mainMenu\\changeShip2_black.png", false);
        shipSelectImages[4] = insertImage(levelButton1, "Assets\\mainMenu\\changeShipButton_White.png", true);
        shipSelectImages[5] = insertImage(levelButton1, "Assets\\mainMenu\\changeShip3_black.png", false);
        ////////////////////////////////////////////////////////////
        //////////////add necessary elements to root////////////////
        ////////////////////////////////////////////////////////////
        menuRoot.getChildren().add(background);
        menuRoot.getChildren().add(buttonHighlight);
        menuRoot.getChildren().add(startGameButton);
        menuRoot.getChildren().add(howToPlayButton);
        menuRoot.getChildren().add(settingsButton);
        menuRoot.getChildren().add(creditsButton);
        menuRoot.getChildren().add(selectLevelButton);
        menuRoot.getChildren().add(quitButton);
        menuRoot.getChildren().add(settingsButtonHighlight);
        menuRoot.getChildren().add(videoResolutionButton);
        menuRoot.getChildren().add(fullScreenButton);
        menuRoot.getChildren().add(soundButton);
        menuRoot.getChildren().add(changeShipButton);
        menuRoot.getChildren().add(goBackButton);
        menuRoot.getChildren().add(levelButton1);
        menuRoot.getChildren().add(levelButton2);
        menuRoot.getChildren().add(levelButton3);
        menuRoot.getChildren().add(selectShipHighlight);
        menuRoot.getChildren().add(selectShipButton);
        menuRoot.getChildren().add(selectShip1);
        menuRoot.getChildren().add(selectShip2);
        menuRoot.getChildren().add(selectShip3);

        // make settings invisible.
        goBackButton.setVisible(false);
        changeShipButton.setVisible(false);
        soundButton.setVisible(false);
        fullScreenButton.setVisible(false);
        videoResolutionButton.setVisible(false);
        settingsButtonHighlight.setVisible(false);
        levelButtonHighlight.setVisible(false);
        levelButton1.setVisible(false);
        levelButton2.setVisible(false);
        levelButton3.setVisible(false);
        selectShip1.setVisible(false);
        selectShip2.setVisible(false);
        selectShip3.setVisible(false);
        selectShipHighlight.setVisible(false);


        // create the controller and return
        menuController = new MenuController(buttons, buttonImages, buttonHighlight, controlImage, settingButtons, settingsButtonImages, settingsButtonHighlight, levelButtons, levelButtonImages, levelButtonHighlight, shipSelectButtons, shipSelectImages, selectShipHighlight);
        return menuRoot;
    }

    // update everything with the current input
    public void update(Game game) {
        menuController.update(game, menuRoot);
        shipSelected = menuController.getShipSelected();
    }

    //Setup buttonHandler to control menu
    public void setButtonHandler(Scene scene) {
        menuController.setButtonHandler(scene);
    }

    // Sets the image from url to button and return imagePattern, if fill is true, else just opens it and returns.
    public ImagePattern insertImage(Rectangle button, String url, boolean fill) {
        ImagePattern imagePattern;
        try {
            // set background image
            FileInputStream inputstream = new FileInputStream(url);
            Image image = new Image(inputstream);
            imagePattern = new ImagePattern(image);
            if (fill)
                button.setFill(imagePattern);
            inputstream.close();
        } catch (Exception e) {
            try {
                // set background image
                FileInputStream inputstream = new FileInputStream(url.replace("\\", "/"));
                Image image = new Image(inputstream);
                imagePattern = new ImagePattern(image);
                if (fill)
                    button.setFill(imagePattern);
                inputstream.close();
            } catch (Exception e2) {
                System.out.println(e.toString());
                return null;
            }
        }
        return imagePattern;
    }

    public int getShipSelected() {
        shipSelected = menuController.getShipSelected();
        return shipSelected;
    }

    public boolean isFullscreen() {
        return menuController.isFullscreen();
    }

}
