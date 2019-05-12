package paoo.game;

import paoo.core.*;

class Direction {
    static final int UP = 0;
    static final int DOWN = 1;
    static final int LEFT = 2;
    static final int RIGHT = 3;
}

abstract class Bullet implements Entity, Collidable {
    Bullet(Vector2D position, Vector2D dimensions, int direction, int speed, String tag) {
        this.position = position;
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

    @Override
    public void update() {
        position.add(velocity);
        collider.setPosition(new Vector2D(position));
        System.out.println(tag);
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
                        new Vector2D(position).add(new Vector2D(dimensions).div(2))));
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
    public void attachDeathListener(DeathListener listener) {
        this.listener = listener;
    }

    abstract int getDamage();

    private DeathListener listener = null;
    private boolean alive;
    private Collider collider;
    private Vector2D position;
    private Vector2D dimensions;
    private Vector2D velocity;
    private String tag;
}
