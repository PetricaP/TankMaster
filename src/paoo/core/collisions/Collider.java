package paoo.core.collisions;

import paoo.core.utils.Vector2D;

public interface Collider {
    Collision resolveCollision(Collider other);
    void setPosition(Vector2D position);
    void setDimensions(Vector2D dimensions);
    Vector2D getPosition();
    Vector2D getDimensions();
    Vector2D getCenter();
}
