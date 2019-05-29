package paoo.game.animations;

import paoo.core.Animation;
import paoo.core.Entity;
import paoo.core.audio.AudioPlayer;
import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.DeathListener;

import java.awt.*;

public class AnimationEntity implements Entity {
    AnimationEntity(Vector2D position, String soundPath, float baseSoundVolume) {
        this.position = position;
        this.baseSoundVolume = baseSoundVolume;
        alive = true;
        audioPlayer = new AudioPlayer(soundPath);
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void update() {
        if(!firstUpdate) {
            firstUpdate = true;
            float distance = (float)Math.sqrt((new Vector2D(position).sub(playerPosition)).squareLength() / 1000.0f);
            float volume = baseSoundVolume / distance;
            if(volume > 1.0f) {
                volume = 1.0f;
            }
            audioPlayer.setVolume(volume);
            audioPlayer.play();
        }
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

    public void setPlayerPosition(Vector2D playerPosition) {
        this.playerPosition = playerPosition;
    }

    @Override
    public JsonObject toJson() {
        return JsonObject.build()
                .addAttribute("position", position.toJson())
                .addAttribute("frame", animation.getCurrentFrame())
                .getObject();
    }

    AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    void setAnimation(Animation animation) {
        this.animation = animation;
    }

    private static Vector2D dimensions = new Vector2D(70, 70);

    private float baseSoundVolume;
    private Vector2D playerPosition;
    private boolean firstUpdate = false;
    private AudioPlayer audioPlayer;
    private Animation animation;
    private boolean alive;
    private Vector2D position;
}
