package org.openjfx;

public class EnemyBullet extends EnemyEquipment {
    double x_player = 0; // x coordinate of the player
    double y_player = 0; // y coordinate of the player
    double x_bullet = 0; // initial x of the bullet
    double y_bullet = 0; // initial y of the bullet
    double dist_x = 0; // distance between x_player and x_bullet
    double dist_y = 0; // distance between y_player and y_bullet
    double hipo = 0;
    EnemyBullet(double width, double height, String assetLocation, boolean toLeft){
        super(width, height, assetLocation);
    }
    public void setX(double x){ // temporary setX method
        body.setTranslateX(x);
        hitBoxes[0].setTranslateX(x);
        x_bullet = x;
    }
    public void setY(double y){ //temporary setY method
        body.setTranslateY(y);
        hitBoxes[0].setTranslateY(y);
        y_bullet = y;
    }
}
