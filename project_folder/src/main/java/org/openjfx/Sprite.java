package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
    boolean dead = false;
    String type;
    double initial_x;
    double initial_y;
    Pane root;
    Sprite(int x, int y, int w, int h, Color color, Pane root) {
        super(w, h, color);
        setTranslateX(x);
        setTranslateY(y);
        initial_x = x;
        initial_y = y;
        this.root = root;
    }

    void moveLeft( double x ) {
        setTranslateX(getTranslateX() - x);
    }
    void moveRight( double x ) {
        setTranslateX(getTranslateX() + x);
    }
    void moveUp( double x ) {
        setTranslateY(getTranslateY() - x);
    }
    void moveDown( double x ) {
        setTranslateY(getTranslateY() + x);
    }
    void moveX (double x) { setTranslateX(getTranslateX() + x); }
    void moveY (double x) { setTranslateX(getTranslateX() + x); }
}