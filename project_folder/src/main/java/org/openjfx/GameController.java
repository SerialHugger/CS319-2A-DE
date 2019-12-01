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
    private double speed = 15; // players speed
    private double maxSpeed = 25; // players maximum speed
    private double acceleration = 0; // screens acceleration todo add acceleration
    // Necessary attiributes for changing directions with the ship
    boolean toLeft = false; // if facing left true else false. Starts with false;
    boolean startSlidingLeft = false; // slides background to left
    boolean startSlidingRight = false; // slides background to right
    double slidingLimit; // sliding limit for bacground
    double slidingCounter; // sliding counter for background
    double slidingSpeed; // sliding speed for background
    // level counter
    int level = 1;
    // BooleanProperties for smoother control on ui.
    private BooleanProperty[] keyInputs = new BooleanProperty[14];

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
    10 — i
    11 — h
    12 — j
    13 — k
     */
    GameController(Pane root, double width, double height) {
        this.gameRoot = root;
        this.width = width;
        this.height = height;
    }

    void createContent() {
        speed = width / 384; // If width = 1920 then speed = 5.
        maxSpeed = width / 76.8; // If width = 1920 then maxSpeed = 25.
        scenery = new Scenery(gameRoot, width, height, speed); // first create scenery
        scenery.createContent(); // create its content
        gameComponents = new ArrayList<>(); // create arraylist for gameComponents
        gameComponentFactory = new GameComponentFactory(width, height, gameComponents); // Initiate factory
        player = (Player) gameComponentFactory.createComponent("player"); // first game component is Player
        player.speed = speed; // set speed for player.
        player.addShapes(gameRoot); // add player to root
        interactionHandler = new InteractionHandler();
        gameRoot.setTranslateX(width); // set starting camera
        createLevel(level); // create the level with enemies
        slidingLimit = width - player.getWidth() * 4;
        slidingCounter = slidingLimit * -1;
        slidingSpeed = (width - player.getWidth() * 4) / 66;
        scenery.setSliding(slidingLimit, slidingCounter, slidingSpeed);
    }
    void updateInteraction(){
        //update interaction
        interactionHandler.handleInteraction(gameRoot);
    }
    void updateGame(int fps) {
        // update scenery
        scenery.update(keyInputs, player, fps);
        // update game components
        int size = gameComponents.size();
        for (int i = 0; i < size; i++) { // for every component in gameComponents.
            if (gameComponents.get(i) instanceof Player) { // if its an instance class of Player.
                ((Player) gameComponents.get(i)).movePlayer(keyInputs, gameComponentFactory, gameRoot); // update it.
                if (player.dead) { // if player is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    player.die(); // kill it, remove it from root.
                }
            } else if (gameComponents.get(i) instanceof PlayerBullet) { // else if its an instance class of PlayerBullet.
                PlayerBullet playerBullet = (PlayerBullet) gameComponents.get(i); // cast it to a temporary variable.
                playerBullet.movePlayerBullet(player); // update it.
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
            } else if (gameComponents.get(i) instanceof Atlas) { // else if its an instance class of EmenyType1.
                Atlas atlas = ((Atlas) gameComponents.get(i));
                atlas.moveAtlas(gameComponentFactory, gameRoot, player, keyInputs[1].get()); // update it.
                if (atlas.dead) { // if enemyType1 is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    atlas.die(); // kill it, remove it from root.
                }
            } else if (gameComponents.get(i) instanceof Dodger) { // else if its an instance class of EmenyType1.
                Dodger dodger = ((Dodger) gameComponents.get(i));
                dodger.update(gameComponentFactory, gameRoot, player, keyInputs[1].get()); // update it.
                if (dodger.dead) { // if enemyType1 is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    dodger.die(); // kill it, remove it from root.
                }
            } else if (gameComponents.get(i) instanceof Dividus) { // else if its an instance class of EmenyType1.
                Dividus dividus = ((Dividus) gameComponents.get(i));
                dividus.update(gameComponentFactory, gameRoot, player, keyInputs[1].get()); // update it.
                if (dividus.dead) { // if enemyType1 is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    dividus.die(); // kill it, remove it from root.
                }
            } else if (gameComponents.get(i) instanceof Dienamite) { // else if its an instance class of EmenyType1.
                Dienamite dienamite = ((Dienamite) gameComponents.get(i));
                dienamite.update(gameComponentFactory, gameRoot, player, keyInputs[1].get()); // update it.
                if (dienamite.dead) { // if enemyType1 is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    dienamite.die(); // kill it, remove it from root.
                }
            } else if (gameComponents.get(i) instanceof laserBullet) { // else if its an instance class of EnemyBulletType1.
                laserBullet laserBullet = (laserBullet) gameComponents.get(i); // cast it to a temporary variable.
                laserBullet.updateLaserBullet(); // update it.
                // if its not in the boundaries of camera/root remove it.
                // first check for X then check for Y.
                if (laserBullet.getX() > (gameRoot.getTranslateX() * -1) + width + laserBullet.width || laserBullet.getX() < (gameRoot.getTranslateX() * -1) + laserBullet.width) {
                    gameComponents.remove(i--); // remove it from components and decrease i.
                    size -= 1; // decrease size.
                    laserBullet.die(); // kill it, remove it from root.
                } else if (laserBullet.getY() >= gameRoot.getHeight() + laserBullet.width || laserBullet.getY() < 0 - laserBullet.width) {
                    gameComponents.remove(i--); // remove it from components and decrease i.
                    size -= 1; // decrease size.
                    laserBullet.die(); // kill it, remove it from root.
                }
                if (laserBullet.dead) {
                    gameComponents.remove(i--);
                    size -= 1;
                    laserBullet.die();
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
        for (int i = 0; i < 5; i++) {
            Atlas atlas = (Atlas) gameComponentFactory.createComponent("atlas");
            atlas.addShapes(gameRoot);
        }

        for (int i = 0; i < 5; i++) {
            Dodger dodger = (Dodger) gameComponentFactory.createComponent("dodger");
            dodger.addShapes(gameRoot);
        }

        for (int i = 0; i < 5; i++) {
            Dividus dividus = (Dividus) gameComponentFactory.createComponent("dividus");
            dividus.addShapes(gameRoot);
        }

        for (int i = 0; i < 3; i++) {
            Dienamite dienamite = (Dienamite) gameComponentFactory.createComponent("dienamite");
            dienamite.addShapes(gameRoot);
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
            if (e.getCode() == KeyCode.Q) {
                keyInputs[6].set(true);
            }
            if (e.getCode() == KeyCode.E) {
                keyInputs[7].set(true);
            }
            if (e.getCode() == KeyCode.Y) {
                keyInputs[8].set(true);
            }
            if (e.getCode() == KeyCode.U) {
                keyInputs[9].set(true);
            }
            if (e.getCode() == KeyCode.I) {
                keyInputs[10].set(true);
            }
            if (e.getCode() == KeyCode.H) {
                keyInputs[11].set(true);
            }
            if (e.getCode() == KeyCode.J) {
                keyInputs[12].set(true);
            }
            if (e.getCode() == KeyCode.K) {
                keyInputs[13].set(true);
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
            if (e.getCode() == KeyCode.Q) {
                keyInputs[6].set(false);
            }
            if (e.getCode() == KeyCode.E) {
                keyInputs[7].set(false);
            }
            if (e.getCode() == KeyCode.Y) {
                keyInputs[8].set(false);
            }
            if (e.getCode() == KeyCode.U) {
                keyInputs[9].set(false);
            }
            if (e.getCode() == KeyCode.I) {
                keyInputs[10].set(false);
            }
            if (e.getCode() == KeyCode.H) {
                keyInputs[11].set(false);
            }
            if (e.getCode() == KeyCode.J) {
                keyInputs[12].set(false);
            }
            if (e.getCode() == KeyCode.K) {
                keyInputs[13].set(false);
            }
        });
    }
}
