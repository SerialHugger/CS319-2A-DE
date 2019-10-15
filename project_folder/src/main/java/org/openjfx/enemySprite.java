package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class enemySprite extends Sprite {
        double Destination_x;
        double Destination_y;
        enemySprite(int x, int y, int w, int h, Color color, Pane root) {
            super(x,y,w, h, color, root);
            type = "enemy";
        }

        double aimX(){ return Destination_x;}
        double aimY(){ return Destination_y;}
}
