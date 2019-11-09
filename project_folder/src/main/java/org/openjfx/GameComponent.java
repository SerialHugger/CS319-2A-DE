package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

import java.io.FileInputStream;

public class GameComponent {
    String type; // type of the component
    Shape[] hitBoxes; // hitboxes of the component
    Shape body; // this will hold the background of the Component. Purpose of this is to seperate hitboxes and background image.
    double width;
    double height;
    boolean dead = false;
    int speed = 5;//todo
    int delayTimer = 0; // todo
    boolean delay = false; //todo
    boolean facingLeft = true;
    GameComponent(double width, double height, String type){
        this.type = type;
        this.width = width;
        this.height = height;
    }

    /*
     * Add the shapes of this component to the given root
     */
    public void addShapes(Pane gameRoot){
        for(int i = 0; i < hitBoxes.length; i++ ){
            gameRoot.getChildren().add(hitBoxes[i]);
        }
        gameRoot.getChildren().add(body);
    }

    /*
     * Inserts image in url to body and returns the imagepattern.
     */
    public ImagePattern fillImage(String url){
        ImagePattern imagePattern;
        try {
            // set background image
            FileInputStream inputstream = new FileInputStream(url);
            Image image = new Image(inputstream);
            imagePattern = new ImagePattern(image);
            body.setFill(imagePattern);
            inputstream.close();
        } catch ( Exception e ) {
            System.out.println(e.toString());
            return null;
        }
        return  imagePattern;
    }

    public void update(){/*This class will be available for other classes*/}
    public void die(){ dead = true; }
}
