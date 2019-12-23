package org.openjfx.GameComponent;

import javafx.scene.layout.Pane;

public interface Shootable {
    void shoot(GameComponentFactory GCF, Enemy enemy, Pane gameRoot);
}
