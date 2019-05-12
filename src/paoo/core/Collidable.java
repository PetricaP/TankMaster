package paoo.core;

public interface Collidable extends Entity {
    void onCollisionEnter(Collision collision);
    void setPosition(Vector2D position);

    Collider getCollider();
}
