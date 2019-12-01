package org.openjfx;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MainGame {
    Pane gameRoot;
    double width;
    double height;
    GameController gameController;

    MainGame(Pane root, double width, double height){
        this.gameRoot = root;
        this.width = width;
        this.height = height;
    }
    public Parent createContent(){
        //create and set content and controller
        gameRoot.setMaxSize(width, height);
        gameController = new GameController(gameRoot , width, height);
        gameController.createContent();
        return gameRoot;
    }
    public void update(Game game, int fps){
        gameController.updateGame(fps);
    }

    public void setButtonHandler(Scene scene) { gameController.setButtonHandler(scene);}

    public void updateInteraction(Game game) { gameController.updateInteraction(); }
}
