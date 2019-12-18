package org.openjfx;

import javafx.scene.layout.Pane;

public class ShootWithLaserBullet implements Shootable {
    public ShootWithLaserBullet(){}

    public void shoot(GameComponentFactory GCF, Enemy enemy , Pane gameRoot){
        LaserBullet enemyBullet = (LaserBullet) GCF.createComponent("laserBullet"); // create the bullet
        enemyBullet.facingLeft = enemy.facingLeft; // make it face left
        enemyBullet.setX(enemy.body.getTranslateX()); // set its X
        enemyBullet.setY(enemy.body.getTranslateY()); // set its Y
        enemyBullet.addShapes(gameRoot); // add its shapes to Root

    }
}
