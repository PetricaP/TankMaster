package paoo.game;

import paoo.core.Vector2D;

import java.awt.*;

public class SimpleBullet extends Bullet {
    SimpleBullet(Vector2D position, int direction, String tag) {
        super(position, new Vector2D(3, 3), direction, 3, tag);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillOval(getPosition().x, getPosition().y, getDimensions().x, getDimensions().y);
    }

}
