package org.openjfx;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public class SceneComponent extends Rectangle {
    String type;
    String url;
    SceneComponent(double width, double height, String type, String url){
        super(width,height,null);
        this.type = type;
        this.url = url;
        try {
            FileInputStream inputstream = new FileInputStream(url);
            Image image = new Image(inputstream);
            ImagePattern imagePattern = new ImagePattern(image);
            this.setFill(imagePattern);
        } catch ( Exception e ) {
            System.out.println(e.toString());
        }
    }
}
