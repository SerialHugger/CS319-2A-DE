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
    private double acceleration = 0.3; // screens acceleration todo add acceleration
    // Necessary attiributes for changing directions with the ship
    boolean toLeft = true; // if facing left true else false. Starts with false;
    boolean startSlidingLeft = false; // slides background to left
    boolean startSlidingRight = false; // slides background to right
    double slidingLimit; // sliding limit for bacground
    double slidingCounter; // sliding counter for background
    double slidingSpeed; // sliding speed for background
    // level counter
    int level = 1;
    int deadCounter = 0;
    long score = 0;
    int noOfEnemies = 0;
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
    8 — h
    9 — j
    10 — k
    11 — l
     */
    GameController(Pane root, double width, double height) {
        this.gameRoot = root;
        this.width = width;
        this.height = height;
    }

    void createContent() {
        speed = 0;
        maxSpeed = magicConverter(25); // If width = 1920 then maxSpeed = 25.
        acceleration = magicConverter(0.3);
        scenery = new Scenery(gameRoot, width, height, speed); // first create scenery
        scenery.createContent(); // create its content
        gameComponents = new ArrayList<>(); // create arraylist for gameComponents
        gameComponentFactory = new GameComponentFactory(width, height, gameComponents); // Initiate factory
        player = (Player) gameComponentFactory.createComponent("player"); // first game component is Player
        player.speed = speed; // set speed for player.
        player.addShapes(gameRoot); // add player to root
        interactionHandler = new InteractionHandler();
        gameRoot.setTranslateX(width); // set starting camera
        //createLevel(level); // create the level with enemies // blo
        slidingLimit = width - player.getWidth() * 4;
        slidingCounter = slidingLimit * -1;
        slidingSpeed = (width - player.getWidth() * 4) / 66; // some numbers yes.
    }
    void updateInteraction(){
        //update interaction
        interactionHandler.handleInteraction(gameRoot);
    }
    void updateGame(int fps) {
        // update game components
        int size = gameComponents.size();
        for (int i = 0; i < size; i++) { // for every component in gameComponents.
            if (gameComponents.get(i) instanceof Player) { // if its an instance class of Player.
                ((Player) gameComponents.get(i)).movePlayer(keyInputs, gameComponentFactory); // update it.
                if (player.dead) { // if player is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    player.die(); // kill it, remove it from root.
                }
            } else if (gameComponents.get(i) instanceof PlayerBullet) { // else if its an instance class of PlayerBullet.
                PlayerBullet playerBullet = (PlayerBullet) gameComponents.get(i); // cast it to a temporary variable.
                playerBullet.movePlayerBullet(); // update it.
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
                    deadCounter++;
                    score = score + 100;
                }
            } else if (gameComponents.get(i) instanceof Dodger) { // else if its an instance class of EmenyType1.
                Dodger dodger = ((Dodger) gameComponents.get(i));
                dodger.update(gameComponentFactory, gameRoot, player, keyInputs[1].get()); // update it.
                if (dodger.dead) { // if enemyType1 is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    dodger.die(); // kill it, remove it from root.
                    deadCounter++;
                    score = score + 100;
                }
            } else if (gameComponents.get(i) instanceof Dividus) { // else if its an instance class of EmenyType1.
                Dividus dividus = ((Dividus) gameComponents.get(i));
                dividus.update(gameComponentFactory, gameRoot, player, keyInputs[1].get()); // update it.
                if (dividus.dead) { // if enemyType1 is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    dividus.die(); // kill it, remove it from root.
                    deadCounter++;
                    score = score + 100;
                }
            } else if (gameComponents.get(i) instanceof Dienamite) { // else if its an instance class of EmenyType1.
                Dienamite dienamite = ((Dienamite) gameComponents.get(i));
                dienamite.update(gameComponentFactory, gameRoot, player, keyInputs[1].get()); // update it.
                if (dienamite.dead) { // if enemyType1 is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    dienamite.die(); // kill it, remove it from root.
                    deadCounter++;
                    score = score + 100;
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
            } else if (gameComponents.get(i) instanceof GuidedBullet) { // else if its an instance class of GuidedBullet
                GuidedBullet guidedBullet = (GuidedBullet) gameComponents.get(i); // cast it to a temporary variable.
                guidedBullet.moveGuidedBullet(player); // update it.
                // if its not in the boundaries of camera/root remove it.
                // first check for X then check for Y.
                if (guidedBullet.getX() > (gameRoot.getTranslateX() * -1) + width + guidedBullet.width || guidedBullet.getX() < (gameRoot.getTranslateX() * -1) + guidedBullet.width) {
                    gameComponents.remove(i--); // remove it from components and decrease i.
                    size -= 1; // decrease size.
                    guidedBullet.die(); // kill it, remove it from root.
                } else if (guidedBullet.getY() >= gameRoot.getHeight() + guidedBullet.width || guidedBullet.getY() < 0 - guidedBullet.width) {
                    gameComponents.remove(i--); // remove it from components and decrease i.
                    size -= 1; // decrease size.
                    guidedBullet.die(); // kill it, remove it from root.
                }
                if (guidedBullet.dead) {
                    gameComponents.remove(i--);
                    size -= 1;
                    guidedBullet.die();
                }
            }
            else if (gameComponents.get(i) instanceof SpeedRunner) { // else if its an instance class of EmenyType1.
                SpeedRunner speedRunner = ((SpeedRunner) gameComponents.get(i));
                speedRunner.moveSpeedRunner(gameComponentFactory, gameRoot, player, keyInputs[1].get()); // update it.
                if (speedRunner.dead) { // if enemyType1 is dead.
                    gameComponents.remove(i--); // remove it from components.
                    size -= 1; // decrease size.
                    speedRunner.die(); // kill it, remove it from root.
                    deadCounter++;
                    score = score + 100;
                }
            }
           createLevel();
        }
        // update root
        if (keyInputs[3].get()) { // if the key D pressed
            //handle the acceleration with scenery!
            if(speed < maxSpeed)
                speed += acceleration;
            if(speed < 0)
                speed += acceleration;
            //check for sliding
            if (!toLeft) { // if it was not toLeft, change camera and bring it to limit x.
                startSlidingRight = true;
                startSlidingLeft = false;
                toLeft = true;
            }
        }
        if (keyInputs[1].get()) { // if the key A pressed
            //handle the acceleration with scenery!
            if(speed > -1*maxSpeed)
                speed -= acceleration;
            if(speed > 0)
                speed -= acceleration;
            //check for sliding
            if (toLeft) { // if it was toLeft, change camera and bring it to limit x.
                startSlidingLeft = true;
                startSlidingRight = false;
                toLeft = false;
            }
        }
        if (keyInputs[4].get()) { // if enter is pressed.
            //todo
        }
        if (startSlidingLeft) { // if the background sliding left
//            (player.getX() - player.getWidth()*1.5) > gameRoot.getTranslateX()*-1
            if (true){
                if (slidingCounter != 0) {// until sliding limit is reached or hits the player to screen limit
                    gameRoot.setTranslateX(gameRoot.getTranslateX() + slidingSpeed); // change background with sliding speed
                    scenery.slideScenery(true, slidingSpeed);
                    slidingCounter += slidingSpeed;
                } else {
                    startSlidingLeft = false; // finish the execution when the limit is reached.
                }
            }
        }
        if (startSlidingRight) { // if the background sliding right
//            (player.getX() + player.getWidth() * 2.5) > (gameRoot.getTranslateX() + width) * -1
            if(true)
            {
                if (slidingLimit * -1 != slidingCounter) {// until counter hits the 0
                gameRoot.setTranslateX(gameRoot.getTranslateX() - slidingSpeed); // change background with sliding speed
                scenery.slideScenery(false,slidingSpeed);
                slidingCounter -= slidingSpeed;
                } else {
                    startSlidingRight = false; // finish the execution when the limit is reached.
                }
            }
        }
        if(!keyInputs[1].get() && !keyInputs[3].get()){ // if the movement keys not pressed
            //handle the acceleration
            if(speed > 0) {
                speed -= acceleration;
                if (speed < 0)
                    speed = 0;
            } else {
                speed += acceleration;
                if(speed > 0)
                    speed = 0;
            }
        }
        gameRoot.setTranslateX(gameRoot.getTranslateX() - speed);
        // update scenery
        scenery.update(keyInputs, player, fps, speed); // todo fix background speed etc.
    }


    public int createEnemies( int atlasNumber , int dodgernumber , int dividusNumber , int dienamiteNumber , int speedRunnerNumber ){
        for (int i = 0; i < atlasNumber; i++) {
            Atlas atlas = (Atlas) gameComponentFactory.createComponent("atlas");
            atlas.addShapes(gameRoot);
        }

        for (int i = 0; i < dodgernumber; i++) {
            Dodger dodger = (Dodger) gameComponentFactory.createComponent("dodger");
            dodger.addShapes(gameRoot);
        }

        for (int i = 0; i < dividusNumber; i++) {
            Dividus dividus = (Dividus) gameComponentFactory.createComponent("dividus");
            dividus.addShapes(gameRoot);
        }

        for (int i = 0; i < dienamiteNumber; i++) {
            Dienamite dienamite = (Dienamite) gameComponentFactory.createComponent("dienamite");
            dienamite.addShapes(gameRoot);
        }

        for (int i = 0; i < speedRunnerNumber; i++) {
            SpeedRunner speedRunner = (SpeedRunner) gameComponentFactory.createComponent("speedRunner");
            speedRunner.addShapes(gameRoot);
        }

        return (atlasNumber + dodgernumber + dividusNumber + dienamiteNumber + speedRunnerNumber);
    }
    /*
     * This creates levels
     * adds enemies
     * todo make it more complex
     */
    public void createLevel() {
        if ( level == 1 ) {
            if ( noOfEnemies == 0 )
            noOfEnemies = createEnemies(3 ,3 ,3 ,3 ,3 );

            if( noOfEnemies == deadCounter ){
                System.out.println("Level1 cleared !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
                level = 2;
                deadCounter = 0;
                noOfEnemies = 0;
            }


        }
        else if ( level == 2 ){
            if ( noOfEnemies == 0)
            noOfEnemies = createEnemies(5 , 0 , 0 , 0 , 0);

            if( noOfEnemies == deadCounter ){
                level = 3;
                deadCounter = 0;
                noOfEnemies = 0;
            }
        }

        else if ( level == 3 ){
            if ( noOfEnemies == 0)
                noOfEnemies = createEnemies(100 , 0 , 0 , 0 , 0);

            if( noOfEnemies == deadCounter ){
                level = 4;
                deadCounter = 0;
                noOfEnemies = 0;
            }
        }
        else if( level == 4 ){
            //eray hoca boss olarak gelcek
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
            if (e.getCode() == KeyCode.H) {
                keyInputs[8].set(true);
            }
            if (e.getCode() == KeyCode.J) {
                keyInputs[9].set(true);
            }
            if (e.getCode() == KeyCode.K) {
                keyInputs[10].set(true);
            }
            if (e.getCode() == KeyCode.L) {
                keyInputs[11].set(true);
            }
            if (e.getCode() == KeyCode.ESCAPE) {
                keyInputs[12].set(true);
            }
            if (e.getCode() == KeyCode.TAB) {
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
            if (e.getCode() == KeyCode.H) {
                keyInputs[8].set(false);
            }
            if (e.getCode() == KeyCode.J) {
                keyInputs[9].set(false);
            }
            if (e.getCode() == KeyCode.K) {
                keyInputs[10].set(false);
            }
            if (e.getCode() == KeyCode.L) {
                keyInputs[11].set(false);
            }
            if (e.getCode() == KeyCode.ESCAPE) {
                keyInputs[12].set(false);
            }
            if (e.getCode() == KeyCode.TAB) {
                keyInputs[13].set(false);
            }
        });
    }

    public double magicConverter(double wantedInteger){
        return width/(1920/wantedInteger);
    }
}
