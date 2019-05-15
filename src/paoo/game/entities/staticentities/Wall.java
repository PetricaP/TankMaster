package paoo.game.entities.staticentities;

import paoo.core.collisions.AABBCollider;
import paoo.core.collisions.Collidable;
import paoo.core.collisions.Collider;
import paoo.core.collisions.Collision;
import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.DeathListener;

import java.awt.*;

public class Wall implements Collidable {
    public Wall(Vector2D position, Vector2D dimensions) {
        this.position = position;
        this.dimensions = dimensions;
        collider = new AABBCollider(this.position, this.dimensions);
    }

    @Override
    public void onCollisionEnter(Collision collision) {
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
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
        graphics.fillRect((int)position.x, (int)position.y, (int)dimensions.x, (int)dimensions.y);
    }

    @Override
    public void attachDeathListener(DeathListener listener) {}

    @Override
    public String getTag() {
        return "Wall";
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
    public JsonObject toJson() {
        return JsonObject.build()
                .addAttribute("type", this.getClass().toString())
                .addAttribute("position", getPosition().toJson())
                .getObject();
    }

    private AABBCollider collider;
    private Vector2D position;
    private Vector2D dimensions;
}
