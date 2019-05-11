package paoo.game;

import paoo.core.Vector2D;

import java.util.ArrayList;

abstract class Weapon {
    Weapon(String owner) {
        this.owner = owner;
        this.position = new Vector2D(0, 0);
        lookingDirection = Direction.DOWN;
    }

    abstract ArrayList<Bullet> fire();

    public void setPosition(Vector2D position) {
        this.position = position;
        System.out.println("x: " + position.x + "y: " + position.y);
    }

    String getOwner() {
        return owner;
    }

    void setLookingDirection(int direction) {
        lookingDirection = direction;
    }

    int getLookingDirection() {
        return lookingDirection;
    }

    public Vector2D getPosition() {
        return position;
    }

    private int lookingDirection;
    private String owner;
    private Vector2D position;
    private Vector2D bulletDimensions;
}
