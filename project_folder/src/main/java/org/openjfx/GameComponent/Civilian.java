package org.openjfx.GameComponent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Civilian extends GameComponent {
    boolean grabbed;
    boolean targeted;
    boolean saved;

    /**
     * constructor for civilian
     * @param width width of the civilian
     * @param height height of the civilian
     * @param asset images for animations
     */
    Civilian (double width, double height, ImagePattern asset){
        super(width, height, "civilian");
        grabbed = false;
        targeted = false;
        saved = false;
        double startingX = (Math.random() * (1920  * 4)) + (1920 * -2) ;
        double startingY = 960;
        body = new Rectangle(width, height, null);
        body.setFill(asset);
        body.setTranslateX(startingX);
        body.setTranslateY(startingY);
        speed_x = 0;
        speed_y = 0;
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxRectangle(width, height, "civilian", type);
        hitBoxes[0].setTranslateX(startingX);
        hitBoxes[0].setTranslateY(startingY);
        maxSpeed = 6;
        acceleration = magicConverter(0.4);
    }

    /**
     * indicates the civilians actions
     * @param GCF needs factory to update civilian's position
     * @param gameRoot needs the pane
     * @param player the player saves civilians needs to interact with the civilian
     * @param left indicates if civilian is going left
     * @param speedFactor an integer which multiplies the speed of the civilian
     */
    public void moveCivilian(GameComponentFactory GCF, Pane gameRoot, Player player, boolean left , int speedFactor){
        if (grabbed){
                speed_x = 0;
                speed_y = 5;
                moveY(-1, speed_y);
        }
        else if (this.getY() < 960){
            speed_y = speed_y + acceleration;
            moveY(1, speed_y);

        }
        else if (this.getY() >= 960){
            if (speed_y > maxSpeed)
                dead = true;
        }
        ComponentHitBoxRectangle temp = ((ComponentHitBoxRectangle) hitBoxes[0]);
        if (temp.isSaved() && this.getY() < 960)
            saved = true;
        else {
            saved = false;
            ((ComponentHitBoxRectangle) hitBoxes[0]).setSaved(false);
        }
        loopAroundTheMap(GCF.width, player, left);

    }
    public void loopAroundTheMap(double bgWidth, Player player, boolean left) {
        //updates the space ships so they loop around map
        if (left) { // if button to go left, "A", is pressed
            if (body.getTranslateX() > player.getX() + (2 * bgWidth)) { // if he enemy ship is width * 2 away from the ship teleport it to other side
                for (Shape hitBox : hitBoxes) {
                    hitBox.setTranslateX(hitBox.getTranslateX() - (4 * bgWidth));
                }
                body.setTranslateX(body.getTranslateX() - (4 * bgWidth));
            }
        } else { // if button to go right, "D", is pressed
            if (body.getTranslateX() < player.getX() - (2 * bgWidth)) { // if he enemy ship is width * 2 away from the ship teleport it to other side
                for (Shape hitBox : hitBoxes) {
                    hitBox.setTranslateX(hitBox.getTranslateX() + (4 * bgWidth));
                }
                body.setTranslateX(body.getTranslateX() + (4 * bgWidth));
            }
        }
    }
    public void setGrabbed(boolean status){
        grabbed = status;
    }


    public boolean isSaved(){return saved;}
}
