package paoo.game.weapons;

import paoo.core.utils.Vector2D;
import paoo.game.Direction;
import paoo.game.entities.bullets.Bullet;

import java.util.ArrayList;

public abstract class Weapon {
    Weapon(String owner, int bulletSpeed) {
        this.owner = owner;
        this.position = new Vector2D(0, 0);
        lookingDirection = Direction.DOWN;
        this.bulletSpeed = bulletSpeed;
    }

    public abstract ArrayList<Bullet> fire();

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    String getOwner() {
        return owner;
    }

    public void setLookingDirection(int direction) {
        lookingDirection = direction;
    }

    int getLookingDirection() {
        return lookingDirection;
    }

    public Vector2D getPosition() {
        return position;
    }

    int getBulletSpeed() { return bulletSpeed; }

    private int lookingDirection;
    private String owner;
    private Vector2D position;
    private int bulletSpeed;
}
