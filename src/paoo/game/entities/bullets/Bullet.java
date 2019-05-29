package paoo.game.entities.bullets;

import paoo.core.collisions.AABBCollider;
import paoo.core.collisions.Collidable;
import paoo.core.collisions.Collider;
import paoo.core.collisions.Collision;
import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.animations.BulletExplosionAnimation;
import paoo.game.DeathListener;
import paoo.game.Direction;

public abstract class Bullet implements Collidable {
    public Bullet(Vector2D position, Vector2D dimensions, int direction, int speed, String tag) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        switch(direction) {
            case Direction.UP:
                this.velocity = new Vector2D(0, -speed);
                break;
            case Direction.DOWN:
                this.velocity = new Vector2D(0, speed);
                break;
            case Direction.LEFT:
                this.velocity = new Vector2D(-speed, 0);
                break;
            case Direction.RIGHT:
                this.velocity = new Vector2D(speed, 0);
                break;
        }
        this.dimensions = dimensions;
        collider = new AABBCollider(new Vector2D(position), this.dimensions);
        this.tag = tag;
        alive = true;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Vector2D getDimensions() {
        return dimensions;
    }

    private long timeCreated = System.currentTimeMillis();
    @Override
    public void update() {
        position.add(velocity);
        collider.setPosition(new Vector2D(position));
        long timeCurrent = System.currentTimeMillis();
        if(timeCurrent - timeCreated > 5000) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        String tag = collision.getOtherObject().getTag();
        if(!getTag().contains(tag)) {
            alive = false;
            if(listener != null) {
                listener.onDeath(new BulletExplosionAnimation(
                        new Vector2D(position).add(new Vector2D(dimensions).div(2).add(new Vector2D(-5, -5))), 0));
            }
        }
    }

    @Override
    public Collider getCollider() {
        return collider;
    }

    void setDimension(Vector2D dimensions) {
        this.dimensions = dimensions;
        collider.setDimensions(dimensions);
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    @Override
    public void attachDeathListener(DeathListener listener) {
        this.listener = listener;
    }

    public abstract int getDamage();

    @Override
    public JsonObject toJson() {
        return JsonObject.build()
                .addAttribute("position", getPosition().toJson())
                .addAttribute("direction", getDirection())
                .addAttribute("tag", getTag())
                .addAttribute("speed", speed).getObject();
    }

    public int getDirection() {
        return direction;
    }

    public static Bullet create(Vector2D position, int direction, String tag, int bulletSpeed) {
        Bullet bullet = null;
        if(tag.contains("Simple")) {
            bullet = new SimpleBullet(position, direction, tag, bulletSpeed);
        }
        if(tag.contains("Fire")) {
            bullet = new FireBall(position, direction, tag, bulletSpeed);
        }
        assert(bullet != null);
        bullet.tag = tag;
        return bullet;
    }

    private int speed;
    private int direction;
    private DeathListener listener = null;
    private boolean alive;
    private Collider collider;
    private Vector2D position;
    private Vector2D dimensions;
    private Vector2D velocity;
    private String tag;
}
