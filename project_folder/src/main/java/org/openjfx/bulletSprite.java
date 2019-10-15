package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class bulletSprite extends Sprite{

    double Destination_x;
    double Destination_y;
    bulletSprite(int x, int y, int w, int h, String type,  Color color, double d_x, double d_y, Pane root) {
        super(x,y,w, h, color, root);
        this.type = type;
        Destination_x = d_x;
        Destination_y = d_y;
    }

    double aimX(){ return Destination_x;}
    double aimY(){ return Destination_y;}
}
