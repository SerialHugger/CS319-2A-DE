package org.openjfx;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;

public class bgSprite extends Sprite{

    private int width;
    private int height;
    private mainBgSprite[] mainBgSprites = new mainBgSprite[8];
    bgSprite(int x, int y, int w, int h, Pane root) {
        super(x,y,w, h, Color.GRAY, root);
        type = "background";
    }
    bgSprite(int x, int y, int w, int h, Pane root, int width, int height) {
        super(x,y,w, h, Color.GRAY, root);
        type = "background";
        this.width = width;
        this.height = height;
    }

    void createMain(){
        int x = -1 * (width * 2);
        int changeFactor = width / 2;
        for(int i = 0; i < 8; i++)
        {
            mainBgSprites[i] = new mainBgSprite(x, 0, changeFactor+1, height, root, "Assets\\bg-" + (i+1) + ".jpg" );
            x = x + changeFactor;
        }
        for(int i = 0; i < 8; i++)
        {
            root.getChildren().add(mainBgSprites[i]);
        }
    }

    void updateAll(boolean left){
        if(left) { // if button to go left, "A", is pressed

            for(int i = 0; i < 8; i++) // do this for all 8 background pieces.
            {
                mainBgSprites[i].moveRight(10); // Move background to right so it looks like player moves left
            }

            for(int i = 0; i < 8; i++) // do this for all 8 background pieces
            {
                if(i < 7) {
                    if (mainBgSprites[i].getTranslateX() + (width / 2) > width * 2) { // if the bg piece passed the limit point
                        mainBgSprites[i].setTranslateX(mainBgSprites[i + 1].getTranslateX() - width / 2); // transfer it to the beginning so it loops.
                    }
                } else if ( i == 7) { // special case for 7 since 7 + 1 != 0
                    if(mainBgSprites[7].getTranslateX() + (width / 2) > width * 2) {
                        mainBgSprites[7].setTranslateX(mainBgSprites[0].getTranslateX() - width / 2);
                    }
                }
            }

        }
        else { // if button to go right, "D", is pressed

            for(int i = 0; i < 8; i++) // do this for all 8 background pieces
            {
                mainBgSprites[i].moveLeft(10); // Move background to left so it looks like player moves right
            }
            for(int i = 0; i < 8; i++) // do this for all 8 background pieces
            {
                if(i > 0) {
                    if (mainBgSprites[i].getTranslateX() < -1 * width * 2) { // if bg piece passed the limit point
                        mainBgSprites[i].setTranslateX(mainBgSprites[i - 1].getTranslateX() + (width / 2)); // transfer it to the end so it loops
                    }
                } else if ( i == 0) { // special case for 0 since 0 - 1  != 0
                    if(mainBgSprites[0].getTranslateX() < -1 * width * 2) {
                        mainBgSprites[0].setTranslateX(mainBgSprites[7].getTranslateX() + (width / 2));
                    }
                }
            }
        }
    }
    void rush(boolean left)
    {
        int rushValue = 300;
        if(!left)
            rushValue = -1 * rushValue;
        System.out.println(rushValue);

        for (int i = 0; i < 8; i++) // do this for all 8 background pieces
        {
            mainBgSprites[i].moveLeft(rushValue); // Move background to left so it looks like player moves right
        }
        for (int i = 0; i < 8; i++) // do this for all 8 background pieces
        {
            if (i > 0) {
                if (mainBgSprites[i].getTranslateX() < -1 * width * 2) { // if bg piece passed the limit point
                    mainBgSprites[i].setTranslateX(mainBgSprites[i - 1].getTranslateX() + (width / 2)); // transfer it to the end so it loops
                }
            } else if (i == 0) { // special case for 0 since 0 - 1  != 0
                if (mainBgSprites[0].getTranslateX() < -1 * width * 2) {
                    mainBgSprites[0].setTranslateX(mainBgSprites[7].getTranslateX() + (width / 2));
                }
            }
        }
    }

}
