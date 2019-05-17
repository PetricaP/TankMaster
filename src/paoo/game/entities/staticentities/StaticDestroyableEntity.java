package paoo.game.entities.staticentities;

import paoo.core.collisions.AABBCollider;
import paoo.core.collisions.Collidable;
import paoo.core.collisions.Collider;
import paoo.core.collisions.Collision;
import paoo.core.utils.Vector2D;
import paoo.game.DeathListener;
import paoo.game.entities.bullets.Bullet;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class StaticDestroyableEntity implements Collidable {
    StaticDestroyableEntity(Vector2D position, Vector2D dimensions, BufferedImage image, int health) {
        alive = true;
        this.position = position;
        this.dimensions = dimensions;
        this.image = image;
        this.health = health;
        collider = new AABBCollider(position, dimensions);
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
        graphics.drawImage(image, (int)position.x, (int)position.y, (int)dimensions.x, (int)dimensions.y, null);
    }

    @Override
    public void attachDeathListener(DeathListener listener) {
        this.listener = listener;
    }

    @Override
    public String getTag() {
        return "StaticEntity";
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Vector2D getDimensions() {
        return dimensions;
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        Collidable other = collision.getOtherObject();
        if(other.getTag().contains("Bullet")) {
            takeDamage(((Bullet)other).getDamage());
            if(health <= 0) {
                alive = false;
            }
        }
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
        collider.setPosition(position);
    }

    @Override
    public Collider getCollider() {
        return collider;
    }

    protected void setImage(BufferedImage image) {
        this.image = image;
    }

    protected void takeDamage(int damage) {
        health -= damage;
    }

    int getHealth() {
        return health;
    }

    protected DeathListener getListener() {
        return listener;
    }

    private int health;
    private Vector2D position;
    private Vector2D dimensions;
    private boolean alive;
    private Collider collider;
    private BufferedImage image;
    private DeathListener listener;
}
