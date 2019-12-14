package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ComponentHitBoxCircle extends Circle {
    public String type;
    private double radius;
    public boolean dead = false;
    ComponentHitBoxCircle(double radius, String type){
        super(radius, Color.TRANSPARENT ); //Color.TRANSPARENT
        this.radius = radius;
        this.type = type;
    }
    public String getType(){
        return type;
    }
    public boolean isDead(){ return dead;}
}