package paoo.game;


import paoo.core.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

class BulletExplosionAnimation implements Entity {
    BulletExplosionAnimation(Vector2D position) {
        this.position = position;
        this.dimensions = new Vector2D(30, 30);
        alive = true;
        ArrayList<Pair<Vector2D, Vector2D>> animationRects = new ArrayList<>();
        Vector2D dimensions = new Vector2D(120, 120);
        for(int i = 0; i < 4; ++i) {
            animationRects.add(new Pair<>(new Vector2D(i * 120, 0), dimensions));
        }
        try {
            animation = new Animation(ImageLoader.getInstance().loadImage("res/images/bullet_explosion.png"),
                    20, animationRects, 0, () -> {
                                                                        System.out.println("Animation finished");
                                                                        alive = false;
                                                                    });
        } catch(IOException e) {
            System.err.println("Couldn't load image source for bullet hit animation");
            System.exit(-1);
        }
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
        graphics.drawImage(animation.getCurrentImage(), position.x - dimensions.x / 2,
                position.y - dimensions.y / 2,
                dimensions.x, dimensions.y, null);
    }

    @Override
    public void attachDeathListener(DeathListener listener) {
    }

    @Override
    public String getTag() {
        return "BulletAnimation";
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Vector2D getDimensions() {
        return dimensions;
    }

    private boolean alive;
    private Vector2D dimensions;
    private Vector2D position;
    private Animation animation;
}
