package paoo.game;

import paoo.core.*;

import java.awt.*;
import java.util.ArrayList;

public class Enemy implements Entity, Collidable {
    Enemy(Tank.Type type, Vector2D position, int speed) {
        this.position = position;
        this.speed = speed;
        dimensions = new Vector2D(50, 50);

        tank = TankFactory.create("Enemy", type);
        if(tank != null) {
            tank.setPosition(position);
        } else {
            System.err.println("Couldn't load image for enemy");
            System.exit(-1);
        }

        collider = new AABBCollider(new Vector2D(position), dimensions);
        alive = true;

        attackListeners = new ArrayList<>();
        deathListeners = new ArrayList<>();
        health = 5;
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
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void draw(Graphics graphics) {
        tank.draw(graphics);
    }

    @Override
    public void attachDeathListener(DeathListener listener) {
        deathListeners.add(listener);
    }

    @Override
    public String getTag() {
        return "Enemy";
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        String tag = collision.getOtherObject().getTag();
        if(tag.contains("Player") && tag.contains("Bullet")) {
            health -= ((Bullet)collision.getOtherObject()).getDamage();
            if(health <= 0) {
                alive = false;
                for(DeathListener listener : deathListeners) {
                    listener.onDeath(new TankExplosionAnimation(position));
                }
            }
        }

        if(tag.equals("Enemy") || tag.equals("Player") || tag.equals("Wall")) {
            Collision.resolveCollision(this, collision.getOtherObject());
            velocity = new Vector2D(0, 0);
        }
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    @Override
    public Collider getCollider() {
        return collider;
    }

    private long fireLastTime = 0;
    private long changeDirectionLastTime = 0;

    @Override
    public void update() {
        collider.setPosition(new Vector2D(position));
        if(moving) {
            position.add(velocity);
            tank.setPosition(position);
        }

        long currentTime = System.currentTimeMillis();

        if(currentTime - changeDirectionLastTime > 1000) {
            chooseRandomMovingDirection();
            tank.setLookingDirection(movingDirection);
            changeDirectionLastTime = currentTime;
        }

        if(currentTime - fireLastTime > 250) {
            fireLastTime = currentTime;
            if(Math.random() > 0.5) {
                for(Bullet bullet : tank.shoot()) {
                    for (AttackListener listener : attackListeners) {
                        listener.onShoot(bullet);
                    }
                }
            }
        }
    }

    void addAttackListener(AttackListener listener) {
        attackListeners.add(listener);
    }

    private void chooseRandomMovingDirection() {
        double random = Math.random();
        if(random < 0.25) {
            movingDirection = Direction.UP;
            velocity = new Vector2D(0, -speed);
        } else if(random < 0.5) {
            movingDirection = Direction.DOWN;
            velocity = new Vector2D(0, speed);
        } else if(random < 0.75) {
            movingDirection = Direction.RIGHT;
            velocity = new Vector2D(speed, 0);
        } else {
            movingDirection = Direction.LEFT;
            velocity = new Vector2D(-speed, 0);
        }
        double random2 = Math.random();

        moving = !(random2 < 0.15);
    }

    private int health;
    private boolean moving;
    private int movingDirection;
    private int speed;
    private Collider collider;
    private Vector2D position;
    private Vector2D dimensions;
    private Vector2D velocity;
    private boolean alive;
    private Tank tank;
    private ArrayList<AttackListener> attackListeners;
    private ArrayList<DeathListener> deathListeners;
}
