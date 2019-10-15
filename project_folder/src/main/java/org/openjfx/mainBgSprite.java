package org.openjfx;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;

public class mainBgSprite extends bgSprite {

    String url;
    mainBgSprite(int x, int y, int w, int h, Pane root, String url) {
        super(x,y,w, h, root);
        type = "mainBackground";
        System.out.println(url);
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
    void updateMainBg(boolean left)
    {

    }
}
