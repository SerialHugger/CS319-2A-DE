package org.openjfx;

import javafx.scene.layout.Pane;

public class MiddleGround {
    private String type;
    private double width;
    private double height;
    private Pane gameRoot;
    // 2 mountains for loop
    private SceneComponent left_mountains_4;
    private SceneComponent right_mountains_4;

    private SceneComponent temple_back;
    private SceneComponent temple_middle;
    private SceneComponent temple_front;

    double speed;


    MiddleGround (double width, double height, String type, Pane gameRoot, double speed){
        this.type = type;
        this.width = width;
        this.height = height;
        this.gameRoot = gameRoot;
        this.speed = speed;
        //Setup mountains
        double mt_Left_Width = width * 1.65; // if 1920 then 3170
        double mt_Right_Width = width * 2.035; // if 1920 then 3910
        left_mountains_4 = new SceneComponent(mt_Left_Width,height,type,"Assets\\Scenery\\leftMountains.png");
        left_mountains_4.setTranslateX((width / 3.2 + width * 1.427 + width / 10.21) * -1); // if 1920 set x to 600 + 2740 + 190
        left_mountains_4.setTranslateY(0);
        right_mountains_4 = new SceneComponent(mt_Right_Width,height,type, "Assets\\Scenery\\rightMountains.png");
        right_mountains_4.setTranslateX(width / 3.2 + width / 10.21); // if 1920 set x to 600 + 190 , 190 is speed difference
        right_mountains_4.setTranslateY(0);
        //add mountains
        gameRoot.getChildren().add(left_mountains_4);
        gameRoot.getChildren().add(right_mountains_4);
        //////////////////////////////////////////
        /////////////////////////////////////////////
        /////////////////////////////////
        //setup temple
        temple_back = new SceneComponent(width/2.795,height/4.372,type,"Assets\\Scenery\\temple_back.png");
        temple_back.setTranslateX((width/2.795/2 - width/6.66) * -1);
        temple_back.setTranslateY(height - height/4.372);
        temple_middle = new SceneComponent(width/1.95,height/3.05,type,"Assets\\Scenery\\temple_middle.png");
        temple_middle.setTranslateX((width/1.95/2 - width/5) * -1);
        temple_middle.setTranslateY(height  - height/3.05);
        temple_front = new SceneComponent(width/2.412,height/3.313,type,"Assets\\Scenery\\temple_front.png");
        temple_front.setTranslateX((width/2.412/2 - width/4) * -1);
        temple_front.setTranslateY(height - height/3.313);
        //add temple
        gameRoot.getChildren().add(temple_back);
        gameRoot.getChildren().add(temple_middle);
        gameRoot.getChildren().add(temple_front);
        //////////////////////////////////////////
        /////////////////////////////////////////////
        /////////////////////////////////

    }

    void update(boolean left, boolean right, Player player, double speed){
        // loops the background so the world seems infinte
        if(right){
            if(gameRoot.getTranslateX() * -1 > 0) ; //todo
        }
        moveX(-1,speed);
        if(speed < -1)
            checkLoop(false);
        else
            checkLoop(true);
    }

    public void slide(boolean toLeft,double slidingSpeed) {
        if(toLeft){
            moveX(1,slidingSpeed);
        } else {
            moveX(-1,slidingSpeed);
        }
    }

    private void moveX(int direction, double speed){
        left_mountains_4.setTranslateX(left_mountains_4.getTranslateX() + (direction * speed/5));
        right_mountains_4.setTranslateX(right_mountains_4.getTranslateX() + (direction * speed/5));
        temple_back.setTranslateX(temple_back.getTranslateX() + (direction * speed*3/10));
        temple_middle.setTranslateX(temple_middle.getTranslateX() + (direction * speed*2/5));
        temple_front.setTranslateX(temple_front.getTranslateX() + (direction * speed*1/2));
    }

    private void checkLoop(boolean left){

    }
}
