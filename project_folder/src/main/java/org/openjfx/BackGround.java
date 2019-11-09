package org.openjfx;


import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public class BackGround {
    private String type;
    private double width;
    private double height;
    // 2 background for loop
    private SceneComponent background_1;
    private SceneComponent background_2;


    BackGround (double width, double height, String type, Pane gameRoot){
        this.type = type;
        this.width = width;
        this.height = height;
        background_1 = new SceneComponent(width * 4,height,type,"Assets\\Scenery\\mainBackground.jpg");
        background_1.setTranslateX(-1 * (width * 2));
        background_1.setTranslateY(0);
        background_2 = new SceneComponent(width * 4,height,type, "Assets\\Scenery\\mainBackground.jpg");
        background_2.setTranslateX(width * 2);
        background_2.setTranslateY(0);
        gameRoot.getChildren().add(background_1);
        gameRoot.getChildren().add(background_2);
    }

    void update(boolean left, Player player){
        if(left) { // if button to go left, "A", is pressed
            if(background_2.getTranslateX() + (width * 4) > player.getX() + (width * 2))
            {
                background_1.setTranslateX(background_2.getTranslateX() - (width * 4));
            }
            if(background_1.getTranslateX() + (width * 4) > player.getX() + (width * 2))
            {
                background_2.setTranslateX(background_1.getTranslateX() - (width * 4));
            }
        }
        else { // if button to go right, "D", is pressed
            if(background_1.getTranslateX() < player.getX() - (width * 2))
            {
                background_2.setTranslateX(background_1.getTranslateX() + (width *4));
            }
            if(background_2.getTranslateX() < player.getX() - (width * 2))
            {
                background_1.setTranslateX(background_2.getTranslateX() + (width * 4));
            }
        }
    }
}
