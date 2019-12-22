package org.openjfx;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MainGame {
    Pane gameRoot;
    double width;
    double height;
    int level;
    int shipSelected;
    GameController gameController;
    Game game;

    MainGame(Pane root, double width, double height, Game game){
        this.gameRoot = root;
        this.width = width;
        this.height = height;
        this.game = game;
        gameController = new GameController(gameRoot, width, height, this);
    }
    public Parent createContent(){
        //create and set content and controller
        gameRoot.setMaxSize(width, height);
        gameController.setSelectShipNumber(shipSelected);
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

    public void backToMainMenu() {
        game.backToMainMenu();
    }
    public void setShipSelected ( int shipSelected ){
        this.shipSelected = shipSelected;
    }
    public int getShipSelected(){
        return shipSelected;
    }
}
