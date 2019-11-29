package org.openjfx;

import java.util.ArrayList;

public class GameComponentFactory {
    double width; // width of the game
    double height; // height of the game
    ArrayList<GameComponent> gameComponents; // game components of the game.

    GameComponentFactory(double width, double height, ArrayList<GameComponent> gameComponents) {
        this.width = width;
        this.height = height;
        this.gameComponents = gameComponents;
    }

    /*
     * Creates components with given type and adds it to the gameComponents array
     */
    public GameComponent createComponent(String type) {
        GameComponent temp = null;
        if (type.equals("player")) {
            temp = new Player(width / 12.8, height / 15.4, "Assets\\spaceship");
        } else if (type.equals("playerBullet")) {
            temp = new PlayerBullet(width / 96, height / 216, "empty", true);
        } else if (type.equals("atlas")) {
            temp = new Atlas(width / 60.95, height / 18, "Assets\\Enemies\\atlas.png");
        } else if (type.equals("enemyType2")) {
            temp = new EnemyType2(width / 38.4, height / 36, "Assets\\pacman.png");
        } else if (type.equals("enemyBulletType1")) {
            temp = new EnemyBulletType1(width / 274, height / 154, "empty", true, gameComponents.get(0)); // 0 is player
        }
        gameComponents.add(temp);
        return temp;
    }

    public double getWidth() {
        return width;
    } // return the width of the game.
}
