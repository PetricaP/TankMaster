package paoo.core;

import paoo.game.DeathListener;

import java.awt.*;

public interface Entity {
    boolean isAlive();
    void update();
    void draw(Graphics graphics);
    void attachDeathListener(DeathListener listener);

    String getTag();
    Vector2D getPosition();
    Vector2D getDimensions();
}
