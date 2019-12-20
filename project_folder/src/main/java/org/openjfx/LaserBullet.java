package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class LaserBullet extends EnemyBullet{
    LaserBullet(double width, double height, ImagePattern asset, boolean toLeft, GameComponent player){
        super(width,height,"laserBullet");
        this.facingLeft = toLeft;
        hitBoxes = new Rectangle[1];
        speed = magicConverter(7);
        this.height = magicConverter(10);
        this.width = magicConverter(15);
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "enemyBullet", "laserBullet");
        body = new Rectangle(this.width, this.height, Color.RED);
        body.setFill(asset);
        x_player = player.getX() + player.width/2;
        y_player = player.getY() + player.height/2;
    }
    public void updateLaserBullet() {
        if( x_player > x_initial && y_player == y_initial) // if the player is directly on the right side
            moveX(1, speed);
        else if (x_player < x_initial && y_player == y_initial) // if the player is directly on the left side
            moveX(-1, speed);
        else if(x_player == x_initial && y_player < y_initial) // if the player is at the top of the ship
            moveY(-1, speed);
        else if(x_player == x_initial && y_player > y_initial) // if the player is at the bottom of the ship
            moveY(1, speed);
        else if(x_player > x_initial && y_player < y_initial) { // if the player is at the top-right of the ship
            moveX(1, speed_x); moveY(-1,speed_y); // right and up
        }
        else if(x_player > x_initial && y_player > y_initial) { // if the player is at the bottom-right of the ship
            moveX(1,speed_x); moveY(1,speed_y);
        }
        else if(x_player < x_initial && y_player < y_initial) { // if the player is at the top-left of the ship
            moveX(-1,speed_x); moveY(-1,speed_y); // left and up
        }
        else if(x_player < x_initial && y_player > y_initial) { // if the player is at the bottom-right of the ship
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
