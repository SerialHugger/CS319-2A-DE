package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ComponentHitBoxCircle extends Circle {
    public String type;
    private double radius;
    private String specificType;
    public boolean dead = false;

    /**
     * hitbox in the shape of circle
     * @param radius radius of circle
     * @param type type of gamecomponent
     * @param specificType specific type of component
     */
    ComponentHitBoxCircle(double radius, String type, String specificType) {
        super(radius, Color.TRANSPARENT); //Color.TRANSPARENT
        //this.setStroke(Color.RED);
        this.specificType = specificType;
        this.radius = radius;
        this.type = type;
    }

    /**
     * getter for type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * getter for specific type
     * @return specific type
     */
    public String getSpecificType() {
        return specificType;
    }

    /**
     * control if circle is dead
     * @return boolean dead
     */
    public boolean isDead() {
        return dead;
    }
}