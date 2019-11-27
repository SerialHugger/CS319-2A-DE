package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

/*
 * This class is responsible for creating the background middleground foreground etc.
 * Then updating them regularly so that they fit with the game.
 */
public class Scenery {
    Pane gameRoot;
    double width; // Games width/resolution
    double height; // Games height/resolution
    Hud hud;
    BackGround backGround; // background of the game
    MiddleGround middleGround; // middleground of the game
    ForeGround foreGround; // foreground of the game
    double speed;
    Scenery (Pane gameRoot, double width, double height, double speed){
        this.width = width;
        this.height = height;
        this.gameRoot = gameRoot;
        this.speed = speed;
    }

    /*
     * Create content for scenery
     */
    public void createContent(){
        backGround = new BackGround(width, height, "background", gameRoot);
        middleGround = new MiddleGround(width,height, "middleground", gameRoot, speed);
        foreGround = new ForeGround(width, height, "foreground", gameRoot);
        hud = new Hud(width, height, "hud", gameRoot);
    }
    /*
     * Update game
     */
    public void update(BooleanProperty[] keyInputs, Player player, int fps){
        //Update all elements of scenery
        backGround.update(keyInputs[1].get(), player);
        hud.update(keyInputs, speed, fps);
        middleGround.update(keyInputs[1].get(), keyInputs[3].get(), player);
        foreGround.update(keyInputs[1].get(), player);
    }

    /*
     * Inserts image in url to rectangle if fill is true
     * else open the image and return it
     */
    public ImagePattern insertImage(Rectangle button, String url, boolean fill) {
        ImagePattern imagePattern;
        try {
            // set background image
            FileInputStream inputstream = new FileInputStream(url);
            Image image = new Image(inputstream);
            imagePattern = new ImagePattern(image);
            if(fill)
                button.setFill(imagePattern);
            inputstream.close();
        } catch ( Exception e ) {
            System.out.println(e.toString());
            return null;
        }
        return  imagePattern;
    }
    public void setSliding(double slidingLimit, double slidingCounter, double slidingSpeed){
        hud.setSliding(slidingLimit,slidingCounter,slidingSpeed);
    }
}
