package org.openjfx;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Civilian extends GameComponent {
    Civilian(double givenWidth, double givenHeight, ImagePattern asset){
        super(givenWidth, givenHeight, "civilian");
        hitBoxes = new Shape[2];
        //update width and height
        double tempWidth = magicConverter(150);
        double tempHeight = magicConverter(70);
        width = tempWidth;
        height = tempHeight;
        //do the calculations with width and height.
        hitBoxes[0] = new ComponentHitBoxRectangle(width,0,"playerHitBoxRectangle"); // setup the Rectangle hit box
        hitBoxes[1] = new ComponentHitBoxCircle(width/6,"playerHitBoxCircle"); // setup the Circle hit box
        body = new Rectangle(width, height, null); //setup the body
        //shipStatus[1] = asset[1];
        //shipStatus[0] = asset[0];
        body.setFill(asset);
        body.setTranslateX(width*1.5 - width*12.8); // set X for body
        body.setTranslateY(0); // set Y for body
        hitBoxes[0].setTranslateX(width*1.5 - width*12.8); // set X for hit box
        hitBoxes[0].setTranslateY(0); // set Y for hit box
        hitBoxes[1].setTranslateX(width*1.5 + width/4 - width*12.8); // set X for hit box
        hitBoxes[1].setTranslateY(0); // set Y for hit box
    }
}
