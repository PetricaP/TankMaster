package paoo.game.animations;

import paoo.core.*;
import paoo.core.json.JsonObject;
import paoo.core.utils.Pair;
import paoo.core.utils.Vector2D;
import paoo.game.DeathListener;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class TankExplosionAnimation implements Entity {
    public TankExplosionAnimation(Vector2D position) {
        this.position = position;
        alive = true;
        ArrayList<Pair<Vector2D, Vector2D>> animationRects = new ArrayList<>();
        Vector2D dimensions = new Vector2D(120, 120);
        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
                animationRects.add(new Pair<>(new Vector2D(i * 120, j * 120), dimensions));
            }
        }
        try {
            animation = new Animation(ImageLoader.getInstance().loadImage("res/images/bullet_explosion.png"),
                    30, animationRects, 0, () -> {
                                                                        System.out.println("Animation finished");
                                                                        alive = false;
                                                                    });
        } catch(IOException e) {
            System.err.println("Couldn't load image source for bullet hit animation");
            System.exit(-1);
        }
        this.position = position;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(animation.getCurrentImage(), (int)(position.x - dimensions.x / 2),
                (int)(position.y - dimensions.y / 2),
                (int)dimensions.x, (int)dimensions.y, null);
    }

    @Override
    public void attachDeathListener(DeathListener listener) {

    }

    @Override
    public String getTag() {
        return "TankExplosion";
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Vector2D getDimensions() {
        return dimensions;
    }

    private static Vector2D dimensions = new Vector2D(100, 100);

    private Animation animation;
    private boolean alive;
    private Vector2D position;

    @Override
    public JsonObject toJson() {
        return null;
    }
}