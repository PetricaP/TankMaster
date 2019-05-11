package paoo.game;

import paoo.core.*;

import java.awt.*;

public class Wall implements Collidable {
    Wall(Vector2D position, Vector2D dimensions) {
        this.position = position;
        this.dimensions = dimensions;
        collider = new AABBCollider(this.position, this.dimensions);
    }

    @Override
    public void onCollisionEnter(Collision collision) {
    }

    @Override
    public Collider getCollider() {
        return collider;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillRect(position.x, position.y, dimensions.x, dimensions.y);
    }

    @Override
    public void attachDeathListener(DeathListener listener) {}

    @Override
    public String getTag() {
        return "Wall";
    }

    @Override
    public Vector2D getPosition() {
        return null;
    }

    @Override
    public Vector2D getDimensions() {
        return null;
    }

    private AABBCollider collider;
    private Vector2D position;
    private Vector2D dimensions;
}
