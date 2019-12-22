package org.openjfx;

import javafx.scene.paint.ImagePattern;

public class Civilian extends GameComponent {
    boolean grabbed;

    Civilian (double givenWidth, double givenHeight, ImagePattern asset){
        super(givenWidth, givenHeight, "civilian");
        this.height = magicConverter( 80);
        this.width = magicConverter( 60);
        body.setFill(asset);
    }

    public void initBody(){
        double startingX = (Math.random() * (width  * 4)) + (width * -2) ;
        double startingY = 1050;
    }
    public void moveCivilian(GameComponentFactory GCF, Player player, boolean left){
        if (grabbed){

        }
    }
}
