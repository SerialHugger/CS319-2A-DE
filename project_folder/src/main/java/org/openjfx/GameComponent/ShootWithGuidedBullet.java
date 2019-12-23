package org.openjfx.GameComponent;

import javafx.scene.layout.Pane;

public class ShootWithGuidedBullet implements Shootable {
    public ShootWithGuidedBullet() {
    }

    public void shoot(GameComponentFactory GCF, Enemy enemy, Pane gameRoot) {
        GuidedBullet enemyBullet = (GuidedBullet) GCF.createComponent("guidedBullet"); // create the bullet
        enemyBullet.facingLeft = enemy.facingLeft; // make it face left
        enemyBullet.setX(enemy.body.getTranslateX()); // set its X
        enemyBullet.setY(enemy.body.getTranslateY()); // set its Y
        enemyBullet.addShapes(gameRoot); // add its shapes to Root
    }
}
