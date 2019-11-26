package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * JavaFX App
 * Class name: Game
 */
public class Game extends Application {

    private Stage theStage;
    private Pane menuRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Scene menuScene;
    private Scene mainScene;
    private Scene gameScene;
    private MainMenu mainMenu;
    private MainGame mainGame;
    private boolean onMenu = true;
    private boolean onGame = false;

    private double width =  1920; // (int)Screen.getPrimary().getVisualBounds().getWidth(); // deafult screen width // adjust here manually for now
    private double height = 1080; // (int)Screen.getPrimary().getVisualBounds().getHeight(); // default screen height // adjust here manually for now

    private void update() {

        if(onMenu){ // if the current scene is menu
            mainMenu.update(this);
        }
        if(onGame){
            mainGame.update(this);
        }
    }

    private void startTimer(){
        // timer create
        AnimationTimer timer =  new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();
        //timer start
    }

    public void startGame(){
        onMenu = false; // set menu to no update
        onGame = true; // set game to update
        menuRoot.setVisible(false); // make menu invisible
        gameRoot.setVisible(true); // make game visible
        mainGame = new MainGame(gameRoot, width, height);
        mainScene.setRoot(mainGame.createContent());
        mainGame.setButtonHandler(mainScene);
        mainScene.setCursor(Cursor.NONE);
        theStage.setScene(mainScene);
        theStage.setFullScreen(mainMenu.isFullscreen());
        theStage.show();
        // todo do stuff here yes hehe
        // todo make menu to game smoother
    }

    @Override
    public void start(Stage theStage) throws Exception{
        //Start the Game here.
        this.theStage = theStage;
        mainMenu = new MainMenu(menuRoot, width, height); // Create MainMenu
        startTimer();
        menuScene = new Scene(mainMenu.createContent()); // create scene with MainMenu
        mainMenu.setButtonHandler(menuScene);
        mainScene = menuScene;
        mainScene.setCursor(Cursor.NONE);
        theStage.setFullScreen(true);
        //theStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        theStage.setScene(mainScene); // set the stage to scene
        theStage.show(); // show it
    }

    public void setFullScreen(boolean fullScreen){
        theStage.setFullScreen(fullScreen);
    }

    public static void main(String[] args) {
        launch(args);
    }


}