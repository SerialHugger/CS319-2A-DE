package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class laserBullet extends EnemyBullet{
    laserBullet(double width, double height, String assetLocation, boolean toLeft, GameComponent player){
        super(width,height,assetLocation);
        this.facingLeft = toLeft;
        hitBoxes = new Rectangle[1];
        speed = 7;
        hitBoxes[0] = new ComponentHitBoxRectangle(width, height, "enemyBulletHitbox");
        body = new Rectangle(width,height, Color.RED);
        //fillImage(assetLocation);
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
