package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Enemy extends GameComponent{
    double directionCheckX; // X direction decider for this type of enemy
    double directionCheckY; // Y direction decider for this type of enemy
    int directionX = 1; // if this is 1 it goes right else left
    int directionY = 1; // if this is 1 it goes down else up
    Enemy(double width, double height, String assetLocation){
        super(width, height, "player");
    }
}
