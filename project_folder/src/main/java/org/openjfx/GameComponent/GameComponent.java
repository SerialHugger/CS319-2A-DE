package org.openjfx.GameComponent;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

import java.io.FileInputStream;

public class GameComponent {
    Pane gameRoot; // Pane of the game. Needed for checking boundaries.
    String type; // type of the component
    Shape[] hitBoxes; // hitboxes of the component
    Shape body; // this will hold the background of the Component. Purpose of this is to seperate hitboxes and background image.
    double width; // width of the component
    double height; // height of the component
    boolean dead = false; // if true then the component will be removed from the root in the next update
    double speed = 5; // components speed
    double maxSpeed = 25; // components maximum speed
    double acceleration = 0; // screens acceleration
    int delayTimer = 0; // provides timer for delay
    boolean delay = false; // provides delays for certain component actions
    boolean facingLeft = true; // this controls where the component is facing.
    double speed_x = 0; // speed of the component at x direction
    double speed_y = 0; // speed of the component at y direction

    double firstTime = 0; // hold the initial time.
    double lastTime = System.nanoTime() / 1000000000.0; // helps calculate time
    double passedTime = 0; // helps calculate fps
    double totalPassedTime = 0; // hold unprocessed time of the game.
    ImagePattern[] animationFrames; // frames for animation
    int currentState = 0; // state of animation
    int counter = 0; // counter for animation state
    int rotate = 0;

    GameComponent(double width, double height, String type) {
        this.type = type;
        this.width = width;
        this.height = height;
    }

    /*
     * Add the shapes of this component to the given root
     */
    public void addShapes(Pane gameRoot) {
        this.gameRoot = gameRoot;
        for (int i = 0; i < hitBoxes.length; i++) {
            gameRoot.getChildren().add(hitBoxes[i]);
        }
        gameRoot.getChildren().add(body);
    }

    /*
     * Inserts image in assetLocation to body and returns the imagepattern.
     */
    public ImagePattern fillImage(String assetLocation) {
        ImagePattern imagePattern;
        try {
            // set background image
            FileInputStream inputstream = new FileInputStream(assetLocation);
            Image image = new Image(inputstream);
            imagePattern = new ImagePattern(image);
            body.setFill(imagePattern);
            inputstream.close();
        } catch (Exception e) {
            try {
                FileInputStream inputstream = new FileInputStream(assetLocation.replace("\\", "/"));
                Image image = new Image(inputstream);
                imagePattern = new ImagePattern(image);
                body.setFill(imagePattern);
                inputstream.close();
            } catch (Exception e2) {
                System.out.println(e2.toString());
                return null;
            }
        }
        return imagePattern;
    }

    public void die() { // if called the component will be removed from game.
        for (int i = 0; i < hitBoxes.length; i++) {
            gameRoot.getChildren().remove(hitBoxes[i]);
        }
        gameRoot.getChildren().remove(body);
    }

    /*
     Moves both hitboxes and rectangles with the given inputs.
     if direction is 1 then right, if -1 then left
     */
    public void moveX(int direction, double newSpeed) {
        for (int i = 0; i < hitBoxes.length; i++) {
            hitBoxes[i].setTranslateX(hitBoxes[i].getTranslateX() + (direction * newSpeed));
        }
        body.setTranslateX(body.getTranslateX() + (direction * newSpeed));
    }

    /*
     Moves both hitboxes and rectangles with the given inputs.
     if 1 then down, if -1 then up
     */
    public void moveY(int direction, double newSpeed) {
        for (int i = 0; i < hitBoxes.length; i++) {
            hitBoxes[i].setTranslateY(hitBoxes[i].getTranslateY() + (direction * newSpeed));
        }
        body.setTranslateY(body.getTranslateY() + (direction * newSpeed));
    }

    public double getX() {
        return body.getTranslateX();
    } // returns the X position of the component

    public double getY() {
        return body.getTranslateY();
    } // returns the Y position of the component

    public void setX(double newX) {
        for (int i = 0; i < hitBoxes.length; i++) {
            hitBoxes[i].setTranslateX(newX);
        }
        body.setTranslateX(newX);
    }

    public void setY(double newY) {
        for (int i = 0; i < hitBoxes.length; i++) {
            hitBoxes[i].setTranslateY(newY);
        }
        body.setTranslateY(newY);
    }


    public double magicConverter(double wantedInteger) {
        if (gameRoot == null)
            return width / (1920 / wantedInteger);
        else
            return gameRoot.getWidth() / (1920 / wantedInteger);
    }

    public boolean updateDeath() {
        for (Shape hitBox : hitBoxes) {
            if (hitBox instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBox);
                if (temp.isDead())
                    return true;
            } else if (hitBox instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBox);
                if (temp.isDead()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDead() {
        return dead;
    }

    public void rotate(double rotateValue) {
        hitBoxes[0].setRotate(rotateValue);
        body.setRotate(rotateValue);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
