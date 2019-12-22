package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GuidedBullet extends EnemyBullet{
    private final int guideLimit = 5;

    private int guideTime = 0;
    private boolean guidable = true;
    private int rotate = 5;
    GuidedBullet(double width, double height, ImagePattern asset, boolean toLeft, GameComponent player){
        super(width,height,"guidedBullet");
        this.facingLeft = toLeft;
        hitBoxes = new Rectangle[1];
        speed = magicConverter(2.5);
        this.height = magicConverter(60);
        this.width = magicConverter(60);
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "enemyBullet", "guidedBullet");
        body = new Rectangle(this.width,this.height, Color.RED);
        body.setFill(asset);
    }
    public void moveGuidedBullet(Player player) {
        rotate += 25;
        body.setRotate(rotate);
        if (guidable) {
            x_player = player.getX() + player.getWidth() / 2;
            y_player = player.getY() + player.getHeight() / 2;
            dist_x = x_player - this.getX(); // calculate distance x
            dist_y = y_player - this.getY(); // calculate distance y
            hipo = Math.sqrt(Math.pow(dist_x, 2) + Math.pow(dist_y, 2)); // hipotenus of x and y
            speed_x = dist_x / hipo * speed; // calculate speed of x
            speed_y = dist_y / hipo * speed; // calculate speed of y
        }
        firstTime = System.nanoTime() / 1000000000.0;
        passedTime = firstTime - lastTime;
        lastTime = firstTime;
        totalPassedTime += passedTime;
        if (totalPassedTime > 1.0) {
            totalPassedTime = 0;
            guideTime++;
            if (guideTime > guideLimit) {
               guidable = false;
            }
        }
        this.setX(this.getX() + speed_x);
        this.setY(this.getY() +speed_y);
        /*if( x_player > x_initial && y_player == y_initial) // if the player is directly on the right side
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
        }*/
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
