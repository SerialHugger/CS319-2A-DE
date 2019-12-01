package org.openjfx;

public class EnemyBullet extends EnemyEquipment {
    double x_player = 0; // x coordinate of the player
    double y_player = 0; // y coordinate of the player
    double x_initial = 0; // initial x of the bullet
    double y_initial = 0; // initial y of the bullet
    double dist_x = 0; // distance between x_player and x_bullet
    double dist_y = 0; // distance between y_player and y_bullet
    double hipo = 0;
    EnemyBullet(double width, double height, String assetLocation){
        super(width, height, assetLocation);
    }
    public void setX(double x){ // temporary setX method
        body.setTranslateX(x);
        hitBoxes[0].setTranslateX(x);
        x_initial = x;
        dist_x = Math.abs(x_player - x_initial); // calculate distance x
    }
    public void setY(double y){ //temporary setY method
        body.setTranslateY(y);
        hitBoxes[0].setTranslateY(y);
        y_initial = y;
        dist_y = Math.abs(y_player - y_initial); // calculate distance y
    }
}
