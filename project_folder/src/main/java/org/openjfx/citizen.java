package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class citizen extends Sprite {

    citizen(int x, int y, int w, int h, Color color, Pane root) {
        super(x,y,w, h, color, root);
        type = "citizen";
    }
}
