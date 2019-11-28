package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GameController {
    private Scenery scenery; // scenery of the game
    private Pane gameRoot; // root of the game
    private GameComponentFactory gameComponentFactory; // Factory for components
    private ArrayList<GameComponent> gameComponents; // array list for updating components
    private InteractionHandler interactionHandler; // interaction handler for controller
    private double width; // width of the game
    private double height; // height of the game
    private Player player; // player variable for easier access
    private int delayTimer = 0; // todo
    boolean delay = false; //todo
    private double speed = 15; // similar to players speed
    // Necessary attiributes for changing directions with the ship
    boolean toLeft = false; // if facing left true else false. Starts with false;
    boolean startSlidingLeft = false; // slides background to left
    boolean startSlidingRight = false; // slides background to right
    double slidingLimit; // sliding limit for bacground
    double slidingCounter; // sliding counter for background
    double slidingSpeed; // sliding speed for background
    private int counter = 0;
    private int counter_interaction = 0;
    // level counter
    int level = 1;
    // BooleanProperties for smoother control on ui.
    private BooleanProperty[] keyInputs = new BooleanProperty[6];

    /*
    For key inputs
    index — key
    0 — w
    1 — a
    2 — s
    3 — d
    4 — enter
    5 — space
    6 — q
    7 — e
    8 — y
    9 — u
    10 — h
    11 — j
    12 — k
     */
    GameController(Pane root, double width, double height) {
        this.gameRoot = root;
        this.width = width;
        this.height = height;
    }

    void createContent() {
        speed = width / 128; // If width = 1920 then speed = 15.
        scenery = new Scenery(gameRoot, width, height, speed); // first create scenery
        scenery.createContent(); // create its content
        gameComponents = new ArrayList<>(); // create arraylist for gameComponents
        gameComponentFactory = new GameComponentFactory(width, height, gameComponents); // Initiate factory
        player = (Player) gameComponentFactory.createComponent("player"); // first game component is Player
        player.setSpeed(speed); // set speed for player.
        player.addShapes(gameRoot); // add player to root
        interactionHandler = new InteractionHandler(gameComponents, gameComponentFactory, gameRoot);
        gameRoot.setTranslateX(width); // set starting camera
        createLevel(level); // create the level with enemies
        slidingLimit = width - player.getWidth() * 4;
        slidingCounter = slidingLimit * -1;
        slidingSpeed = (width - player.getWidth() * 4) / 66;
        scenery.setSliding(slidingLimit, slidingCounter, slidingSpeed);
    }
    void updateInteraction(){
        //update interaction
        interactionHandler.update();
        counter_interaction++;
    }
    void update(int fps) {
        counter++;
        // update scenery
        scenery.update(keyInputs, player, fps);
//        //update interaction
//        interactionHandler.update();
        // update game components
        int size = gameComponents.size();
        for (int i = 0; i < size; i++) { // for every component in gameComponents.
            if (gameComponents.get(i) instanceof Player) { // if its an instance class of Player.
                ((Player) gameComponents.get(i)).update(keyInputs, gameComponentFactory, gameRoot); // update it.
                if (player.dead) { // if player is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    player.die(); // kill it, remove it from root.
                }
            } else if (gameComponents.get(i) instanceof PlayerBullet) { // else if its an instance class of PlayerBullet.
                PlayerBullet playerBullet = (PlayerBullet) gameComponents.get(i); // cast it to a temporary variable.
                playerBullet.update(player); // update it.
                // If its outside of the current camera/root location.
                if (playerBullet.getX() > (gameRoot.getTranslateX() * -1) + width + playerBullet.width ||
                        playerBullet.getX() < (gameRoot.getTranslateX() * -1) - playerBullet.width) {
                    gameComponents.remove(i--); // remove it from gameComponents.
                    size -= 1; // decrease size.
                    playerBullet.die(); // kill it, remove it from root.
                } else if (playerBullet.dead) {
                    gameComponents.remove(i--); // remove it from gameComponents.
                    size -= 1; // decrease size.
                    playerBullet.die(); // kill it, remove it from root.
                }
            } else if (gameComponents.get(i) instanceof EnemyType1) { // else if its an instance class of EmenyType1.
                EnemyType1 enemyType1 = ((EnemyType1) gameComponents.get(i));
                enemyType1.update(gameComponentFactory, gameRoot, player, keyInputs[1].get()); // update it.
                if (enemyType1.dead) { // if enemyType1 is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    enemyType1.die(); // kill it, remove it from root.
                }
            } else if (gameComponents.get(i) instanceof EnemyType2) { // else if its an instance class of EmenyType1.
                EnemyType2 enemyType2 = ((EnemyType2) gameComponents.get(i));
                enemyType2.update(gameComponentFactory, gameRoot, player, keyInputs[1].get()); // update it.
                if (enemyType2.dead) { // if enemyType1 is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    enemyType2.die(); // kill it, remove it from root.
                }
            } else if (gameComponents.get(i) instanceof EnemyType3) { // else if its an instance class of EmenyType3.
                EnemyType3 enemyType3 = ((EnemyType3) gameComponents.get(i));
                enemyType3.update(gameComponentFactory, gameRoot, player, keyInputs[1].get()); // update it.
                if (enemyType3.dead) { // if enemyType1 is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    enemyType3.die(); // kill it, remove it from root.
                }
            } else if (gameComponents.get(i) instanceof EnemyBulletType1) { // else if its an instance class of EnemyBulletType1.
                EnemyBulletType1 enemyBulletType1 = (EnemyBulletType1) gameComponents.get(i); // cast it to a temporary variable.
                enemyBulletType1.update(); // update it.
                // if its not in the boundaries of camera/root remove it.
                // first check for X then check for Y.
                if (enemyBulletType1.getX() > (gameRoot.getTranslateX() * -1) + width + enemyBulletType1.width || enemyBulletType1.getX() < (gameRoot.getTranslateX() * -1) + enemyBulletType1.width) {
                    gameComponents.remove(i--); // remove it from components and decrease i.
                    size -= 1; // decrease size.
                    enemyBulletType1.die(); // kill it, remove it from root.
                } else if (enemyBulletType1.getY() >= gameRoot.getHeight() + enemyBulletType1.width || enemyBulletType1.getY() < 0 - enemyBulletType1.width) {
                    gameComponents.remove(i--); // remove it from components and decrease i.
                    size -= 1; // decrease size.
                    enemyBulletType1.die(); // kill it, remove it from root.
                }
                if (enemyBulletType1.dead) {
                    gameComponents.remove(i--);
                    size -= 1;
                    enemyBulletType1.die();
                }
            }
        }
        // update root
        if (keyInputs[3].get()) { // if the key D pressed
            if (toLeft) { // if it was toLeft, change camera and bring it to limit x.
                startSlidingLeft = true;
                startSlidingRight = false;
                toLeft = false;
            } else // if it was already not toLeft, just move it.
                gameRoot.setTranslateX(gameRoot.getTranslateX() - speed);

        }
        if (keyInputs[1].get()) { // if the key A pressed
            if (!toLeft) { // if it was not toLeft, change camera and bring it to limit x.
                startSlidingRight = true;
                startSlidingLeft = false;
                toLeft = true;
            } else // if it was already to left, just move it
                gameRoot.setTranslateX(gameRoot.getTranslateX() + speed);
        }
        if (keyInputs[4].get()) { // if enter is pressed.
            //todo
        }
        if (startSlidingLeft) { // if the background sliding left
            if (slidingLimit * -1 != slidingCounter) {// until sliding limit is reached
                gameRoot.setTranslateX(gameRoot.getTranslateX() - slidingSpeed); // change background with sliding speed
                slidingCounter -= slidingSpeed;
            } else {
                startSlidingLeft = false; // finish the execution when the limit is reached.
            }
        }
        if (startSlidingRight) { // if the background sliding right
            if (slidingCounter != 0) {// until counter hits the 0
                gameRoot.setTranslateX(gameRoot.getTranslateX() + slidingSpeed); // change background with sliding speed
                slidingCounter += slidingSpeed;
            } else {
                startSlidingRight = false; // finish the execution when the limit is reached.
            }
        }
    }

    /*
     * This creates levels
     * adds enemies
     * todo
     */
    public void createLevel(int lvl) {
        for (int i = 0; i < 7; i++) {
            EnemyType1 eT1 = (EnemyType1) gameComponentFactory.createComponent("enemyType1");
            eT1.addShapes(gameRoot);
        }

        for (int i = 0; i < 3; i++) {
            EnemyType2 eT2 = (EnemyType2) gameComponentFactory.createComponent("enemyType2");
            eT2.addShapes(gameRoot);
        }

        for (int i = 0; i < 2; i++) {
            EnemyType3 eT3 = (EnemyType3) gameComponentFactory.createComponent("enemyType3");
            eT3.addShapes(gameRoot);
        }

    }

    // Sets buttons to play
    public void setButtonHandler(Scene scene) {
        for (int i = 0; i < keyInputs.length; i++)
            keyInputs[i] = new SimpleBooleanProperty();
        scene.setOnKeyPressed(e -> {
            if ((e.getCode() == KeyCode.W) || (e.getCode() == KeyCode.UP)) {
                keyInputs[0].set(true);
            }
            if ((e.getCode() == KeyCode.S) || (e.getCode() == KeyCode.DOWN)) {
                keyInputs[2].set(true);
            }
            if ((e.getCode() == KeyCode.A) || (e.getCode() == KeyCode.LEFT)) {
                keyInputs[1].set(true);
            }
            if ((e.getCode() == KeyCode.D) || (e.getCode() == KeyCode.RIGHT)) {
                keyInputs[3].set(true);
            }
            if (e.getCode() == KeyCode.ENTER) {
                keyInputs[4].set(true);
            }
            if (e.getCode() == KeyCode.SPACE) {
                keyInputs[5].set(true);
            }
        });
        scene.setOnKeyReleased(e -> {
            if ((e.getCode() == KeyCode.W) || (e.getCode() == KeyCode.UP)) {
                keyInputs[0].set(false);
            }
            if ((e.getCode() == KeyCode.S) || (e.getCode() == KeyCode.DOWN)) {
                keyInputs[2].set(false);
            }
            if ((e.getCode() == KeyCode.A) || (e.getCode() == KeyCode.LEFT)) {
                keyInputs[1].set(false);
            }
            if ((e.getCode() == KeyCode.D) || (e.getCode() == KeyCode.RIGHT)) {
                keyInputs[3].set(false);
            }
            if (e.getCode() == KeyCode.ENTER) {
                keyInputs[4].set(false);
            }
            if (e.getCode() == KeyCode.SPACE) {
                keyInputs[5].set(false);
            }
        });
    }
}
