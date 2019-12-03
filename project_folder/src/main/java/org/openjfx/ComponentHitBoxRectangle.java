package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ComponentHitBoxRectangle extends Rectangle {
    public String type;
    private double width;
    private double height;
    public boolean dead = false;
    ComponentHitBoxRectangle(double width, double height, String type){
        super(width,height, Color.TRANSPARENT ); //Color.TRANSPARENT
        this.width = width;
        this.height = height;
        this.type = type;
    }
    public String getType(){
        return type;
    }
    public boolean isDead(){ return dead;}
}