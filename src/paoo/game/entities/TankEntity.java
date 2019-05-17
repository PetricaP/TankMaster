package paoo.game.entities;

import paoo.core.collisions.AABBCollider;
import paoo.core.collisions.Collidable;
import paoo.core.collisions.Collider;
import paoo.core.utils.Vector2D;
import paoo.game.*;
import paoo.game.entities.bullets.Bullet;

import java.awt.*;
import java.util.ArrayList;

public abstract class TankEntity implements Collidable {
    TankEntity(Tank.Type type, Vector2D position, Vector2D dimensions,
               Vector2D velocity, int lookingDirection, String owner,
               int fireRate) {
        this.position = position;
        this.velocity = velocity;
        this.fireRate = fireRate;
        this.dimensions = dimensions;
        this.type = type;
        alive = true;

        tank = TankFactory.create(owner, type);
        if(tank == null) {
            System.err.println("Couldn't create tank for " + owner);
            System.exit(-1);
        }
        tank.setLookingDirection(lookingDirection);

        attackListeners = new ArrayList<>();

        collider = new AABBCollider(this.position, this.dimensions);
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void update() {
        position.add(velocity);
        if(velocity.x > 0) {
            setLookingDirection(Direction.RIGHT);
        } else if(velocity.x < 0) {
            setLookingDirection(Direction.LEFT);
        } else if(velocity.y > 0) {
            setLookingDirection(Direction.DOWN);
        } else if(velocity.y < 0) {
            setLookingDirection(Direction.UP);
        }

        collider.setPosition(position);
        tank.setPosition(position);
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

    @Override
    public void draw(Graphics graphics) {
        tank.draw(graphics);
    }

    @Override
    public void attachDeathListener(DeathListener listener) {
        this.listener = listener;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Vector2D getDimensions() {
        return dimensions;
    }

    void setLookingDirection(int lookingDirection) {
        this.lookingDirection = lookingDirection;
        tank.setLookingDirection(lookingDirection);
    }

    public void addAttackListener(AttackListener listener) {
        this.attackListeners.add(listener);
    }

    void shoot() {
        ArrayList<Bullet> bullets = tank.shoot();
        for(AttackListener listener : attackListeners) {
            for(Bullet bullet : bullets) {
                listener.onShoot(bullet);
            }
        }
    }

    int getFireRate() {
        return fireRate;
    }

    int getLookingDirection() {
        return lookingDirection;
    }

    ArrayList<AttackListener> getAttackListeners() {
        return attackListeners;
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    DeathListener getDeathListener() {
        return listener;
    }

    Tank.Type getType() {
        return type;
    }

    DeathListener listener;
    Tank tank;

    private Tank.Type type;
    private AABBCollider collider;
    private int lookingDirection;
    private Vector2D position;
    Vector2D velocity;
    private Vector2D dimensions;
    private boolean alive;
    private int fireRate;
    private ArrayList<AttackListener> attackListeners;
}
