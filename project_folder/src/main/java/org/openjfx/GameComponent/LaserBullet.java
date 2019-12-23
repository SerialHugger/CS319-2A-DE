package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class LaserBullet extends EnemyBullet {

    LaserBullet(double width, double height, ImagePattern asset, boolean toLeft, GameComponent player) {
        super(width, height, "laserBullet");
        this.facingLeft = toLeft;
        hitBoxes = new Shape[1];
        speed = magicConverter(7);
        this.height = magicConverter(10);
        this.width = magicConverter(15);
        hitBoxes[0] = new ComponentHitBoxCircle(this.width / 2, "enemyBullet", "laserBullet");
        body = new Circle(this.width / 2, Color.RED);
        body.setFill(asset);
        double missChance = magicConverter(Math.random() * 400 - 200);
        x_player = player.getX() + player.width / 2 - missChance;
        y_player = player.getY() + player.height / 2 - missChance;

    }

    public void updateLaserBullet() {
        body.setRotate(rotate);
        rotate += 15;
        if (x_player > x_initial && y_player == y_initial) // if the player is directly on the right side
            moveX(1, speed);
        else if (x_player < x_initial && y_player == y_initial) // if the player is directly on the left side
            moveX(-1, speed);
        else if (x_player == x_initial && y_player < y_initial) // if the player is at the top of the ship
            moveY(-1, speed);
        else if (x_player == x_initial && y_player > y_initial) // if the player is at the bottom of the ship
            moveY(1, speed);
        else if (x_player > x_initial && y_player < y_initial) { // if the player is at the top-right of the ship
            moveX(1, speed_x);
            moveY(-1, speed_y); // right and up
        } else if (x_player > x_initial && y_player > y_initial) { // if the player is at the bottom-right of the ship
            moveX(1, speed_x);
            moveY(1, speed_y);
        } else if (x_player < x_initial && y_player < y_initial) { // if the player is at the top-left of the ship
            moveX(-1, speed_x);
            moveY(-1, speed_y); // left and up
        } else if (x_player < x_initial && y_player > y_initial) { // if the player is at the bottom-right of the ship
            moveX(-1, speed_x);
            moveY(1, speed_y); // left and down
        }
        for (int i = 0; i < hitBoxes.length; i++) {
            if (hitBoxes[i] instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBoxes[i]);
                if (temp.isDead()) {
                    dead = true;
                }
            } else if (hitBoxes[i] instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBoxes[i]);
                if (temp.isDead()) {
                    dead = true;
                }
            }
        }
    }
}
