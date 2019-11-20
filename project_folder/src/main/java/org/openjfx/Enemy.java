package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Enemy extends GameComponent{
    Enemy(double width, double height, String assetLocation){
        super(width, height, "player");
    }
}
