package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.List;
import java.util.Random;


public class Collectible extends GameComponent {

    enum Ability { // an enum that represents the special abilities
        Shield, HyperJump, EngineBlast, BulletRain, Barrier, GuidedRocket, Melee;

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
                case HyperJump:
                    type = "hyperJump";
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

    private boolean didHitTheGround; // checks whether the ability hit the ground or not
    private Ability abilityType;

    Collectible(double width, double height, String assetLocation) {
        super(width, height, "collectible");
        abilityType = Ability.randomAbility();
        didHitTheGround = false;
        System.out.println("Ability type of random: " + abilityType.getType());
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "collectible", getAbilityType());
        body = new Rectangle(this.width, this.height);
        body.setFill(Color.PURPLE);
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

    public String getAbilityType() {
        return abilityType.getType();
    }
}
