package org.openjfx;

import javafx.scene.layout.Pane;

public class ForeGround {
    private String type;
    private double width;
    private double height;
    private SceneComponent foreground_1;
    private SceneComponent foreground_2;
    ForeGround (double width, double height, String type, Pane gameRoot){
        this.type = type;
        this.width = width;
        this.height = height;
        foreground_1 = new SceneComponent(width * 4,height,type,"Assets\\Scenery\\mainForeground.png");
        foreground_1.setTranslateX(-1 * (width * 2));
        foreground_1.setTranslateY(0);
        foreground_2 = new SceneComponent(width * 4,height,type, "Assets\\Scenery\\mainForeground.png");
        foreground_2.setTranslateX(width * 2);
        foreground_2.setTranslateY(0);
        gameRoot.getChildren().add(foreground_1);
        gameRoot.getChildren().add(foreground_2);
    }

    void update(boolean left, Player player){
        if(left) { // if button to go left, "A", is pressed
            if(foreground_2.getTranslateX() + (width * 4) > player.getX() + (width * 2))
            {
                foreground_1.setTranslateX(foreground_2.getTranslateX() - (width * 4));
            }
            if(foreground_1.getTranslateX() + (width * 4) > player.getX() + (width * 2))
            {
                foreground_2.setTranslateX(foreground_1.getTranslateX() - (width * 4));
            }
        }
        else { // if button to go right, "D", is pressed
            if(foreground_1.getTranslateX() < player.getX() - (width * 2))
            {
                foreground_2.setTranslateX(foreground_1.getTranslateX() + (width *4));
            }
            if(foreground_2.getTranslateX() < player.getX() - (width * 2))
            {
                foreground_1.setTranslateX(foreground_2.getTranslateX() + (width * 4));
            }
        }
    }
}
