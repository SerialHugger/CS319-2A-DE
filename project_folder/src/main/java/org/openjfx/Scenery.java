package org.openjfx;

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
    ForeGround foreGround; // foreground of the game
    Scenery (Pane gameRoot, double width, double height){
        this.width = width;
        this.height = height;
        this.gameRoot = gameRoot;
    }

    /*
     * Create content for scenery
     */
    public void createContent(){
        backGround = new BackGround(width, height, "background", gameRoot);
        foreGround = new ForeGround(width, height, "foreground", gameRoot);
        hud = new Hud(width, height, "hud", gameRoot);
    }
    /*
     * Update game
     */
    public void update(boolean left, Player player){
        //Update all elements of scenery
        hud.update();
        backGround.update(left, player);
        foreGround.update(left, player);
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
}
