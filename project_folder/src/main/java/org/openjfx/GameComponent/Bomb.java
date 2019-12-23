package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Bomb extends PlayerEquipment {
    /**
     * constructor for bomb a player equipment
     * @param width width of the bomb's area effect
     * @param height height of the bomb's area affect
     * @param assets images for animation
     * @param player needs player to get used
     */
    Bomb(double width, double height, ImagePattern assets, Player player) {
        super(width, height, "bomb");
        hitBoxes = new Shape[1];
        speed = magicConverter(10);
        this.height = magicConverter(50);
        this.width = magicConverter(40);
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "playerEquipment", "bomb");
        speed_x = player.speed;
        body = new Rectangle(this.width, this.height);
        body.setFill(Color.RED);

    }

    /**
     * fall of the bomb because of the gravity
     */
    public void moveBomb() {
        if (gameRoot.getHeight() - height <= getY())
            dead = true;
        moveX(1, speed_x);
        moveY(1, speed);
        rotate(60);
    }

    /**
     * explosion of the bomb when it hits enemy or ground
     * @param GCF needs the gamefactory since it needs to call selfdestruct object
     */
    public void explode(GameComponentFactory GCF) {
        Explosion selfDest = (Explosion) GCF.createComponent("explosion");
        selfDest.setX(this.getX());
        selfDest.setY(this.getY());
        selfDest.addShapes(gameRoot);
    }
}
