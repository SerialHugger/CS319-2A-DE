package org.openjfx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class InteractionHandler {
    private Pane gameRoot; // root of the game
    private GameComponentFactory gameComponentFactory; // Factory for components
    private ArrayList<GameComponent> gameComponents; // array list for updating components
    InteractionHandler(ArrayList<GameComponent> gameComponents, GameComponentFactory gameComponentFactory, Pane gameRoot){
        this.gameRoot = gameRoot;
        this.gameComponentFactory = gameComponentFactory;
        this.gameComponents = gameComponents;
    }
    public boolean update(){
        BooleanProperty isDead = new SimpleBooleanProperty(false);
        gameRoot.getChildren().forEach( i -> { // first loop
            ComponentHitBoxCircle circleTemp; // first temp circle hit box
            ComponentHitBoxRectangle rectangleTemp; // first temp rectangle hit box
            if(i instanceof ComponentHitBoxCircle) {
                //////////////////////
                //// FOR CIRCLES
                //////////////////////
                circleTemp = (ComponentHitBoxCircle)i; // cast first circle
                gameRoot.getChildren().forEach(j -> { // second loop
                    ComponentHitBoxCircle circleTemp2; // second temp circle hit box
                    ComponentHitBoxRectangle rectangleTemp2; // second temp rectangle hit box.
                    if(j instanceof ComponentHitBoxCircle){
                        //////////////////////
                        //// FOR CIRCLES 2
                        //////////////////////
                        circleTemp2 = (ComponentHitBoxCircle)j; // cast second circle
                        if(circleTemp.getType().equals("playerHitBoxCircle")){ // circleTemp is player
                            if(circleTemp2.getType().equals("enemyBulletType2")){ // circleTemp2 is enemyBulletType2
                                if(circleTemp.getBoundsInParent().intersects(circleTemp2.getBoundsInParent())) { // if they intersect
                                    if (!isDead.get()) { // if the player is not dead, kill it.
                                        System.out.println("Player Circle to Bullet Type 2");
                                        isDead.set(true); // make return value true
                                        circleTemp.dead = true; // change circleTemp to dead status.
                                        circleTemp2.dead = true; // change circleTemp2 to dead status
                                    }
                                }
                            }
                        }
                    } else if(j instanceof  ComponentHitBoxRectangle){ // for rectangles
                        //////////////////////
                        //// FOR RECTANGLES 2
                        //////////////////////
                        rectangleTemp2 = (ComponentHitBoxRectangle)j; // cast second rectangle
                        if(circleTemp.getType().equals("playerHitBoxCircle")){ // circleTemp is player
                            if(rectangleTemp2.getType().equals("enemyBulletType1")){ // rectangleTemp2 is enemyBulletType1
                                if(circleTemp.getBoundsInParent().intersects(rectangleTemp2.getBoundsInParent())) {
                                    if (!isDead.get()) { // if the player is not dead, kill it.
                                        System.out.println("Player Circle to Bullet Type 1");
                                        isDead.set(true); // make return value true
                                        circleTemp.dead = true; // change circleTemp to dead status.
                                        rectangleTemp2.dead = true; // change circleTemp to dead status.
                                    }
                                }
                            } else if(rectangleTemp2.getType().equals("enemyHitBoxRectangleType1")){ // rectangleTemp2 is enemyBulletType1
                                if(circleTemp.getBoundsInParent().intersects(rectangleTemp2.getBoundsInParent())) {
                                    if (!isDead.get()) { // if the player is not dead, kill it.
                                        System.out.println("Player Circle to enemy Type 1");
                                        isDead.set(true); // make return value true
                                        circleTemp.dead = true; // change circleTemp to dead status.
                                        rectangleTemp2.dead = true;
                                    }
                                }
                            }

                        }
                    }
                });
            } else if (i instanceof  ComponentHitBoxRectangle){
                //////////////////////
                //// FOR RECTANGLES
                //////////////////////
                rectangleTemp = (ComponentHitBoxRectangle)i; // cast first rectangle
                gameRoot.getChildren().forEach(j -> { // second loop
                    ComponentHitBoxCircle circleTemp2; // second temp circle hit box
                    ComponentHitBoxRectangle rectangleTemp2; // second temp rectangle hit box.
                    if(j instanceof ComponentHitBoxCircle){
                        //////////////////////
                        //// FOR CIRCLES 2
                        //////////////////////
                        circleTemp2 = (ComponentHitBoxCircle)j; // cast second circle
                        if(rectangleTemp.getType().equals("playerHitBoxRectangle")){ // circleTemp is player
                            if(circleTemp2.getType().equals("enemyBulletType2")){ // circleTemp2 is enemyBulletType2
                                if(rectangleTemp.getBoundsInParent().intersects(circleTemp2.getBoundsInParent())) { // if they intersect
                                    if (!isDead.get()) { // if the player is not dead, kill it.
                                        System.out.println("Player Rect  to Bullet Type 2");
                                        isDead.set(true); // make return value true
                                        rectangleTemp.dead = true; // change circleTemp to dead status.
                                        circleTemp2.dead = true; // change circleTemp2 to dead status
                                    }
                                }
                            }
                        }
                    } else if(j instanceof  ComponentHitBoxRectangle){ // for rectangles
                        //////////////////////
                        //// FOR RECTANGLES 2
                        //////////////////////
                        rectangleTemp2 = (ComponentHitBoxRectangle)j; // cast second rectangle
                        if(rectangleTemp.getType().equals("playerHitBoxRectangle")){ // circleTemp is player
                            if(rectangleTemp2.getType().equals("enemyBulletType1")){ // rectangleTemp2 is enemyBulletType1
                                if(rectangleTemp.getBoundsInParent().intersects(rectangleTemp2.getBoundsInParent())) {
                                    if (!isDead.get()) { // if the player is not dead, kill it.
                                        System.out.println("Player Rect to enemy Bullet Type 1");
                                        isDead.set(true); // make return value true
                                        rectangleTemp.dead = true; // change rectangleTemp to dead status.
                                        rectangleTemp2.dead = true; // change rectangleTemp2 to dead status.
                                        System.out.println(rectangleTemp2.dead);
                                    }
                                }
                            } else if(rectangleTemp2.getType().equals("enemyHitBoxRectangleType1")){ // rectangleTemp2 is enemyBulletType1
                                if(rectangleTemp.getBoundsInParent().intersects(rectangleTemp2.getBoundsInParent())) {
                                    if (!isDead.get()) { // if the player is not dead, kill it.
                                        System.out.println("Player Rect to enemy 1");
                                        isDead.set(true); // make return value true
                                        rectangleTemp.dead = true; // change rectangleTemp to dead status.
                                        rectangleTemp2.dead = true; // change rectangleTemp2 to dead status.
                                    }
                                }
                            }

                        } else if( rectangleTemp.getType().equals("playerBullet")) { // rectangleTemp is playerBullet
                            if (rectangleTemp2.getType().equals("enemyBulletType1")) { // rectangleTemp2 is enemyBulletType1
                                if (rectangleTemp.getBoundsInParent().intersects(rectangleTemp2.getBoundsInParent())) {
                                    if (!isDead.get()) { // if the player is not dead, kill it.
                                        System.out.println("PlayerBullet to enemy Bullet Type 1");
                                        isDead.set(true); // make return value true
                                        rectangleTemp.dead = true; // change rectangleTemp to dead status.
                                        rectangleTemp2.dead = true; // change rectangleTemp2 to dead status.
                                    }
                                }
                            } else if (rectangleTemp2.getType().equals("enemyHitBoxRectangleType1")) { // rectangleTemp2 is enemyBulletType1
                                if (rectangleTemp.getBoundsInParent().intersects(rectangleTemp2.getBoundsInParent())) {
                                    if (!isDead.get()) { // if the player is not dead, kill it.
                                        System.out.println("PlayerBullet to enemy 1");
                                        isDead.set(true); // make return value true
                                        rectangleTemp.dead = true; // change rectangleTemp to dead status.
                                        rectangleTemp2.dead = true; // change rectangleTemp2 to dead status.
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        return isDead.get();
    }
}
