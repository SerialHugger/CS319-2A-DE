package org.openjfx.SceneryManager;

import javafx.scene.layout.Pane;
import org.openjfx.GameComponent.Player;

public class MiddleGround {
    private String type;
    private double width;
    private double height;
    private Pane gameRoot;
    // 2 mountains
    private SceneComponent left_mountains_4;
    private SceneComponent right_mountains_4;
    // 3 human settlements for left side of the map
    private SceneComponent humanSettlement_3;
    private SceneComponent humanSettlement_2;
    private SceneComponent humanSettlement_1;
    // 3 alien temples for right side of the map
    private SceneComponent alienStructure_3;
    private SceneComponent alienStructure_2;
    private SceneComponent alienStructure_1;
    // 3 temple elements

    private SceneComponent temple_3;
    private SceneComponent temple_2;
    private SceneComponent temple_1;

    double speed;

    //  ______            _____                 ____              ___________________________      _______     __________________________
    //////           //////                ///\\\             ||||||||||||||||||||||||||||     ||||||||    |||||||||||||||||||||||||||
    ///////         ///////               ////\\\\            ||||||||||||||||||||||||||||     ||||||||    |||||||||||||||||||||||||||
    ////////       ////////              /////\\\\\           ||||||||||||||||||||||||||||     ||||||||    |||||||||||||||||||||||||||
    /////////     /////////             //////\\\\\\          ||||||||||         |||||||||     ||||||||    ||||||||            |||||||
    //////////   //////////            //////  \\\\\\         ||||||||||     ____________      ||||||||    ||||||||            |||||||
    /////////// ///////////           //////    \\\\\\        ||||||||||   __|||||||||||||     ||||||||    ||||||||
    ////// ///////// //////          //////______\\\\\\       ||||||||||   |||||||||||||||     ||||||||    ||||||||
    //////  //////   //////         //////||||||||\\\\\\      ||||||||||     |||||||||||||     ||||||||    ||||||||            _______
    //////           //////        //////          \\\\\\     ||||||||||         |||||||||     ||||||||    ||||||||____________|||||||
    //////           //////       //////            \\\\\\    ||||||||||_________|||||||||     ||||||||    |||||||||||||||||||||||||||
    //////           //////      //////              \\\\\\   ||||||||||||||||||||||||||||     ||||||||    |||||||||||||||||||||||||||
    //////           //////     //////                \\\\\\  ||||||||||||||||||||||||||||     ||||||||    |||||||||||||||||||||||||||
    MiddleGround(double width, double height, String type, Pane gameRoot, double speed) {
        this.type = type;
        this.width = width;
        this.height = height;
        this.speed = speed;
        ////// Setup mountains
        left_mountains_4 = new SceneComponent(magicConverter(3400), height, type, "Assets\\Scenery\\leftMountains.png");
        left_mountains_4.setTranslateX(magicConverter(3808) * -1); // if 1920 set x to 600 + 2740 + 288 normally. +180 weirdly
        left_mountains_4.setTranslateY(25);
        right_mountains_4 = new SceneComponent(magicConverter(3800), height, type, "Assets\\Scenery\\rightMountains.png");
        right_mountains_4.setTranslateX(magicConverter(792)); // if 1920 set x to 600 + 192 , 192 is speed difference
        right_mountains_4.setTranslateY(0);
        ////// Setup human settlements
        humanSettlement_3 = new SceneComponent(magicConverter(3640), height, type, "Assets\\Scenery\\humanSettlement_1.png");
        humanSettlement_3.setTranslateX(magicConverter(3981) * -1);
        humanSettlement_3.setTranslateY(0);
        humanSettlement_2 = new SceneComponent(magicConverter(3880), height, type, "Assets\\Scenery\\humanSettlement_2.png");
        humanSettlement_2.setTranslateX(magicConverter(4125) * -1);
        humanSettlement_2.setTranslateY(0);
        humanSettlement_1 = new SceneComponent(magicConverter(4120), height, type, "Assets\\Scenery\\humanSettlement_3.png");
        humanSettlement_1.setTranslateX(magicConverter(4269) * -1);
        humanSettlement_1.setTranslateY(0);

        ////// Setup alien Structures
        alienStructure_3 = new SceneComponent(magicConverter(4140), height, type, "Assets\\Scenery\\alienStructure_1.png");
        alienStructure_3.setTranslateX(magicConverter(888));
        alienStructure_3.setTranslateY(0);
        alienStructure_2 = new SceneComponent(magicConverter(4620), height, type, "Assets\\Scenery\\alienStructure_2.png");
        alienStructure_2.setTranslateX(magicConverter(984));
        alienStructure_2.setTranslateY(0);
        alienStructure_1 = new SceneComponent(magicConverter(5100), height, type, "Assets\\Scenery\\alienStructure_3.png");
        alienStructure_1.setTranslateX(magicConverter(1080));
        alienStructure_1.setTranslateY(0);
        //////////////////////////////////////////
        /////////////////////////////////////////////
        /////////////////////////////////
        //setup temple
        temple_3 = new SceneComponent(magicConverter(1200), magicConverter(363), type, "Assets\\Scenery\\temple_back.png");
        temple_3.setTranslateX((magicConverter(315)) * -1);
        temple_3.setTranslateY(magicConverter(1080 - 363));
        temple_2 = new SceneComponent(magicConverter(1200), magicConverter(363), type, "Assets\\Scenery\\temple_middle.png");
        temple_2.setTranslateX((magicConverter(220)) * -1);
        temple_2.setTranslateY(magicConverter(1080 - 363));
        temple_1 = new SceneComponent(magicConverter(1200), magicConverter(363), type, "Assets\\Scenery\\temple_front.png");
        temple_1.setTranslateX((magicConverter(125)) * -1);
        temple_1.setTranslateY(magicConverter(1080 - 363));
        //add everything in order
        this.gameRoot = gameRoot;
        // first 4's
        gameRoot.getChildren().add(left_mountains_4);
        gameRoot.getChildren().add(right_mountains_4);
        // second 3's
        gameRoot.getChildren().add(humanSettlement_3);
        gameRoot.getChildren().add(alienStructure_3);
        gameRoot.getChildren().add(temple_3);
        // third 2's
        gameRoot.getChildren().add(humanSettlement_2);
        gameRoot.getChildren().add(alienStructure_2);
        gameRoot.getChildren().add(temple_2);
        // fourth 1's
        gameRoot.getChildren().add(humanSettlement_1);
        gameRoot.getChildren().add(alienStructure_1);
        gameRoot.getChildren().add(temple_1);
        //////////////////////////////////////////
        /////////////////////////////////////////////
        /////////////////////////////////

    }

    void update(boolean left, boolean right, Player player, double speed) {

        moveX(-1, speed);
        if (speed > 0)
            checkLoop(false);
        else
            checkLoop(true);
    }

    public void slide(boolean toLeft, double slidingSpeed) {
        if (toLeft) {
            moveX(1, slidingSpeed);
        } else {
            moveX(-1, slidingSpeed);
        }
    }

    private void moveX(int direction, double speed) {
        //move mountains
        left_mountains_4.setTranslateX(left_mountains_4.getTranslateX() + (direction * speed / 5));
        right_mountains_4.setTranslateX(right_mountains_4.getTranslateX() + (direction * speed / 5));
        //move settlements
        humanSettlement_3.setTranslateX(humanSettlement_3.getTranslateX() + (direction * speed * 3 / 10));
        humanSettlement_2.setTranslateX(humanSettlement_2.getTranslateX() + (direction * speed * 2 / 5));
        humanSettlement_1.setTranslateX(humanSettlement_1.getTranslateX() + (direction * speed * 1 / 2));
        //move settlements
        alienStructure_3.setTranslateX(alienStructure_3.getTranslateX() + (direction * speed * 3 / 10));
        alienStructure_2.setTranslateX(alienStructure_2.getTranslateX() + (direction * speed * 2 / 5));
        alienStructure_1.setTranslateX(alienStructure_1.getTranslateX() + (direction * speed * 1 / 2));
        //move temples
        temple_3.setTranslateX(temple_3.getTranslateX() + (direction * speed * 3 / 10));
        temple_2.setTranslateX(temple_2.getTranslateX() + (direction * speed * 2 / 5));
        temple_1.setTranslateX(temple_1.getTranslateX() + (direction * speed * 1 / 2));
    }

    private void checkLoop(boolean left) {
        // RANGE = width *4 +-25
        if (left) {
            double mod = Math.abs(gameRoot.getTranslateX()) % (width * 4);
            if (mod < 35 && mod > -35) {
                double newLocationMountain = -1 * (gameRoot.getTranslateX() + mod) - magicConverter(600 + 192) - left_mountains_4.getWidth();
                double newLocationHumanSettlement_3 = -1 * (gameRoot.getTranslateX() - mod) - magicConverter(600 + 288) - humanSettlement_3.getWidth();
                double newLocationHumanSettlement_2 = -1 * (gameRoot.getTranslateX() - mod) - magicConverter(600 + 384) - humanSettlement_2.getWidth();
                double newLocationHumanSettlement_1 = -1 * (gameRoot.getTranslateX() - mod) - magicConverter(600 + 480) - humanSettlement_1.getWidth();
                left_mountains_4.setTranslateX(newLocationMountain);
                humanSettlement_3.setTranslateX(newLocationHumanSettlement_3);
                humanSettlement_2.setTranslateX(newLocationHumanSettlement_2);
                humanSettlement_1.setTranslateX(newLocationHumanSettlement_1);
            }
            mod = (Math.abs(gameRoot.getTranslateX()) + width * 2) % (width * 4);
            if (mod < 35 && mod > -35) {
                double newLocationMountain = -1 * (gameRoot.getTranslateX() - mod) - magicConverter(480 + 192 - 260) - right_mountains_4.getWidth();
                double newLocationTemple_back = -1 * (gameRoot.getTranslateX() - mod) - magicConverter(2880 + 1440 + 360) - temple_3.getWidth();
                double newLocationTemple_middle = -1 * (gameRoot.getTranslateX() - mod) - magicConverter(2880 + 1920 + 360) - temple_2.getWidth();
                double newLocationTemple_front = -1 * (gameRoot.getTranslateX() - mod) - magicConverter(2880 + 2400 + 360) - temple_1.getWidth();
                double newLocationAlienStructure_3 = -1 * (gameRoot.getTranslateX() - mod) - magicConverter(480 + 288 - 260) - alienStructure_3.getWidth();
                double newLocationAlienStructure_2 = -1 * (gameRoot.getTranslateX() - mod) - magicConverter(480 + 384 - 260) - alienStructure_2.getWidth();
                double newLocationAlienStructure_1 = -1 * (gameRoot.getTranslateX() - mod) - magicConverter(480 + 480 - 260) - alienStructure_1.getWidth();
                right_mountains_4.setTranslateX(newLocationMountain);
                temple_3.setTranslateX(newLocationTemple_back);
                temple_2.setTranslateX(newLocationTemple_middle);
                temple_1.setTranslateX(newLocationTemple_front);
                alienStructure_3.setTranslateX(newLocationAlienStructure_3);
                alienStructure_2.setTranslateX(newLocationAlienStructure_2);
                alienStructure_1.setTranslateX(newLocationAlienStructure_1);
            }

        } else { // MOVING RIGHT YEEE
            double mod = Math.abs(gameRoot.getTranslateX()) % (width * 4);
            if (mod < 35 && mod > -35) {
                double newLocationMountain = -1 * (gameRoot.getTranslateX() + mod) + width * 2 + magicConverter(840 + 572 - 260);
                double newLocationHumanSettlement_3 = -1 * (gameRoot.getTranslateX() + mod) + width * 2 + magicConverter(840 + 864 - 130);
                double newLocationHumanSettlement_2 = -1 * (gameRoot.getTranslateX() + mod) + width * 2 + magicConverter(840 + 1152 - 130);
                double newLocationHumanSettlement_1 = -1 * (gameRoot.getTranslateX() + mod) + width * 2 + magicConverter(840 + 1440 - 130);
                left_mountains_4.setTranslateX(newLocationMountain);
                humanSettlement_3.setTranslateX(newLocationHumanSettlement_3);
                humanSettlement_2.setTranslateX(newLocationHumanSettlement_2);
                humanSettlement_1.setTranslateX(newLocationHumanSettlement_1);

            }
            mod = (Math.abs(gameRoot.getTranslateX()) + width * 2) % (width * 4);
            if (mod < 35 && mod > -35) {
                double newLocationMountain = -1 * (gameRoot.getTranslateX() + mod) + width * 2 + magicConverter(600 + 572);
                double newLocationTemple_back = -1 * (gameRoot.getTranslateX() + mod) + magicConverter(3220 + 864);
                double newLocationTemple_middle = -1 * (gameRoot.getTranslateX() + mod) + magicConverter(3220 + 1152);
                double newLocationTemple_front = -1 * (gameRoot.getTranslateX() + mod) + magicConverter(3220 + 1440);
                double newLocationAlienStructure_3 = -1 * (gameRoot.getTranslateX() + mod) + width * 2 + magicConverter(840 + 864 - 200);
                double newLocationAlienStructure_2 = -1 * (gameRoot.getTranslateX() + mod) + width * 2 + magicConverter(840 + 1152 - 200);
                double newLocationAlienStructure_1 = -1 * (gameRoot.getTranslateX() + mod) + width * 2 + magicConverter(840 + 1440 - 200);
                right_mountains_4.setTranslateX(newLocationMountain);
                temple_3.setTranslateX(newLocationTemple_back);
                temple_2.setTranslateX(newLocationTemple_middle);
                temple_1.setTranslateX(newLocationTemple_front);
                alienStructure_3.setTranslateX(newLocationAlienStructure_3);
                alienStructure_2.setTranslateX(newLocationAlienStructure_2);
                alienStructure_1.setTranslateX(newLocationAlienStructure_1);
            }
        }
    }

    public double magicConverter(double wantedInteger) {
        if (gameRoot == null)
            return width / (1920 / wantedInteger);
        else
            return gameRoot.getWidth() / (1920 / wantedInteger);
    }
}
