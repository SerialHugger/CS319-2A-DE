package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ComponentHitBoxRectangle extends Rectangle {
    public String type;
    private double width;
    private double height;
    public boolean dead;
    ComponentHitBoxRectangle(double width, double height, String type){
        super(width,height, Color.PURPLE);
        this.width = width;
        this.height = height;
        this.type = type;
        dead = false;
    }
    public String getType(){
        return type;
    }
}