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
        Shield, Bomb, TimeFreeze, BulletRain, Barrier, GuidedRocket, Melee;

        private static final List<Ability> VALUES = List.of(values()); // holds the abilities

        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        // returns a random ability that will be added to the inventory upon collection of collectible
        public static Ability randomAbility() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }

        // returns the type of the ability
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
                case Melee:
                    type = "melee";
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
    private ImagePattern engineBlastHudImage;

    Collectible(double width, double height, String assetLocation) {
        super(width, height, "collectible");
        this.height = magicConverter(50);
        this.width = magicConverter(40);
        abilityType = Ability.randomAbility();
        didHitTheGround = false;
        System.out.println("Ability type of random: " + abilityType.getType());
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "collectible", getAbilityType());
        openCollectibleImages();
        body = new Rectangle(this.width, this.height);
        if (getAbilityType() == "guidedRocket") {
            body.setFill(guidedRocketHudImage);
        }
        else
            body.setFill(Color.TRANSPARENT);
    }

    public void moveCollectible() {
        // Actions when collision
        for (Shape hitBox : hitBoxes) {
            if (hitBox instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBox);
                if (temp.isDead()) {
                    dead = true;
                    System.out.println("COLLECTIBE: " + abilityType.getType() + " DEAD");
                    moveY(-1, 500);
                }
            } else if (hitBox instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBox);
                if (temp.isDead()) {
                    dead = true;
                    System.out.println("COLLECTIBE: " + abilityType.getType() + " DEAD");
                    moveY(-1, 500);
                }
            }
        }
    }

    private void openCollectibleImages() {
        //barrierHudImage = openAsset("Assets\\barrierImage.png");
        guidedRocketHudImage = openAsset("Assets\\light_saber.png");
        //shieldHudImage = openAsset("Assets\\shieldImage.png");
        //engineBlastHudImage = openAsset("Assets\\engineBlastImage.png");
    }
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

    public String getAbilityType() {
        return abilityType.getType();
    }
}
