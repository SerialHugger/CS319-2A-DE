package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class EnemyType1 extends Enemy {
    EnemyType1(double width, double height, String url){
        super(width, height, "player");
        hitBoxes = new Shape[1];
        hitBoxes[0] = new ComponentHitBoxRectangle(width,height,"enemyHitBoxRectangle"); // setup the Rectangle hitbox
        body = new Rectangle(width, height, null); //setup the body
        fillImage(url);
        speed = 25;
        double startingX = (width * 38.4 * -2) + Math.random() * (width * 38.4 * 4);
        double startingY = Math.random() * (height * 36);
        body.setTranslateX(startingX);
        body.setTranslateY(startingY);
        hitBoxes[0].setTranslateX(startingX);
        hitBoxes[0].setTranslateY(startingY);
    }
    public void update( GameComponentFactory GCF, Pane gameRoot, Player player, boolean left){
        double random = Math.random()*1000;
        if(random < 30){
            EnemyBulletType1 enemyBullet = (EnemyBulletType1) GCF.createComponent("enemyBulletType1");
            enemyBullet.toLeft = facingLeft;
            enemyBullet.setX(body.getTranslateX());
            enemyBullet.setY(body.getTranslateY());
            enemyBullet.addShapes(gameRoot);
        }
        if(left) { // if button to go left, "A", is pressed
            if(body.getTranslateX() > player.getX() + (2 * GCF.getWidth())){
                for(int i = 0; i < hitBoxes.length; i ++) {
                    hitBoxes[i].setTranslateX(hitBoxes[i].getTranslateX() - (4 * GCF.getWidth()));
                }
                body.setTranslateX(body.getTranslateX() - (4 * GCF.getWidth()));
            }
        }
        else { // if button to go right, "D", is pressed
            if(body.getTranslateX() < player.getX() - (2 * GCF.getWidth())){
                for(int i = 0; i < hitBoxes.length; i ++) {
                    hitBoxes[i].setTranslateX(hitBoxes[i].getTranslateX() + (4 * GCF.getWidth()));
                }
                body.setTranslateX(body.getTranslateX() + (4 * GCF.getWidth()));
            }
        }
    }
    private void updateHitBoxes(boolean x, boolean positive){
        int movement = 25;
        for(int i = 0; i < hitBoxes.length; i ++) {
            if (!positive) {
                movement = -25;
            } else {
                movement = 25;
            }
            if (x) {
                hitBoxes[i].setTranslateX(hitBoxes[i].getTranslateX() + movement);
            } else {
                hitBoxes[i].setTranslateY(hitBoxes[i].getTranslateY() + movement);
            }
        }
    }
}
