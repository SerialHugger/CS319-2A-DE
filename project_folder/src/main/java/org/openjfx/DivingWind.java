package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

import java.net.CookieHandler;


public class DivingWind extends Enemy {
    private double x_dest;
    private double y_dest;
    DivingWind(double width, double height, ImagePattern asset) {
        super(width, height, "divingWind");
        this.height = magicConverter( 60);
        acceleration = magicConverter(0.3);
        this.width = magicConverter( 32);
        super.initBody(asset, width, height);
        speed = 0;
        speed_x = 0;
        speed_y = 0;
        x_dest = 0;
        y_dest = 0;
        body.setFill(asset);
    }

    public void moveDivingWind(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left , int speedFactor) {
        // TODO: Add Comments
        //System.out.println("Updating...");
        double xCoord = this.getX() + width / 2;
        double yCoord = this.getY() + height / 2;
        if ((int)speed_x == 0 && (int)speed_y == 0){
            x_dest = player.getX() + player.getWidth() / 2;
            y_dest = player.getY() + player.getHeight() / 2;
        }
        double x_dist = Math.abs(x_dest - xCoord);
        double y_dist = Math.abs(y_dest - yCoord);
        //System.out.println("x_dist" + x_dist);
        //System.out.println("y_dist" + y_dist);
        double hipo = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));
        if (speed > 0 && x_dist < speed_x && y_dist < speed_y && acceleration > 0){
            acceleration = acceleration * -4;
            x_dest = xCoord;
            y_dest = yCoord;
        }
        else if ((int)speed == 0){
            acceleration = magicConverter(0.3);
            //System.out.println(acceleration);
        }
        speed = (speed + acceleration) * speedFactor;
        //System.out.println(acceleration);
        if (speed <= maxSpeed){
            speed_x = speed * x_dist / hipo;
            speed_y = speed * y_dist / hipo;
        }
        if (speed > 0) {
            if (x_dest > xCoord && acceleration > 0) {
                directionX = 1;
                moveX(1, speed_x);
                //System.out.println("1 called");
            } else if (x_dest < xCoord && acceleration > 0) {
                directionX = -1;
                moveX(-1, speed_x);
                //System.out.println("2 called");
            } else if (x_dest < xCoord && acceleration < 0) {
                directionX = 1;
                moveX(1, speed_x);
                //System.out.println("3 called");
            } else if (x_dest > xCoord && acceleration < 0) {
                directionX = -1;
                moveX(-1, speed_x);
                //System.out.println("4 called");
            } else /*if (x_dest == xCoord)*/{
                moveX(directionX, speed_x);
                //System.out.println("9 called");
            }
            if (y_dest > yCoord && acceleration > 0) {
                directionY = 1;
                moveY(1, speed_y);
                //System.out.println("5 called");
            } else if (y_dest > yCoord && acceleration < 0) {
                directionY = -1;
                moveY(-1, speed_y);
                //System.out.println("6 called");
            } else if (y_dest < yCoord && acceleration < 0) {
                directionY = 1;
                moveY(1, speed_y);
                //System.out.println("7 called");
            } else if (y_dest < yCoord && acceleration > 0) {
                directionY = -1;
                moveY(-1, speed_y);
                //System.out.println("8 called");
            } else /*if (y_dest == yCoord) */{
                moveY(directionY, speed_y);
                //System.out.println("10 called");
            }
        }
        /*double initialSpeed = 15;
        double xCoord = this.getX();
        double yCoord = this.getY();
        double x_player = player.getX() + player.getWidth() / 2;
        double y_player = player.getY() + player.getHeight() / 2;
        double x_dist = Math.abs(x_player - xCoord);
        double y_dist = Math.abs(y_player - yCoord);
        double hipo = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));
        speed_x = initialSpeed * x_dist / hipo;
        speed_y = initialSpeed * y_dist / hipo;
        if (x_player > xCoord) {
            directionX = 1;
            moveX(directionX, speed_x);
        }
        else if (x_player < xCoord){
            directionX = -1;
            moveX(directionX, speed_x);
        }
        if (y_player > yCoord) {
            directionY = 1;
            moveY(directionY, speed_y);
        }
        else if (y_player < yCoord){
            directionY = -1;
            moveY(directionY, speed_y);
        }*/
        //updates the space ships so they loop around map
        loopAroundTheMap(GCF.width, player, left);

        // Actions when collision
        dead = updateDeath();
        if (dead) {
            dropAbility(GCF);
            explode("explode", GCF);
        }
    }
}
