package org.openjfx.GameComponent;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GuidedRocket extends PlayerEquipment {
    private final int TOTAL_LIFETIME = 3;
    private final double unitTime = 1.0;
    private int lifeTime;
    private double x_enemy;
    private double y_enemy;
    private double dist_x;
    private double dist_y;
    private double hipo;
    private Enemy targetEnemy;
    private int rotate = 0;
    boolean isObjectInScene;

    GuidedRocket(double width, double height, ImagePattern asset) {
        super(width, height, "guidedRocket");
        hitBoxes = new Rectangle[1];
        speed = magicConverter(10);
        this.height = magicConverter(45);
        this.width = magicConverter(45);
        lifeTime = 0;
        hitBoxes[0] = new ComponentHitBoxRectangle(this.width, this.height, "playerEquipment", "guidedRocket");
        body = new Rectangle(this.width, this.height);
        body.setFill(asset);
    }

    public void chooseTarget(ArrayList<GameComponent> gameComponents) {
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < gameComponents.size(); i++) {
            if (gameComponents.get(i) instanceof Enemy) {
                Enemy enemy = (Enemy) gameComponents.get(i);

                double temp_x_enemy = enemy.getX() + enemy.getWidth() / 2;
                double temp_y_enemy = enemy.getY() + enemy.getHeight() / 2;
                double temp_dist_x = temp_x_enemy - this.getX();
                double temp_dist_y = temp_y_enemy - this.getY();
                double temp_hipo = Math.sqrt(Math.pow(temp_dist_x, 2) + Math.pow(temp_dist_y, 2));
                if (temp_hipo < minDistance) {
                    minDistance = temp_hipo;
                    targetEnemy = enemy;
                }
            }
        }
        if (targetEnemy != null)
            isObjectInScene = targetEnemy.getX() <= gameRoot.getWidth() - gameRoot.getTranslateX() && targetEnemy.getX() > gameRoot.getTranslateX() * -1;
        else
            isObjectInScene = false;
    }

    public void moveGuidedRocket() {

        if (lifeTime > TOTAL_LIFETIME)
            dead = true;
        rotate += 5;
        body.setRotate(rotate);
        firstTime = System.nanoTime() / 1000000000.0; // get time
        passedTime = firstTime - lastTime; // calculate passedTime
        lastTime = firstTime; // reset last time.
        totalPassedTime += passedTime; // calculate total passed time
        if (totalPassedTime > unitTime) { // if 1 second is passed
            totalPassedTime = 0; // reset timer
            lifeTime++;
        }
        if (isObjectInScene) {
            x_enemy = targetEnemy.getX() + targetEnemy.getWidth() / 2;
            y_enemy = targetEnemy.getY() + targetEnemy.getHeight() / 2;
            dist_x = x_enemy - this.getX(); // calculate distance x
            dist_y = y_enemy - this.getY(); // calculate distance y
            hipo = Math.sqrt(Math.pow(dist_x, 2) + Math.pow(dist_y, 2)); // hipotenus of x and y
            speed_x = dist_x / hipo * speed; // calculate speed of x
            speed_y = dist_y / hipo * speed; // calculate speed of y
        } else {
            moveY(1, speed);
        }
        this.setX(this.getX() + speed_x);
        this.setY(this.getY() + speed_y);
    }
}