package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GameController {
    private Scenery scenery;
    private Pane gameRoot;
    private GameComponentFactory gameComponentFactory;
    private ArrayList<GameComponent> gameComponents;
    private double width;
    private double height;
    private Player player;
    private int delayTimer = 0; // todo
    private int speed = 5; //todo
    boolean delay = false; //todo
    boolean toLeft = false;
    int level = 1;
    // BooleanProperties for smoother control on ui.
    private BooleanProperty[] keyInputs = new BooleanProperty[6];
    /*
    For key inputs
    index --- key
    0     --- w
    1     --- a
    2     --- s
    3     --- d
    4     --- enter
    5     --- space
     */
    GameController(Pane root, double width, double height) {
        this.gameRoot = root;
        this.width = width;
        this.height = height;
    }

    void createContent(){
        scenery = new Scenery(gameRoot, width,height);
        scenery.createContent();
        gameComponents = new ArrayList<>();
        gameComponentFactory = new GameComponentFactory(width,height, gameComponents);
        player = (Player)gameComponentFactory.createComponent("player");
        player.addShapes(gameRoot);
        createLevel(level);
        // create booleans for key inputs
    }

    void update(){
        // update scenery
        scenery.update(keyInputs[1].get(), player);
        // update game components
        for(int i = 0; i < gameComponents.size(); i++){
            if(gameComponents.get(i) instanceof Player) {
                ((Player) gameComponents.get(i)).update(keyInputs,gameComponentFactory, gameRoot);
            } else if(gameComponents.get(i) instanceof PlayerBullet) {
                ((PlayerBullet) gameComponents.get(i)).update();
            } else if (gameComponents.get(i) instanceof  EnemyType1) {
                ((EnemyType1) gameComponents.get(i)).update(gameComponentFactory, gameRoot, player, keyInputs[1].get());
            } else if (gameComponents.get(i) instanceof  EnemyBulletType1) {
                ((EnemyBulletType1) gameComponents.get(i)).update();
            }
        }
        // update root
        if(keyInputs[3].get()) {
            if(toLeft) {
                double newX = gameRoot.getTranslateX()-(width-4*player.getWidth()); // limit x
                gameRoot.setTranslateX(newX); // limit
            } else
                gameRoot.setTranslateX(gameRoot.getTranslateX() - 25);
            toLeft = false;
        }
        if(keyInputs[1].get()) {
            if(!toLeft) {
                double newX = gameRoot.getTranslateX()+(width-4*player.getWidth()); // limit x
                gameRoot.setTranslateX(newX); // limit
            } else
                gameRoot.setTranslateX(gameRoot.getTranslateX() + 25);
            toLeft = true;
        }
        if(keyInputs[4].get())
            delay = true;
//        if(delay) {
//            delayTimer += 15;
//            if (delayTimer % 15 == 0){
//                gameRoot.setTranslateX(gameRoot.getTranslateX() - speed);
//                if(delayTimer > 2000 && speed > 300)
//                    speed -= 30;
//                else if(delayTimer > 2200 && speed > 100)
//                    speed -= 10;
//                else if (speed < 200)
//                    speed += 5;
//                else if(speed < 700)
//                    speed += 60;
//            }
//            if(delayTimer == 3000) {
//                delay = false;
//                speed = 60;
//                delayTimer = 0;
//            }
//        }
    }

    public void createLevel(int lvll){
        for(int i = 0; i < 10; i ++){
            System.out.println("enemy created");
            EnemyType1 eT1 =  (EnemyType1)gameComponentFactory.createComponent("enemyType1");
            eT1.addShapes(gameRoot);
        }
    }

    public void setButtonHandler(Scene scene) {
        for(int i = 0 ; i < keyInputs.length; i++)
            keyInputs[i] = new SimpleBooleanProperty();
        scene.setOnKeyPressed(e -> {
            if ((e.getCode() == KeyCode.W) || (e.getCode() == KeyCode.UP)) {
                keyInputs[0].set(true);
            }
            if ((e.getCode() == KeyCode.S)  || (e.getCode() == KeyCode.DOWN)) {
                keyInputs[2].set(true);
            }
            if ((e.getCode() == KeyCode.A)  || (e.getCode() == KeyCode.LEFT)) {
                keyInputs[1].set(true);
            }
            if ((e.getCode() == KeyCode.D)  || (e.getCode() == KeyCode.RIGHT)) {
                keyInputs[3].set(true);
            }
            if (e.getCode() == KeyCode.ENTER) {
                keyInputs[4].set(true);
            }
            if(e.getCode() == KeyCode.SPACE){
                keyInputs[5].set(true);
            }
        });
        scene.setOnKeyReleased(e -> {
            if ((e.getCode() == KeyCode.W) || (e.getCode() == KeyCode.UP)) {
                keyInputs[0].set(false);
            }
            if ((e.getCode() == KeyCode.S)  || (e.getCode() == KeyCode.DOWN)) {
                keyInputs[2].set(false);
            }
            if ((e.getCode() == KeyCode.A)  || (e.getCode() == KeyCode.LEFT)) {
                keyInputs[1].set(false);
            }
            if ((e.getCode() == KeyCode.D)  || (e.getCode() == KeyCode.RIGHT)) {
                keyInputs[3].set(false);
            }
            if (e.getCode() == KeyCode.ENTER) {
                keyInputs[4].set(false);
            }
            if(e.getCode() == KeyCode.SPACE){
                keyInputs[5].set(false);
            }
        });
    }
}
