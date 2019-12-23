package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ComponentHitBoxRectangle extends Rectangle {
    public String type;
    public String specificType;
    private double width;
    private double height;
    public boolean dead = false;
    public boolean saved = false;
    /**
     * hitbox in the shape of circle
     * @param width radius of circle
     * @param height height of the circle
     * @param type type of gamecomponent
     * @param specificType specific type of component
     */
    ComponentHitBoxRectangle(double width, double height, String type, String specificType) {
        super(width, height, Color.TRANSPARENT); //Color.TRANSPARENT
        //this.setStroke(Color.YELLOW);
        this.width = width;
        this.height = height;
        this.type = type;
        this.specificType = specificType;
    }

    /**
     * getter for type
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * getter for specific type
     * @return the specific type
     */
    public String getSpecificType() {
        return specificType;
    }

    /**
     * check if hitbox is dead
     * @return dead
     */
    public boolean isDead() {
        return dead;
    }
    public boolean isSaved() { return saved; }
    public void setSaved(boolean status) { saved = status;}
}