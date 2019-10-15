package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class playerSprite extends Sprite {
    playerSprite(int x, int y, int w, int h,  Color color, Pane root) {
        super(x, y, w, h, color, root);
        type = "player";
    }
}
