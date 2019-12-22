package org.openjfx;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.util.ArrayList;

public class GameComponentFactory {
    double width; // width of the game
    double height; // height of the game
    ArrayList<GameComponent> gameComponents; // game components of the game.
    /*
   ////////////////////////
    Name imagePatterns Here
   ////////////////////////
    */
    /// ARRAYS
    ImagePattern[] playerImage = new ImagePattern[2];
    ImagePattern[] playerBulletImage = new ImagePattern[2];
    ImagePattern[] selfDestructImage = new ImagePattern[8];
    ImagePattern[] explosionImage = new ImagePattern[8];
    ImagePattern[] atlasImage = new ImagePattern[3];
    ImagePattern[] dienamiteImage = new ImagePattern[9];
    ImagePattern[] divingWindImage = new ImagePattern[2];

    /// SINGLES
    ImagePattern dodgerImage;
    ImagePattern dividusImage;
    ImagePattern speedRunnerImage;
    ImagePattern laserBulletImage;
    ImagePattern guidedBulletImage;
    ImagePattern guidedRocketImage;
    ImagePattern civilianImage;
    ImagePattern bossImage;

    GameComponentFactory(double width, double height, ArrayList<GameComponent> gameComponents) {
        this.width = width;
        this.height = height;
        this.gameComponents = gameComponents;
        openAssets();
    }

    /*
     * Creates components with given type and adds it to the gameComponents array
     */
    public GameComponent createComponent(String type) {
        GameComponent temp = null;
        if (type.equals("player")) {
            temp = new Player(width, height, playerImage);
        } else if (type.equals("playerBullet")) {
            temp = new PlayerBullet(width, height, playerBulletImage, true, gameComponents.get(0).speed);
        } else if (type.equals("atlas")) {
            temp = new Atlas(width, height, atlasImage);
        } else if (type.equals("dodger")) {
            temp = new Dodger(width, height, dodgerImage);
        } else if (type.equals("dividus")) {
            temp = new Dividus(width, height, dividusImage );
        } else if (type.equals("dienamite")) {
            temp = new Dienamite(width, height, dienamiteImage );
        } else if (type.equals("divingWind")) {
            temp = new DivingWind(width, height, divingWindImage);
        } else if( type.equals("speedRunner")){
            temp = new SpeedRunner(width , height , speedRunnerImage);
        } else if (type.equals("laserBullet")) {
            temp = new LaserBullet(width, height, laserBulletImage, true, gameComponents.get(0)); // 0 is player //274 //154
        } else if (type.equals("guidedBullet")) {
            temp = new GuidedBullet(width, height, guidedBulletImage, true, gameComponents.get(0));
        } else if (type.equals("enemySelfDestruct")) {
            temp = new EnemySelfDestruct(width, height, selfDestructImage, true);
        } else if(type.equals("explode")) {
            temp = new EnemySelfDestruct(width, height, explosionImage, false);
        } else if (type.equals("bomb")) {
            temp = new Bomb(width, height, null, (Player)gameComponents.get(0));
        } else if (type.equals("shield")) {
            temp = new Shield( width, height, "empty");
        } else if (type.equals("engineBlast")) {
            temp = new EngineBlast(40,50, "empty");
        } else if (type.equals("collectible")) {
            temp = new Collectible(40, 50, "empty");
        } else if (type.equals("melee")) {
            temp = new Melee(5, 150, "empty");
        } else if (type.equals("barrier")) {
            temp = new Barrier(700, 700, "empty");
        }  else if (type.equals("boss")) {
            temp = new Boss(width, height, bossImage);
        } else if (type.equals("guidedRocket")) {
            temp = new GuidedRocket(width, height, guidedRocketImage);
        }
        gameComponents.add(temp);
        return temp;
    }
    private void openAssets(){
        //Open arrays
        playerImage[0] = openAsset("Assets\\spaceship_right.png");
        playerImage[1] = openAsset("Assets\\spaceship_left.png");
        playerBulletImage[0] = openAsset("Assets\\playerBullet\\playerBullet_1.png");
        playerBulletImage[1] = openAsset("Assets\\playerBullet\\playerBullet_2.png");
        for(int i = 0; i < 8; i++) {
            selfDestructImage[i] = openAsset("Assets\\Enemies\\enemySelfDestruct\\selfDestruct_" + (i+1)+ ".png");
        }
        for(int i = 0; i < 8; i++) {
            explosionImage[i] = openAsset("Assets\\Enemies\\explosion\\explosion_" + (i+1) + ".png");
        }
        atlasImage[0] = openAsset("Assets\\Enemies\\atlas\\atlas.png");
        atlasImage[1] = openAsset("Assets\\Enemies\\atlas\\atlas_toLeft.png");
        atlasImage[2] = openAsset("Assets\\Enemies\\atlas\\atlas_toRight.png");
        for(int i = 0; i < 9; i++){
            dienamiteImage[i] = openAsset("Assets\\Enemies\\dienamite\\dienamite_" + (i+1) + ".png");
        }
        playerBulletImage[0] = openAsset("Assets\\playerBullet\\playerBullet_1.png");
        playerBulletImage[1] = openAsset("Assets\\playerBullet\\playerBullet_2.png");
        divingWindImage[0] = openAsset("Assets\\ragetrollface.png");
        divingWindImage[1] = openAsset("Assets\\calmface.png");
        //Open singles
        dodgerImage = openAsset("Assets\\pacman.png");
        dividusImage = openAsset("Assets\\pacman.png");
        speedRunnerImage = openAsset("Assets\\alpaka.png");
        guidedBulletImage = openAsset("Assets\\guided_ball.png");
        guidedRocketImage = openAsset("Assets\\light_saber.png");
        laserBulletImage = openAsset("Assets\\Enemies\\enemyBullet.png");       
        civilianImage = openAsset("Assets\\Civilian.png");
        bossImage = openAsset("Assets\\array_tuezuen.png");

    }

    /*
     * Inserts image in assetLocation to body and returns the imagepattern.
     */
    public ImagePattern openAsset(String assetLocation){
        ImagePattern imagePattern;
        try {
            // set background image
            FileInputStream inputstream = new FileInputStream(assetLocation);
            Image image = new Image(inputstream);
            imagePattern = new ImagePattern(image);
            inputstream.close();
        } catch ( Exception e ) {
            try{
                FileInputStream inputstream = new FileInputStream(assetLocation.replace("\\","/"));
                Image image = new Image(inputstream);
                imagePattern = new ImagePattern(image);
                inputstream.close();
            } catch ( Exception e2) {
                System.out.println(e2.toString());
                return null;
            }
        }
        return  imagePattern;
    }
}
