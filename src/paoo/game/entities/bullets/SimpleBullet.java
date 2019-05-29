package paoo.game.entities.bullets;

import paoo.core.utils.Vector2D;

import java.awt.*;

public class SimpleBullet extends Bullet {
    public SimpleBullet(Vector2D position, int direction, String tag, int bulletSpeed) {
        super(position, new Vector2D(6, 6), direction, bulletSpeed, tag);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillOval((int)getPosition().x, (int)getPosition().y,
                          (int)getDimensions().x, (int)getDimensions().y);
    }

    @Override
    public int getDamage() {
        return 1;
    }

    @Override
    public void setPosition(Vector2D position) {}
}
