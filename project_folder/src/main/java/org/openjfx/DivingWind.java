package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

import java.net.CookieHandler;


public class DivingWind extends Enemy {
    private double x_dest;
    private double y_dest;
    DivingWind(double width, double height, ImagePattern asset) {
        super(width / 60.95, height / 18, "divingWind");
        super.initBody(asset, width, height);
        this.height = magicConverter( 60);
        this.width = magicConverter( 31.5);
        speed = 0;
        speed_x = 0;
        speed_y = 0;
        x_dest = 0;
        y_dest = 0;
        acceleration = magicConverter(5);
        body.setFill(asset);
    }

    public void moveDivingWind(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left) {
        // TODO: Add Comments

        // TODO: Fix this
        if (player.facingLeft){
            if (player.getX() >= getX())
                canSeePlayer = (player.getX() - getX()) < player.getWidth() * 3;
            else
                canSeePlayer = (getX() - player.getX()) < 1920 - player.getWidth() * 2;
        }
        else {
            if (getX() >= player.getX())
                canSeePlayer = (getX() - player.getX()) < player.getWidth() * 3;
            else
                canSeePlayer = (player.getX() - getX()) < 1920 - player.getWidth() * 2;
        }
        if (canSeePlayer) {
            double xCoord = this.getX() + width / 2;
            double yCoord = this.getY() + height / 2;
            if ((int) speed_x == 0 && (int) speed_y == 0) {
                x_dest = player.getX() + player.getWidth() / 2;
                y_dest = player.getY() + player.getHeight() / 2;
            }
            double x_dist = Math.abs(x_dest - xCoord);
            double y_dist = Math.abs(y_dest - yCoord);
            double hipo = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));
            if (speed > 0 && x_dist < speed_x && y_dist < speed_y && acceleration > 0) {
                acceleration = acceleration * -4;
                x_dest = xCoord;
                y_dest = yCoord;
            } else if ((int) speed == 0) {
                acceleration = magicConverter(8);
            }
            speed = speed + acceleration;
            if (speed <= maxSpeed) {
                speed_x = speed * x_dist / hipo;
                speed_y = speed * y_dist / hipo;
            }
            if (speed > 0) {
                if (x_dest > xCoord && acceleration > 0) {
                    directionX = 1;
                    moveX(1, speed_x);
                } else if (x_dest < xCoord && acceleration > 0) {
                    directionX = -1;
                    moveX(-1, speed_x);
                } else if (x_dest < xCoord && acceleration < 0) {
                    directionX = 1;
                    moveX(1, speed_x);
                } else if (x_dest > xCoord && acceleration < 0) {
                    directionX = -1;
                    moveX(-1, speed_x);
                } else {
                    moveX(directionX, speed_x);
                }
                if (y_dest > yCoord && acceleration > 0) {
                    directionY = 1;
                    moveY(1, speed_y);
                } else if (y_dest > yCoord && acceleration < 0) {
                    directionY = -1;
                    moveY(-1, speed_y);
                } else if (y_dest < yCoord && acceleration < 0) {
                    directionY = 1;
                    moveY(1, speed_y);
                } else if (y_dest < yCoord && acceleration > 0) {
                    directionY = -1;
                    moveY(-1, speed_y);
                } else {
                    moveY(directionY, speed_y);
                }
            }
        }
        else{
            speed = 0;
            speed_y = 0;
            speed_x = 0;
        }

        //updates the space ships so they loop around map
        loopAroundTheMap(GCF.width, player, left);

        // Actions when collision
        for (Shape hitBox : hitBoxes) {
            if (hitBox instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBox);
                if (temp.isDead())
                    dead = true;
            } else if (hitBox instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBox);
                if (temp.isDead()) {
                    dead = true;
                }
            }
        }
        if (dead) {
            dropAbility(GCF);
            explode("explode", GCF);
        }
    }
}
