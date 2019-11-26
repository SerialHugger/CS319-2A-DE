package org.openjfx;


public class Equipment extends GameComponent {
    boolean toLeft;
    Equipment(double width, double height, String assetLocation){
        super(width,height,assetLocation);
    }
    // temporary setX method
    public void setX(double x){
        body.setTranslateX(x);
        hitBoxes[0].setTranslateX(x);
    }
    //temporary setY method
    public void setY(double y){
        body.setTranslateY(y);
        hitBoxes[0].setTranslateY(y);
    }
}
