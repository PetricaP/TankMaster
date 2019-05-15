package paoo.core.collisions;

import paoo.core.Entity;
import paoo.core.utils.Vector2D;

public interface Collidable extends Entity {
    void onCollisionEnter(Collision collision);
    void setPosition(Vector2D position);

    Collider getCollider();
}
