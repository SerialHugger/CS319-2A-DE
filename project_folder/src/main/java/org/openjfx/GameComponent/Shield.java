package org.openjfx.GameComponent;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Shield extends PlayerEquipment {
    int shieldStatus = 0; // if 0 its
    private int life;
    private final int overChargeLimit = 5;
    private int overChargeCount;
    boolean overChargeActive;

    Shield(double width, double height, ImagePattern[] assets) {
        super(width, height, "shield");
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxCircle(magicConverter(55), "shield", "shield");
        life = 7;
        totalPassedTime = 0;
        overChargeCount = 0;
        overChargeActive = false;
        this.body = new Circle(magicConverter(55));
        this.height = magicConverter(110);
        this.width = magicConverter(110);
        animationFrames = new ImagePattern[9];
        for (int i = 0; i < 9; i++)
            animationFrames[i] = assets[i];
        this.body.setFill(animationFrames[0]);

        // set X and Y
    }

    public void moveShield(Player player) {
        System.out.println(player.isShieldActive);
        setX(player.body.getTranslateX() + player.width / 2);
        setY(player.body.getTranslateY() + player.height / 2);
        if (!overChargeActive) {
            checkDeath();
            totalPassedTime=0;
        }
        else {
            firstTime = System.nanoTime() / 10000000000.0; // get time
            passedTime = firstTime - lastTime; // calculate passedTime
            lastTime = firstTime; // reset last time.
            totalPassedTime += passedTime; // calculate total passed time
            if (totalPassedTime > 0.03) {
                totalPassedTime = 0;
                player.isShieldActive = false;
                dead = true;
            }
        }
        if (currentState == 8)
            currentState = 3;
        currentState++;
        if(!overChargeActive) {
            currentState = 0;
            if (dead)
                player.isShieldActive = false;
        }
        body.setFill(animationFrames[currentState]);
    }

    public void overCharge(Player player) {
        if (life > 1)
            overChargeCount = 7 - life;
        else
            overChargeCount = 5;
        overChargeActive = true;
        totalPassedTime = 0;
        gameRoot.getChildren().remove(body);
        gameRoot.getChildren().remove(hitBoxes[0]);
        body = new Circle(this.width * overChargeCount);
        body.setFill(animationFrames[1]);
        hitBoxes[0] = new ComponentHitBoxCircle(this.width * overChargeCount - magicConverter(20 * overChargeCount), "shield", "overCharge");
        addShapes(gameRoot);
        setX(player.body.getTranslateX() + player.width / 2);
        setY(player.body.getTranslateY() + player.height / 2);
    }
    private void checkDeath() {
        for (int i = 0; i < hitBoxes.length; i++) {
            if (hitBoxes[i] instanceof ComponentHitBoxCircle) {
                ComponentHitBoxCircle temp = ((ComponentHitBoxCircle) hitBoxes[i]);
                if (temp.isDead()) {
                    life -= 1;
                    temp.dead = false;
                }
            } else if (hitBoxes[i] instanceof ComponentHitBoxRectangle) {
                ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBoxes[i]);
                if (temp.isDead()) {
                    life -= 1;
                    temp.dead = false;
                }
            }
            if (life == 0) {
                dead = true;
                break;
            }
        }
    }
}
