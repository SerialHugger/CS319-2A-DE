package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JavaFX App
 */
public class App extends Application {

        private Pane root = new Pane();

        private double t = 0;
        private double t_2 = 0;
        private int livesCount = 3;
        private int count = 0;
        private int width = 1920; // (int)Screen.getPrimary().getVisualBounds().getWidth(); // deafult screen width // adjust here manually for now
        private int height = 1080; // (int)Screen.getPrimary().getVisualBounds().getMaxY(); // default screen height // adjust here manually for now
        private playerSprite player = new playerSprite(400, 750, 40, 40, Color.PURPLE, root);
        private bgSprite bgSprite = new bgSprite(width*2, 0, width*4, height, root, width, height); // default bgSprite, creates more content inside its class
        private BooleanProperty upPressed = new SimpleBooleanProperty();
        private BooleanProperty downPressed = new SimpleBooleanProperty();
        private BooleanProperty leftPressed = new SimpleBooleanProperty();
        private BooleanProperty rightPressed = new SimpleBooleanProperty();
        private BooleanProperty spacePressed = new SimpleBooleanProperty();
        private BooleanProperty qPressed = new SimpleBooleanProperty();
        private BooleanProperty ePressed = new SimpleBooleanProperty();
        private BooleanBinding anyPressed = upPressed.or(downPressed).or(leftPressed).or(rightPressed).or(spacePressed);
        private boolean rush = true;

        private Parent createContent(){
            File currentDirFile = new File(".");
            String helper = currentDirFile.getAbsolutePath();
            System.out.println(helper);
            System.out.println(width);
            System.out.println(height);
            root.setPrefSize(width, height);
            bgSprite.createMain();
            root.getChildren().add(player);
            AnimationTimer timer =  new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update();
                }
            };
            timer.start();

            nextLevel();

            return root;
        }

        private void nextLevel() {
           // for (int i = 0; i < 9; i++) {
           //     root.getChildren().add(new enemySprite( 1800, 90 + i*100, 30, 30, Color.RED, root));
           // }
        }

        private List<Sprite> sprites() {
            return root.getChildren().stream().map(n -> (Sprite)n).collect(Collectors.toList());
        }

        private void update() {
            t += 0.016;
            count++;
            if(count > 100) {
                count = 0;
                rush = true;
            }
            if(t_2 != 0)
            {
                t_2 -= 0.05;
            }
            if(!player.dead) {
                if (upPressed.get()) {
                    player.moveUp(5);
                }
                if (downPressed.get()) {
                    player.moveDown(5);
                }
                // player will move a little to left, if looking right it will change places in map // todo
                if (leftPressed.get()) {
                    bgSprite.updateAll(true);
                }
                // player will move a little to right, if looking left it will change places in map // todo
                if (rightPressed.get()) {
                   bgSprite.updateAll(false);
                }
                if (spacePressed.get()) {
                    if(t_2 <= 0) {
                        shoot(player);
                        t_2 = 0.5;
                    }
                }
                if(!leftPressed.get() && !rightPressed.get())
                {
                    // todo
                }
                if(ePressed.get())
                {
                    if(rush) {
                        bgSprite.rush(true);
                        rush = false;
                    }

                }
                if(qPressed.get())
                {
                    if(rush) {
                        bgSprite.rush(false);
                        rush = false;
                    }
                }
            }
            sprites().forEach(s -> {
                if(s.type.equals("playerbullet")) {
                    s.moveRight(25);
                    s.setStroke(Color.BLACK);
                }
            });
            root.getChildren().removeIf( n -> {
                Sprite s = (Sprite) n;
                return s.dead;
            });

            if( t > 1 ) {
                t = 0;
            }
        }
        private void shoot(Sprite who) {
            Sprite s = new bulletSprite((int) who.getTranslateX()+40,  (int) who.getTranslateY()+20, 10, 5, who.type + "bullet", Color.DARKSALMON, player.getTranslateX(), player.getTranslateY(), root);
            root.getChildren().add(s);
        }

        @Override
        public void start(Stage primaryStage) throws Exception{
            Scene scene = new Scene(createContent());

            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.W) {
                    upPressed.set(true);
                }
                if (e.getCode() == KeyCode.S) {
                    downPressed.set(true);
                }
                if (e.getCode() == KeyCode.A) {
                    leftPressed.set(true);
                }
                if (e.getCode() == KeyCode.D) {
                    rightPressed.set(true);
                }
                if (e.getCode() == KeyCode.SPACE) {
                    spacePressed.set(true);
                }
                if(e.getCode() == KeyCode.Q){
                    qPressed.set(true);
                }
                if(e.getCode() == KeyCode.E){
                    ePressed.set(true);
                }
            });
            scene.setOnKeyReleased(e -> {
                if (e.getCode() == KeyCode.W) {
                    upPressed.set(false);
                }
                if (e.getCode() == KeyCode.S) {
                    downPressed.set(false);
                }
                if (e.getCode() == KeyCode.A) {
                    leftPressed.set(false);
                }
                if (e.getCode() == KeyCode.D) {
                    rightPressed.set(false);
                }
                if (e.getCode() == KeyCode.SPACE) {
                    spacePressed.set(false);
                }
                if(e.getCode() == KeyCode.Q){
                    qPressed.set(false);
                }
                if(e.getCode() == KeyCode.E){
                    ePressed.set(false);
                }
            });
            primaryStage.setFullScreen(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        }


        public static void main(String[] args) {
            launch(args);
        }


    }