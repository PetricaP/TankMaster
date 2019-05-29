package paoo.game.state;

import java.awt.*;

public interface GameState {
    void update();
    void draw(Graphics g);
    void cleanUp();
}
