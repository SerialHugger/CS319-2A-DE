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

    private double mt_Left_Width;
    private double mt_Right_Width;
    double speed;


    MiddleGround (double width, double height, String type, Pane gameRoot, double speed){
        this.type = type;
        this.width = width;
        this.height = height;
        this.gameRoot = gameRoot;
        this.speed = speed;
        //Setup mountains
        mt_Left_Width = width * 1.65;
        mt_Right_Width = width * 2.035;
        left_mountains_4 = new SceneComponent(mt_Left_Width,height,type,"/Users/cemalardakizilkaya/Desktop/Assets/Scenery/leftMountains.png");
        left_mountains_4.setTranslateX((width / 3.2 + width * 1.427 + width / 10.21) * -1);
        left_mountains_4.setTranslateY(0);
        right_mountains_4 = new SceneComponent(mt_Right_Width,height,type, "/Users/cemalardakizilkaya/Desktop/Assets/Scenery/rightMountains.png");
        right_mountains_4.setTranslateX(width / 3.2 + width / 10.21);
        right_mountains_4.setTranslateY(0);
        //add mountains
        gameRoot.getChildren().add(left_mountains_4);
        gameRoot.getChildren().add(right_mountains_4);
        //////////////////////////////////////////
        /////////////////////////////////////////////
        /////////////////////////////////
        //setup temple
        temple_back = new SceneComponent(width/2.795,height/4.372,type,"/Users/cemalardakizilkaya/Desktop/Assets/Scenery/temple_back.png");
        temple_back.setTranslateX((width/2.795/2 - width/6.66) * -1);
        temple_back.setTranslateY(height - height/4.372);
        temple_middle = new SceneComponent(width/1.95,height/3.05,type,"/Users/cemalardakizilkaya/Desktop/Assets/Scenery/temple_middle.png");
        temple_middle.setTranslateX((width/1.95/2 - width/5) * -1);
        temple_middle.setTranslateY(height  - height/3.05);
        temple_front = new SceneComponent(width/2.412,height/3.313,type,"/Users/cemalardakizilkaya/Desktop/Assets/Scenery/temple_front.png");
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

    void update(boolean left, boolean right, Player player){
        // loops the background so the world seems infinte
        if(left) { // if button to go left, "A", is pressed
//            if(right_mountains_4.getTranslateX() + (width * 4) > player.getX() + (width * 2))
//            {
//                middleGround_1.setTranslateX(middleGround_2.getTranslateX() - (width * 4));
//            }
//            if(middleGround_1.getTranslateX() + (width * 4) > player.getX() + (width * 2))
//            {
//                middleGround_2.setTranslateX(middleGround_1.getTranslateX() - (width * 4));
//            }
            left_mountains_4.setTranslateX(left_mountains_4.getTranslateX() + speed/5);
            right_mountains_4.setTranslateX(right_mountains_4.getTranslateX() + speed/5);
            temple_back.setTranslateX(temple_back.getTranslateX() + speed*3/10);
            temple_middle.setTranslateX(temple_middle.getTranslateX() + speed*2/5);
            temple_front.setTranslateX(temple_front.getTranslateX() + speed*1/2);
        }
        if(right) { // if button to go right, "D", is pressed
//            if(middleGround_1.getTranslateX() < player.getX() - (width * 2))
//            {
//                middleGround_2.setTranslateX(middleGround_1.getTranslateX() + (width *4));
//            }
//            if(middleGround_2.getTranslateX() < player.getX() - (width * 2))
//            {
//                middleGround_1.setTranslateX(middleGround_2.getTranslateX() + (width * 4));
//            }
            left_mountains_4.setTranslateX(left_mountains_4.getTranslateX() - speed/5);
            right_mountains_4.setTranslateX(right_mountains_4.getTranslateX() - speed/5);
            temple_back.setTranslateX(temple_back.getTranslateX() - speed*3/10);
            temple_middle.setTranslateX(temple_middle.getTranslateX() - speed*2/5);
            temple_front.setTranslateX(temple_front.getTranslateX() - speed*1/2);
        }
        if(right){
            if(gameRoot.getTranslateX() * -1 > 0) ; //todo
        }
    }
}
