package paoo.game.animations;

import paoo.core.Animation;
import paoo.core.ImageLoader;
import paoo.core.utils.Pair;
import paoo.core.utils.Vector2D;

import java.io.IOException;
import java.util.ArrayList;

public class BurnAnimation extends AnimationEntity {
    public BurnAnimation(Vector2D position, int firstFrame) {
        super(position, "/sounds/burn.wav", 0.2f);
        ArrayList<Pair<Vector2D, Vector2D>> animationRects = new ArrayList<>();
        Vector2D dimensions = new Vector2D(250, 350);
        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 3; ++j) {
                animationRects.add(new Pair<>(new Vector2D(i * 250, j * 350), dimensions));
            }
        }
        try {
            setAnimation(new Animation(ImageLoader.getInstance().loadImage("res/images/fire_animation.png"),
                    50, animationRects, firstFrame, 0, () -> {
                setAlive(false);
                getAudioPlayer().close();
            }));
        } catch(IOException e) {
            System.err.println("Couldn't load image source for bullet hit animation");
            System.exit(-1);
        }
    }
}
