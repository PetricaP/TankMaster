package paoo.game.animations;

import paoo.core.*;
import paoo.core.utils.Pair;
import paoo.core.utils.Vector2D;

import java.io.IOException;
import java.util.ArrayList;

public class TankExplosionAnimation extends AnimationEntity {
    public TankExplosionAnimation(Vector2D position, int firstFrame) {
        super(position, "/sounds/explosion.wav", 2.0f);
        ArrayList<Pair<Vector2D, Vector2D>> animationRects = new ArrayList<>();
        Vector2D dimensions = new Vector2D(120, 120);
        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
                animationRects.add(new Pair<>(new Vector2D(i * 120, j * 120), dimensions));
            }
        }
        try {
            setAnimation(new Animation(ImageLoader.getInstance().loadImage("res/images/bullet_explosion.png"),
                    30, animationRects, firstFrame, 0, () -> {
                setAlive(false);
                getAudioPlayer().close();
            }));
        } catch(IOException e) {
            System.err.println("Couldn't load image source for bullet hit animation");
            System.exit(-1);
        }
    }
}
