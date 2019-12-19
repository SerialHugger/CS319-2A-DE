package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ComponentHitBoxRectangle extends Rectangle {
    public String type;
    public String specificType;
    private double width;
    private double height;
    public boolean dead = false;
    ComponentHitBoxRectangle(double width, double height, String type, String specificType){
        super(width,height, Color.TRANSPARENT); //Color.TRANSPARENT
        this.setStroke(Color.YELLOW);
        this.width = width;
        this.height = height;
        this.type = type;
        this.specificType = specificType;
    }
    public String getType(){
        return type;
    }
    public String getSpecificType(){
        return specificType;
    }
    public boolean isDead(){ return dead;}
}