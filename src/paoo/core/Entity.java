package paoo.core;

import paoo.core.json.JsonConvertible;
import paoo.core.utils.Vector2D;
import paoo.game.DeathListener;

import java.awt.*;

public interface Entity extends JsonConvertible {
    boolean isAlive();
    void update();
    void draw(Graphics graphics);
    void attachDeathListener(DeathListener listener);

    String getTag();
    Vector2D getPosition();
    Vector2D getDimensions();
}
