package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Collectible extends GameComponent {

    enum Ability { // an enum that represents the special abilities
        Shield, Bomb, EngineBlast, BulletRain, Barrier, GuidedRocket, Melee;

        private static final List<Ability> VALUES = List.of(values()); // holds the abilities

        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();
        // returns a random ability that will be added to the inventory upon collection of collectible
        public static Ability randomAbility()  {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }

        // returns the type of the ability
        public String getType() {
            String type;
            switch (this) {
                case Shield:
                    type =  "shield";
                    break;
                case Bomb:
                    type = "bomb";
                    break;
                case EngineBlast:
                    type = "engineBlast";
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

    private Ability abilityType;

    Collectible(double width, double height, String assetLocation) {
        super(width, height, "collectible");
        abilityType = Ability.randomAbility();
        System.out.println("Ability type of random: " + abilityType.getType());
        hitBoxes = new Rectangle[1];
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "collectible", getAbilityType());
        body = new Rectangle(this.width, this.height);
        body.setFill(Color.YELLOW);
    }

    public void moveCollectible() {

        if (gameRoot.getHeight() - height * 1.2 > getY())
            moveY(1, magicConverter(10));

        // Actions when collision
        for (Shape hitBox : hitBoxes) {
            if (hitBox instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBox);
                if (temp.isDead()) {
                    dead = true;
                    System.out.println("asd123");
                    moveY(-1, 500);
                }
            } else if (hitBox instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBox);
                if (temp.isDead()) {
                    dead = true;
                    System.out.println("123asd");
                    moveY(-1, 500);
                }
            }
        }
    }

    public String getAbilityType() {
        return abilityType.getType();
    }
}
