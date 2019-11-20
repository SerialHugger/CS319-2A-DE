package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnemyBulletType1 extends EnemyBullet{
    EnemyBulletType1(double width, double height, String assetLocation, boolean toLeft, GameComponent player){
        super(width,height,assetLocation, toLeft);
        this.toLeft = toLeft;
        hitBoxes = new Rectangle[1];
        speed = 7;
        hitBoxes[0] = new ComponentHitBoxRectangle(width, height, "enemyBulletType1");
        body = new Rectangle(width,height, Color.RED);
        //fillImage(assetLocation);
        x_player = player.getX() + player.width/2;
        y_player = player.getY() + player.height/2;
    }
    public void update() {
        dist_x = Math.abs(x_player - x_bullet); // calculate distance x
        dist_y = Math.abs(y_player - y_bullet); // calculate distance y
        hipo = Math.sqrt(Math.pow(dist_x, 2) + Math.pow(dist_y,2)); // hioptenus of x and y
        speed_x = dist_x / hipo * speed; // calculate speed of x
        speed_y =  dist_y / hipo * speed;
        if( x_player > x_bullet && y_player == y_bullet) // if the player is directly on the right side
            moveX(1, speed);
        else if (x_player < x_bullet && y_player == y_bullet) // if the player is directly on the left side
            moveX(-1, speed);
        else if(x_player == x_bullet && y_player < y_bullet) // if the player is at the top of the ship
            moveY(-1, speed);
        else if(x_player == x_bullet && y_player > y_bullet) // if the player is at the bottom of the ship
            moveY(1, speed);
        else if(x_player > x_bullet && y_player < y_bullet) { // if the player is at the top-right of the ship
            moveX(1, speed_x); moveY(-1,speed_y); // right and up
        }
        else if(x_player > x_bullet && y_player > y_bullet) { // if the player is at the bottom-right of the ship
            moveX(1,speed_x); moveY(1,speed_y);
        }
        else if(x_player < x_bullet && y_player < y_bullet) { // if the player is at the top-left of the ship
            moveX(-1,speed_x); moveY(-1,speed_y); // left and up
        }
        else if(x_player < x_bullet && y_player > y_bullet) { // if the player is at the bottom-right of the ship
            moveX(-1,speed_x); moveY(1,speed_y); // left and down
        }
        for(int i = 0; i < hitBoxes.length; i++){
            if(hitBoxes[i] instanceof ComponentHitBoxCircle){
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle)hitBoxes[i]);
                if(temp.isDead()) {
                    dead = true;
                }
            } else if (hitBoxes[i] instanceof ComponentHitBoxRectangle){
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle)hitBoxes[i]);
                if(temp.isDead()) {
                    dead = true;
                }
            }
        }
    }
}
