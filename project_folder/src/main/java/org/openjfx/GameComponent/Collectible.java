package org.openjfx.GameComponent;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.FileInputStream;
import java.util.List;
import java.util.Random;


public class Collectible extends GameComponent {

    enum Ability { // an enum that represents the special abilities
        Shield, Bomb, TimeFreeze, BulletRain, Barrier, GuidedRocket;

        private static final List<Ability> VALUES = List.of(values()); // holds the abilities

        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        // returns a random ability that will be added to the inventory upon collection of collectible

        /**
         * \function to use randomability
         * @return the ability
         */
        public static Ability randomAbility() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }

        /**
         *  gets the type of ability
         * @return the specific type of ability
         */
        public String getType() {
            String type;
            switch (this) {
                case Shield:
                    type = "shield";
                    break;
                case Bomb:
                    type = "bomb";
                    break;
                case TimeFreeze:
                    type = "timeFreeze";
                    break;
                case BulletRain:
                    type = "bulletRain";
                    break;
                case Barrier:
                    type = "barrier";
                    break;
                case GuidedRocket:
                    type = "guidedRocket";
                    break;
                default:
                    type = "";
            }
            return type;
        }
    }

    private boolean didHitTheGround; // checks whether the ability hit the ground or not
    private Ability abilityType;
    private ImagePattern barrierHudImage;
    private ImagePattern guidedRocketHudImage;
    private ImagePattern shieldHudImage;
    private ImagePattern bombHudImage;
    private ImagePattern bulletRainHudImage;
    private ImagePattern timeFreezeHudImage;

    /**
     * constructor for collectible
     * @param width the width of collectible
     * @param height the height of collectib;e
     * @param assetLocation image for animation
     */
    Collectible(double width, double height, String assetLocation) {
        super(width, height, "collectible");
        this.height = magicConverter(50);
        this.width = magicConverter(40);
        abilityType = Ability.randomAbility();
        didHitTheGround = false;
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "collectible", getAbilityType());
        openCollectibleImages();
        body = new Rectangle(this.width, this.height);
        if (getAbilityType() == "guidedRocket") {
            body.setFill(guidedRocketHudImage);
        } else if (getAbilityType() == "shield")
            body.setFill(shieldHudImage);
        else if (getAbilityType() == "barrier")
            body.setFill(barrierHudImage);
        else if (getAbilityType() == "bomb")
            body.setFill(bombHudImage);
        else if (getAbilityType().equals("bulletRain"))
            body.setFill(bulletRainHudImage);
        else if (getAbilityType().equals("timeFreeze"))
            body.setFill(timeFreezeHudImage);
        else
            body.setFill(Color.GREENYELLOW);
    }

    /**
     * function for falling of the collectibles due to gravity
     */
    public void moveCollectible() {
        // Actions when collision
        for (Shape hitBox : hitBoxes) {
            if (hitBox instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBox);
                if (temp.isDead()) {
                    dead = true;
                    moveY(-1, 500);
                }
            } else if (hitBox instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBox);
                if (temp.isDead()) {
                    dead = true;
                    moveY(-1, 500);
                }
            }
        }
    }

    /**
     * macro for opening the assets of colectibles
     */
    private void openCollectibleImages() {
        barrierHudImage = openAsset("Assets\\skills\\barrier.png");
        guidedRocketHudImage = openAsset("Assets\\light_saber.png");
        shieldHudImage = openAsset("Assets\\skills\\shield.png");
        bombHudImage = openAsset("Assets\\skills\\bomb.png");
        bulletRainHudImage = openAsset("Assets\\skills\\bullet_rain.png");
        timeFreezeHudImage = openAsset("Assets\\skills\\time_freeze.png");
    }

    /**
     * macro for opening the assets in general
     * @param assetLocation parameter for location of assets
     * @return returns javafx type imagepattern
     */
    public ImagePattern openAsset(String assetLocation) {
        ImagePattern imagePattern;
        try {
            // set background image
            FileInputStream inputstream = new FileInputStream(assetLocation);
            Image image = new Image(inputstream);
            imagePattern = new ImagePattern(image);
            inputstream.close();
        } catch (Exception e) {
            try {
                FileInputStream inputstream = new FileInputStream(assetLocation.replace("\\", "/"));
                Image image = new Image(inputstream);
                imagePattern = new ImagePattern(image);
                inputstream.close();
            } catch (Exception e2) {
                System.out.println(e2.toString());
                return null;
            }
        }
        return imagePattern;
    }

    /**
     *
     * @return
     */
    public String getAbilityType() {
        return abilityType.getType();
    }
}
