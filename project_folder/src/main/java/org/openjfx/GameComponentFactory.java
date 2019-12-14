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
            temp = new PlayerBullet(width / 96, height / 216, "empty", true, gameComponents.get(0).speed);
        } else if (type.equals("atlas")) {
            temp = new Atlas(width, height, "Assets\\Enemies\\atlas.png");
        } else if (type.equals("dodger")) {
            temp = new Dodger(width, height, "Assets\\pacman.png");
        } else if (type.equals("dividus")) {
            temp = new Dividus(width, height, "Assets\\pacman.png" );
        } else if (type.equals("dienamite")) {
            temp = new Dienamite(width, height, "Assets\\Spaceship.png" );
        } else if (type.equals("laserBullet")) {
            temp = new laserBullet(width / 384, height / 108, "empty", true, gameComponents.get(0)); // 0 is player //274 //154
        } else if (type.equals("enemySelfDestruct")) {
            temp = new EnemySelfDestruct(75, 75, "");
        } else if (type.equals("guidedbullet")) {
            temp = new GuidedBullet(width / 384, height / 108, "empty", true, gameComponents.get(0));
        }
        gameComponents.add(temp);
        return temp;
    }
}
