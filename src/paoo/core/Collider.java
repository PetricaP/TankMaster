package paoo.core;

public interface Collider {
    Collision resolveCollision(Collider other);
    void setPosition(Vector2D position);
    void setDimensions(Vector2D dimensions);
    Vector2D getPosition();
    Vector2D getDimensions();
}
