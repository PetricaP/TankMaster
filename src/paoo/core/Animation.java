package paoo.core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
    public Animation(BufferedImage spriteSheet, long frameTime,
                     ArrayList<Pair<Vector2D, Vector2D>> animationRects, int firstRepeating,
                     AnimationListener listener) {
        this.spriteSheet = spriteSheet;
        this.frameTime = frameTime;
        rects = animationRects;
        currentFrame = 0;

        imageCache = new BufferedImage[animationRects.size()];

        imageCache[currentFrame] = spriteSheet.getSubimage(rects.get(currentFrame).first.x,
                                               rects.get(currentFrame).first.y,
                                               rects.get(currentFrame).second.x,
                                               rects.get(currentFrame).second.y);
        this.firstRepeating = firstRepeating;
        this.listener = listener;
    }

    private long lastTime = 0;
    public BufferedImage getCurrentImage() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastTime > frameTime) {
            ++currentFrame;
            if(currentFrame == rects.size()) {
                currentFrame = firstRepeating;
                if(listener != null) {
                    listener.onEnd();
                }
            }
            if(imageCache[currentFrame] == null) {
                imageCache[currentFrame] = spriteSheet.getSubimage(rects.get(currentFrame).first.x,
                        rects.get(currentFrame).first.y,
                        rects.get(currentFrame).second.x,
                        rects.get(currentFrame).second.y);
            }
            lastTime = currentTime;
        }
        return imageCache[currentFrame];
    }

    private AnimationListener listener;
    private int firstRepeating;
    private final long frameTime;
    private BufferedImage spriteSheet;
    private int currentFrame;
    private ArrayList<Pair<Vector2D, Vector2D>> rects;
    private BufferedImage[] imageCache;
}
