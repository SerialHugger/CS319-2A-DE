package org.openjfx;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

/*
 * Component for Scenery elements.
 * Makes it easier to control background/middleground/foregorund elements in menu.
 */
public class SceneComponent extends Rectangle {
    String type;
    String assetLocation;
    SceneComponent(double width, double height, String type, String assetLocation){
        super(width,height,null);
        this.type = type;
        this.assetLocation = assetLocation;
        if(!assetLocation.equals("empty")){
            try {
                FileInputStream inputstream = new FileInputStream(assetLocation);
                Image image = new Image(inputstream);
                ImagePattern imagePattern = new ImagePattern(image);
                this.setFill(imagePattern);
            } catch ( Exception e ) {
                try{
                    FileInputStream inputstream = new FileInputStream(assetLocation.replace("\\","/"));
                    Image image = new Image(inputstream);
                    ImagePattern imagePattern = new ImagePattern(image);
                    this.setFill(imagePattern);
                } catch ( Exception e2 ){
                    System.out.println(e.toString());
                }
            }
        }
    }
}
