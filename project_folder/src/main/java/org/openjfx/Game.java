package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Screen;
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

    private double width =  (int)Screen.getPrimary().getVisualBounds().getWidth(); // deafult screen width // adjust here manually for now
    private double height = (int)Screen.getPrimary().getVisualBounds().getHeight(); // default screen height // adjust here manually for now

    private final double UPDATE_CAP = 1.0/60.0; // fps limit is indicatied by 1/x, x is fps limit.
    double firstTime = 0; // hold the initial time.
    double lastTime = System.nanoTime() / 1000000000.0; // helps calculate fps
    double passedTime = 0; // helps calculate fps
    double unprocessedTime = 0; // hold unprocessed time of the game.
    boolean render = false; // render check
    double frameTime = 0; // hold the frameTime
    int frames = 0; // frame count
    int fps = 0; // fps count
    int shipSelected;
    private void update() {

        if(onMenu){ // if the current scene is menu
            mainMenu.update(this);
            shipSelected = mainMenu.getShipSelected();
        }
        if(onGame){
            // I probably miss commended this cuz reasons.
            render = false;
            firstTime = System.nanoTime() / 1000000000.0; // get time
            passedTime = firstTime - lastTime; // calculate passedTime
            lastTime = firstTime; // reset last time.
            unprocessedTime += passedTime; // calculate unprocessedTime
            frameTime += passedTime; // calculate frameTime
            shipSelected = mainMenu.getShipSelected();
            while(unprocessedTime >= UPDATE_CAP){ // if unprocessedTime is greater then UPDATE_CAP, intended frame.
                unprocessedTime -= UPDATE_CAP; // reset unprocecssedTime
                render = true; // make render true
                mainGame.updateInteraction(); // update Interaction/Game
                if(frameTime >= 1.0){ //keeping track of the frames
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }
            if(render){ // if the game updated Render it
                mainGame.update(this, fps); // render game.
                frames++;
            } else {
                try{
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
        gameRoot = new Pane();
        menuRoot.setVisible(false); // make menu invisible
        gameRoot.setVisible(true); // make game visible
        mainGame = new MainGame(gameRoot, width, height, this);
        mainGame.setShipSelected(mainMenu.getShipSelected());
        mainScene.setRoot(mainGame.createContent());
        mainGame.setButtonHandler(mainScene);
        mainScene.setCursor(Cursor.NONE);
        theStage.setScene(mainScene);
        theStage.setFullScreen(mainMenu.isFullscreen());
        theStage.show();
        // todo do stuff here yes hehe
        // todo make menu to game smoother
    }
    // start game from a level
    public void startGame(int level){
        onMenu = false; // set menu to no update
        onGame = true; // set game to update
        menuRoot.setVisible(false); // make menu invisible
        gameRoot.setVisible(true); // make game visible
        mainGame = new MainGame(gameRoot, width, height, this);
        mainGame.setlevel(level);
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
        theStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        theStage.setScene(mainScene); // set the stage to scene
        theStage.show(); // show it
    }

    public void setFullScreen(boolean fullScreen){
        theStage.setFullScreen(fullScreen);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public int getShipSelected(){
        shipSelected = mainMenu.getShipSelected();
        return shipSelected;
    }

    public void backToMainMenu() {
        onMenu = true; // set menu to no update
        onGame = false; // set game to update
        menuRoot.setVisible(true); // make menu invisible
        gameRoot.setVisible(false); // make game visible
        mainScene.setRoot(mainMenu.createContent());
        mainScene.setCursor(Cursor.NONE);
        mainMenu.setButtonHandler(mainScene);
        theStage.setScene(mainScene);
        theStage.setFullScreen(mainMenu.isFullscreen());
        theStage.show();
    }
}