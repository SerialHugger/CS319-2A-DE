package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ComponentHitBoxCircle extends Circle {
    public String type;
    private double radius;
    private String specificType;
    public boolean dead = false;

    ComponentHitBoxCircle(double radius, String type, String specificType) {
        super(radius, Color.TRANSPARENT); //Color.TRANSPARENT
        this.setStroke(Color.RED);
        this.specificType = specificType;
        this.radius = radius;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getSpecificType() {
        return specificType;
    }

    public boolean isDead() {
        return dead;
    }
}