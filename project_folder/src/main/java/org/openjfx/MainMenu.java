package org.openjfx;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


import java.io.FileInputStream;

public class MainMenu {
    private Pane menuRoot;
    private double width;
    private double height;
    // set menu controller
    private MenuController menuController;

    /*
     * todo -> Insert setting,  make it good to go before start coding the game part.
     */
    MainMenu(Pane root, double width, double height)
    {
        menuRoot = root; // get root
        this.width = width; // get width
        this.height = height; // get height
    }
    //Create content for main menu here.
    public Parent createContent(){
        ////////////////////////////////////////////////////////////
        /////////////creating buttons and its holders///////////////
        ////////////////////////////////////////////////////////////
        Rectangle[] buttons = new Rectangle[5]; // will hold start/howToPlay/setting/credits/quit buttons
        Rectangle[] settingButtons = new Rectangle[5]; // will hold videoResolution/fullScreen/sound/changeShip/goBack buttons
        ImagePattern[] buttonImages = new ImagePattern[buttons.length * 2];// Image patterns to store asset information, and make it easier to use dynamically.
        ImagePattern[] settingsButtonImages = new ImagePattern[settingButtons.length * 2]; // same purpose but for settings
        Rectangle background;
        Rectangle startGameButton;
        Rectangle howToPlayButton;
        Rectangle settingsButton;
        Rectangle creditsButton;
        Rectangle quitButton;
        Rectangle buttonHighlight; // for background of the buttons.
        Rectangle controlImage; // for how to play button
        //for settings buttons
        Rectangle videoResolutionButton;
        Rectangle fullScreenButton;
        Rectangle soundButton;
        Rectangle changeShipButton;
        Rectangle goBackButton;
        Rectangle settingsButtonHighlight; // for background of the buttons at settings.
        // set root width and height
        menuRoot.setPrefSize(width, height);
        ////////////////////////////////////////////////////////////
        //////////////setting the main menu buttons/////////////////
        ////////////////////////////////////////////////////////////
        // for main elements
        background = new Rectangle(width, height, Color.GRAY); // create rectangle to insert image.
        controlImage = new Rectangle(width, height, Color.GRAY);
        // set start game button
        startGameButton = new Rectangle(width/3.5, height/18, Color.PURPLE);
        startGameButton.setTranslateX(width/16);
        startGameButton.setTranslateY(height/3.5);
        // set howToPlayButton
        howToPlayButton = new Rectangle(width/3.5, height/18, Color.WHITE);
        howToPlayButton.setTranslateX(width/16);
        howToPlayButton.setTranslateY(height/2.8);
        // set settingsButton
        settingsButton = new Rectangle(width/3.5, height/18, Color.WHITE);
        settingsButton.setTranslateX(width/16);
        settingsButton.setTranslateY(height/2.8-((height/3.5)-(height/2.8)));
        // set creditsButton
        creditsButton = new Rectangle(width/3.5, height/18, Color.WHITE);
        creditsButton.setTranslateX(width/16);
        creditsButton.setTranslateY(height/2.8- (2 * ((height/3.5)-(height/2.8))));
        // set quitButton
        quitButton = new Rectangle(width/3.5, height/18, Color.WHITE);
        quitButton.setTranslateX(width/16);
        quitButton.setTranslateY(height/2.8- (3 * ((height/3.5)-(height/2.8))));
        // set buttons array to make life easier.
        buttons[0] = startGameButton;
        buttons[1] = howToPlayButton;
        buttons[2] = settingsButton;
        buttons[3] = creditsButton;
        buttons[4] = quitButton;
        // set backgroundButton for cool effect.
        buttonHighlight = new Rectangle(width/3.5, height/18,null);
        buttonHighlight.setTranslateX(width/16);
        buttonHighlight.setTranslateY(height/3.5);
        /////////////////////
        // for settings menu
        /////////////////////
        // set video resolution button
        videoResolutionButton = new Rectangle(width/3.5, height/18, Color.PURPLE);
        videoResolutionButton.setTranslateX(width/16);
        videoResolutionButton.setTranslateY(height/3.5);
        // set fullScreen
        fullScreenButton = new Rectangle(width/3.5, height/18, Color.WHITE);
        fullScreenButton.setTranslateX(width/16);
        fullScreenButton.setTranslateY(height/2.8);
        // set sound button
        soundButton = new Rectangle(width/3.5, height/18, Color.WHITE);
        soundButton.setTranslateX(width/16);
        soundButton.setTranslateY(height/2.8-((height/3.5)-(height/2.8)));
        // set change ship button
        changeShipButton = new Rectangle(width/3.5, height/18, Color.WHITE);
        changeShipButton.setTranslateX(width/16);
        changeShipButton.setTranslateY(height/2.8- (2 * ((height/3.5)-(height/2.8))));
        // set go back button
        goBackButton = new Rectangle(width/3.5, height/18, Color.WHITE);
        goBackButton.setTranslateX(width/16);
        goBackButton.setTranslateY(height/2.8- (3 * ((height/3.5)-(height/2.8))));
        // set buttons array to make life easier.
        settingButtons[0] = videoResolutionButton;
        settingButtons[1] = fullScreenButton;
        settingButtons[2] = soundButton;
        settingButtons[3] = changeShipButton;
        settingButtons[4] = goBackButton;
        // set button highlight for cool effect.
        settingsButtonHighlight = new Rectangle(width/3.5, height/18,null);
        settingsButtonHighlight.setTranslateX(width/16);
        settingsButtonHighlight.setTranslateY(height/3.5);
        ////////////////////////////////////////////////////////////
        ///////////////setting images for buttons///////////////////
        ////////////////////////////////////////////////////////////
        // for main menu
        insertImage(background, "Assets\\mainMenu\\Main_Menu.jpg", true );
        insertImage(buttonHighlight, "Assets\\mainMenu\\buttonHighlight.png", true);
        insertImage(controlImage, "Assets\\mainMenu\\controls.jpg", true);
        buttonImages[0] = insertImage(startGameButton ,"Assets\\mainMenu\\startButton_White.png", false);
        buttonImages[1] = insertImage(startGameButton ,"Assets\\mainMenu\\startButton_Black.png", true);
        buttonImages[2] = insertImage(howToPlayButton, "Assets\\mainMenu\\howToPlayButton_White.png", true);
        buttonImages[3] = insertImage(howToPlayButton, "Assets\\mainMenu\\howToPlayButton_Black.png", false);
        buttonImages[4] = insertImage(settingsButton ,"Assets\\mainMenu\\settingsButton_White.png", true);
        buttonImages[5] = insertImage(settingsButton ,"Assets\\mainMenu\\settingsButton_Black.png", false);
        buttonImages[6] = insertImage(creditsButton ,"Assets\\mainMenu\\creditsButton_White.png", true);
        buttonImages[7] = insertImage(creditsButton ,"Assets\\mainMenu\\creditsButton_Black.png", false);
        buttonImages[8] = insertImage(quitButton ,"Assets\\mainMenu\\quitButton_White.png", true);
        buttonImages[9] = insertImage(quitButton ,"Assets\\mainMenu\\quitButton_Black.png", false);
        // for settings menu
        insertImage(settingsButtonHighlight, "Assets\\mainMenu\\buttonHighlight.png", true);
        settingsButtonImages[0] = insertImage(videoResolutionButton ,"Assets\\mainMenu\\videoResolution_White.png", false);
        settingsButtonImages[1] = insertImage(videoResolutionButton ,"Assets\\mainMenu\\videoResolution_Black.png", true);
        settingsButtonImages[2] = insertImage(fullScreenButton, "Assets\\mainMenu\\fullscreenButton_White.png", true);
        settingsButtonImages[3] = insertImage(fullScreenButton, "Assets\\mainMenu\\fullScreenButton_Black.png", false);
        settingsButtonImages[4] = insertImage(soundButton ,"Assets\\mainMenu\\soundButton_White.png", true);
        settingsButtonImages[5] = insertImage(soundButton ,"Assets\\mainMenu\\soundButton_Black.png", false);
        settingsButtonImages[6] = insertImage(changeShipButton ,"Assets\\mainMenu\\changeShipButton_White.png", true);
        settingsButtonImages[7] = insertImage(changeShipButton ,"Assets\\mainMenu\\changeShipButton_Black.png", false);
        settingsButtonImages[8] = insertImage(goBackButton,"Assets\\mainMenu\\goBackButton_White.png", true);
        settingsButtonImages[9] = insertImage(goBackButton,"Assets\\mainMenu\\goBackButton_Black.png", false);
        ////////////////////////////////////////////////////////////
        //////////////add necessary elements to root////////////////
        ////////////////////////////////////////////////////////////
        menuRoot.getChildren().add(background);
        menuRoot.getChildren().add(buttonHighlight);
        menuRoot.getChildren().add(startGameButton);
        menuRoot.getChildren().add(howToPlayButton);
        menuRoot.getChildren().add(settingsButton);
        menuRoot.getChildren().add(creditsButton);
        menuRoot.getChildren().add(quitButton);
        menuRoot.getChildren().add(settingsButtonHighlight);
        menuRoot.getChildren().add(videoResolutionButton);
        menuRoot.getChildren().add(fullScreenButton);
        menuRoot.getChildren().add(soundButton);
        menuRoot.getChildren().add(changeShipButton);
        menuRoot.getChildren().add(goBackButton);
        // make settings invisible.
        goBackButton.setVisible(false);
        changeShipButton.setVisible(false);
        soundButton.setVisible(false);
        fullScreenButton.setVisible(false);
        videoResolutionButton.setVisible(false);
        settingsButtonHighlight.setVisible(false);



        // create the controller and return
        menuController = new MenuController(buttons, buttonImages,  buttonHighlight, controlImage, settingButtons, settingsButtonImages, settingsButtonHighlight);
        return menuRoot;
    }

    // update everything with the current input
    public void update(Game game)
    {
        menuController.update(game, menuRoot);
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
            if(fill)
                button.setFill(imagePattern);
            inputstream.close();
        } catch ( Exception e ) {
            try{
                // set background image
                FileInputStream inputstream = new FileInputStream(url.replace("\\","/"));
                Image image = new Image(inputstream);
                imagePattern = new ImagePattern(image);
                if(fill)
                    button.setFill(imagePattern);
                inputstream.close();
            } catch ( Exception e2) {
                System.out.println(e.toString());
                return null;
            }
        }
        return  imagePattern;
    }
    public boolean isFullscreen(){
        return menuController.isFullscreen();
    }

}
