package org.openjfx;


public class Equipment extends GameComponent {
    boolean toLeft;
    Equipment(double width, double height, String url){
        super(width,height,url);
    }
    public void setX(double x){
        body.setTranslateX(x);
        hitBoxes[0].setTranslateX(x);
    }
    public void setY(double y){
        body.setTranslateY(y);
        hitBoxes[0].setTranslateY(y);
    }
}
