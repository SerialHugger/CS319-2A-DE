package org.openjfx.GameComponent;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Minimap extends Rectangle {
    private Rectangle background;
    private ArrayList<GameComponent> components;
    private double backgroundwidth;
    private double backgroundheight;
    private ArrayList<Rectangle> componentRects;

    public Minimap (){
        this.setVisible(false);
        backgroundwidth = 1920;
        backgroundheight = 270;
        background = new Rectangle(backgroundwidth, backgroundheight);
    }
    public void createMinimap(Pane gameRoot, ArrayList<GameComponent> components, double currentX, double width, double height){
        //System.out.println("Create minimap....");
        this.components = components;
        background.setTranslateX(-currentX);
        background.setTranslateY(height/2 - backgroundheight/2);
        //System.out.println(background.getTranslateX());
        //System.out.println(background.getTranslateY());
        background.setFill(Color.rgb(50,50,50, 0.8));
        componentRects = new ArrayList<Rectangle>();
        background.setVisible(false);
        gameRoot.getChildren().add(background);
        for (GameComponent g : this.components){
            //System.out.println("Iterate components..");
            if (g instanceof Enemy){
                System.out.println("Enemy");
                Rectangle rect = new Rectangle(30, 30);
                rect.setFill(Color.YELLOWGREEN);
                rect.setTranslateX(-currentX + (g.getX() % (width * 4)) / 4);
                rect.setTranslateY(background.getTranslateY() + g.getY() / 4);
                //System.out.println("x: " + rect.getTranslateX());
                //System.out.println("y: " + rect.getTranslateY());
                rect.setVisible(false);
                componentRects.add(rect);
                gameRoot.getChildren().add(rect);
            }
            else if (g instanceof Player){
                //System.out.println("Player");
                Rectangle rect = new Rectangle(40, 40);
                rect.setFill(Color.DARKBLUE);
                rect.setTranslateX(-currentX + (g.getX() % (width * 4)) / 4);
                rect.setTranslateY(background.getTranslateY() + g.getY() / 4);
                //System.out.println("x: " + rect.getTranslateX());
                //System.out.println("y: " + rect.getTranslateY());
                rect.setVisible(false);
                componentRects.add(rect);
                gameRoot.getChildren().add(rect);
            }
        }
    }
    public void display(Pane gameRoot){
        //System.out.println("Display...");
        background.setVisible(true);
        background.toFront();
        for (Rectangle r : componentRects){
            r.setVisible(true);
            r.toFront();
        }
    }
    public void hide(Pane gameRoot){
        background.setVisible(false);
        for (Rectangle r : componentRects){
            r.setVisible(false);
        }
        gameRoot.getChildren().remove(background);
        for (Rectangle r : componentRects){
            gameRoot.getChildren().remove(r);
        }
    }
}
