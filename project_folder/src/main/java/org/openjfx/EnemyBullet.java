package org.openjfx;

public class EnemyBullet extends EnemyEquipment {
    double x_player = 0; // x coordinate of the player
    double y_player = 0; // y coordinate of the player
    double x_initial = 0; // initial x of the bullet
    double y_initial = 0; // initial y of the bullet
    double dist_x = 0; // distance between x_player and x_bullet
    double dist_y = 0; // distance between y_player and y_bullet
    double direction_x = 0;
    double direction_y = 0;
    double hipo = 0;
    EnemyBullet(double width, double height, String type){
        super(width, height, type);
    }
    public void setX(double x){ // temporary setX method
        body.setTranslateX(x);
        hitBoxes[0].setTranslateX(x);
        x_initial = x;
        dist_x = Math.abs(x_player - x_initial); // calculate distance x
    }
    public void setY(double y) { //temporary setY method
        body.setTranslateY(y);
        hitBoxes[0].setTranslateY(y);
        y_initial = y;
        dist_y = Math.abs(y_player - y_initial); // calculate distance y
        setBodyAndDirection();
    }
    private void setBodyAndDirection(){
        hipo = Math.sqrt(Math.pow(dist_x, 2) + Math.pow(dist_y, 2)); // hipotenus of x and y
        speed_x = dist_x / hipo * speed; // calculate speed of x
        speed_y = dist_y / hipo * speed; // calculate speed of y
        body.setRotate(180 * (dist_x / hipo));
        hitBoxes[0].setRotate(180 * (dist_x / hipo));
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
    }
}
