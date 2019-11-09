package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ComponentHitBoxCircle extends Circle {
    public String type;
    private double radius;
    public boolean dead;
    ComponentHitBoxCircle(double radius, String type){
        super(radius, Color.ORANGE);
        this.radius = radius;
        this.type = type;
        dead = false;
    }

}