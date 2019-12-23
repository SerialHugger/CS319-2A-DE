package org.openjfx.GameComponent;

import javafx.scene.layout.Pane;

public class ShootWithLaserBullet implements Shootable {
    public ShootWithLaserBullet() {
    }

    /**
     * This method is the implementation of shooting using laser bullet
     * @param GCF GameComponentFactory to add the bullet to the game
     * @param enemy Enemy will be used to set the initial position of the bullet
     * @param gameRoot Pane
     */
    public void shoot(GameComponentFactory GCF, Enemy enemy, Pane gameRoot) {
        LaserBullet enemyBullet = (LaserBullet) GCF.createComponent("laserBullet"); // create the bullet
        enemyBullet.facingLeft = enemy.facingLeft; // make it face left
        enemyBullet.setX(enemy.body.getTranslateX()); // set its X
        enemyBullet.setY(enemy.body.getTranslateY()); // set its Y
        enemyBullet.addShapes(gameRoot); // add its shapes to Root
    }
}
