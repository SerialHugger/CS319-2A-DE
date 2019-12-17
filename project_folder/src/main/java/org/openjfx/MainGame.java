package org.openjfx;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MainGame {
    Pane gameRoot;
    double width;
    double height;
    int level;
    GameController gameController;

    MainGame(Pane root, double width, double height){
        this.gameRoot = root;
        this.width = width;
        this.height = height;
        gameController = new GameController(gameRoot , width, height);
    }
    public Parent createContent(){
        //create and set content and controller
        gameRoot.setMaxSize(width, height);
        gameController.createContent();
        return gameRoot;
    }
    public void update(Game game, int fps){
        gameController.updateGame(fps);
    }

    public void setButtonHandler(Scene scene) { gameController.setButtonHandler(scene);}

    public void updateInteraction() { gameController.updateInteraction(); }

    public int getLevel(){
        return gameController.getLevel();
    }
    public void setlevel(int levelNum){
        gameController.setLevel(levelNum);
    }
}
