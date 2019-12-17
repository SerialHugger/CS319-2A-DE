package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;


public class MenuController {
    // necessary buttons.
    private Rectangle buttonHighlight;
    private Rectangle settingButtonHighlight;
    private Rectangle controlImage;
    private Rectangle[] buttons; // will hold buttons
    private Rectangle[] settingButtons; // will hold buttons for settings
    // Image patterns to store asset information, and make it easier to use dynamically.
    private ImagePattern[] buttonImages; // will hold start white-black, setting white-black, quit white-black
    private ImagePattern[] settingsButtonImages; // todo will hold  images for settings

    // BooleanProperties for smoother control on ui.
    private BooleanProperty w_Pressed = new SimpleBooleanProperty();
    private BooleanProperty a_Pressed = new SimpleBooleanProperty();
    private BooleanProperty s_Pressed = new SimpleBooleanProperty();
    private BooleanProperty d_Pressed = new SimpleBooleanProperty();
    private BooleanProperty enter_Pressed = new SimpleBooleanProperty();

    private int currentButton = 0; // 0 -> Start 1 -> How to play 2 -> Settings 3 -> Credits 4 -> Quit
    private int currentButtonSettings = 0; // 0 -> Video Resolution 1 -> Fullscreen 2-> Sound 3 -> Change Ship 4-> Go back
    private int currentScreen = 0; // 0 -> Main, 1 -> HowtoPlay, 2 -> Settings, 3-> Credits
    private boolean delay = true; // delay to main menu so user can use it hehe :)
    private int delayTimer = 0; // delay timer to adjust delay.
    private int whatImage = 1; // just to make it more easier to code, this will decide the image numbers.
    private int whatImageSettings = 1; // Same purpose but for setting
    private int fullscreen = 1; // check the fullscreen 1 = true, 0 = false


    private MediaPlayer mediaPlayer;


    MenuController (Rectangle[] buttons, ImagePattern[] buttonImages, Rectangle buttonHighlight, Rectangle controlImage, Rectangle[] settingButtons, ImagePattern[] settingsButtonImages, Rectangle settingButtonHighlight){
        this.buttons = buttons;
        this.buttonImages = buttonImages;
        this.buttonHighlight = buttonHighlight;
        this.controlImage = controlImage;
        this.settingButtons = settingButtons;
        this.settingsButtonImages = settingsButtonImages;
        this.settingButtonHighlight = settingButtonHighlight;

        String mainMenuMusicUrl = new File("Assets/Music/destiny2_Journey.mp3").toURI().toString();
        mediaPlayer = new MediaPlayer( new Media(mainMenuMusicUrl));
        mediaPlayer.play();

    }
    public void update(Game game, Pane root) {

        // initiate delay timer.
        if(!delay) {
            delayTimer += 10;
            if(delayTimer == 100){
                delay = true;
                delayTimer = 0;
            }
        }
        if(delay){ //delay here for better control of the menu
            if(currentScreen == 0){
                if (s_Pressed.get()) { // if the down button pressed change the current position of buttons +1.
                    if(delay) {
                        updateButtons(buttons[currentButton], buttons[(currentButton + 1) % buttons.length], true);
                        currentButton = (currentButton + 1) % buttons.length;
                        delay = false;
                    }
                }
                if (w_Pressed.get()) { // if the up button pressed change the current position of buttons by -1.
                    int temp = 0;
                    if (currentButton == 0)
                        temp = buttons.length-1;
                    else
                        temp = (currentButton - 1) % buttons.length;
                    updateButtons(buttons[currentButton], buttons[temp], false);
                    currentButton = temp;
                    delay = false;
                }
                if(enter_Pressed.get()) {
                    if(currentButton == 0) { // Start Game
                        System.out.println("Start Game"); // todo call startGame method of Game <-- first create that method hehe
                        mediaPlayer.stop();
                        game.startGame();
                    }
                    else if(currentButton == 1) { // How To Play
                        currentScreen = 1;
                        root.getChildren().add(controlImage);
                        controlImage.setVisible(true);
                    }
                    else if(currentButton == 2) { // Settings
                        currentScreen = 2;
                        System.out.println("Open Settings"); // todo create settings menu here <-- decide how it will look like
                        for (int i = 0; i < buttons.length; i++){
                            buttons[i].setVisible(false);
                        }
                        buttonHighlight.setVisible(false);
                        settingButtonHighlight.setVisible(true);
                        for (int i = 0; i < settingButtons.length; i++){
                            settingButtons[i].setVisible(true);
                        }

                    }
                    else if(currentButton == 3) { // Credits
                        System.out.println("Open Credits");
                    }
                    else if(currentButton == 4) { // Quit
                        System.exit(0); // this quits the application.
                    }
                    delay = false;
                }
            } else if (currentScreen == 1) {
                if(enter_Pressed.get()) {
                    root.getChildren().remove(controlImage);
                    controlImage.setVisible(false);
                    currentScreen = 0;
                    delay = false;
                }
            } else if ( currentScreen == 2) {
                if (s_Pressed.get()) { // if the down button pressed change the current position of buttons +1.
                    if(delay) {
                        settingUpdateButtons(settingButtons[currentButtonSettings], settingButtons[(currentButtonSettings + 1) % settingButtons.length], true);
                        currentButtonSettings = (currentButtonSettings + 1) % settingButtons.length;
                        delay = false;
                    }
                }
                if (w_Pressed.get()) { // if the up button pressed change the current position of buttons by -1.
                    int temp = 0;
                    if (currentButtonSettings == 0)
                        temp = settingButtons.length - 1;
                    else
                        temp = (currentButtonSettings - 1) % settingButtons.length;
                    settingUpdateButtons(settingButtons[currentButtonSettings], settingButtons[temp], false);
                    currentButtonSettings = temp;
                    delay = false;
                }
                if(enter_Pressed.get()){
                    if(currentButtonSettings == 0) { // Video Resolution
                        System.out.println("Start Game"); // todo call startGame method of Game <-- first create that method hehe
                    }
                    else if(currentButtonSettings == 1) { // Fullscreen
                        if(fullscreen == 1) {
                            game.setFullScreen(false);
                            fullscreen = 0;
                        } else {
                            game.setFullScreen(true);
                            fullscreen = 1;
                        }
                    }
                    else if(currentButtonSettings == 2) { // Settings
                        System.out.println("Open Settings"); // todo create settings menu here <-- decide how it will look like

                    }
                    else if(currentButtonSettings == 3) { // Credits
                        System.out.println("Open Credits");
                    }
                    else if(currentButtonSettings == 4) { // Go back
                        buttonHighlight.setVisible(true);
                        for (int i = 0; i < buttons.length; i++){
                            buttons[i].setVisible(true);
                        }
                        settingButtonHighlight.setVisible(false);
                        for (int i = 0; i < settingButtons.length; i++){
                            settingButtons[i].setVisible(false);
                        }
                        currentScreen = 0;
                    }
                    delay = false;
                }
            }
        }
    }

    /*
     * changes the properties of current with old.
     * uses buttonHighlight
     */
    private void updateButtons(Rectangle old, Rectangle current, boolean fromUp) {
        old.setFill(buttonImages[whatImage-1 % 10]); // make old white
        if(fromUp) {
            whatImage = (whatImage + 2) % buttonImages.length;
        } else {
            whatImage = (whatImage - 2) % buttonImages.length;
        }
        if(whatImage == -1)
            whatImage = buttonImages.length -1;
        current.setFill(buttonImages[whatImage]); // make current black
        buttonHighlight.setTranslateX(current.getTranslateX()); // adjust highlight X
        buttonHighlight.setTranslateY(current.getTranslateY()); // adjust highlight Y
    }

    /*
     * changes the properties of current with old for settings
     * uses settingButtonHighlight
     */
    private void settingUpdateButtons(Rectangle old, Rectangle current, boolean fromUp) {
        old.setFill(settingsButtonImages[whatImageSettings-1 % 10]); // make old white
        if(fromUp) {
            whatImageSettings = (whatImageSettings + 2) % settingsButtonImages.length;
        } else {
            whatImageSettings = (whatImageSettings - 2) % settingsButtonImages.length;
        }
        if(whatImageSettings == -1)
            whatImageSettings = settingsButtonImages.length -1;
        current.setFill(settingsButtonImages[whatImageSettings]); // make current black
        settingButtonHighlight.setTranslateX(current.getTranslateX()); // adjust highlight X
        settingButtonHighlight.setTranslateY(current.getTranslateY()); // adjust highlight Y
    }

    // returns the fullscreenity
    public boolean isFullscreen(){
        if(fullscreen == 1)
            return true;
        return false;
    }
    // Set buttons for give control over menu
    public void setButtonHandler(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if ((e.getCode() == KeyCode.W) || (e.getCode() == KeyCode.UP)) {
                w_Pressed.set(true);
            }
            if ((e.getCode() == KeyCode.S)  || (e.getCode() == KeyCode.DOWN)) {
                s_Pressed.set(true);
            }
            if ((e.getCode() == KeyCode.A)  || (e.getCode() == KeyCode.LEFT)) {
                a_Pressed.set(true);
            }
            if ((e.getCode() == KeyCode.D)  || (e.getCode() == KeyCode.RIGHT)) {
                d_Pressed.set(true);
            }
            if (e.getCode() == KeyCode.ENTER) {
                enter_Pressed.set(true);
            }
        });
        scene.setOnKeyReleased(e -> {
            if ((e.getCode() == KeyCode.W) || (e.getCode() == KeyCode.UP)) {
                w_Pressed.set(false);
            }
            if ((e.getCode() == KeyCode.S)  || (e.getCode() == KeyCode.DOWN)) {
                s_Pressed.set(false);
            }
            if ((e.getCode() == KeyCode.A)  || (e.getCode() == KeyCode.LEFT)) {
                a_Pressed.set(false);
            }
            if ((e.getCode() == KeyCode.D)  || (e.getCode() == KeyCode.RIGHT)) {
                d_Pressed.set(false);
            }
            if (e.getCode() == KeyCode.ENTER) {
                enter_Pressed.set(false);
            }
        });
    }
}
