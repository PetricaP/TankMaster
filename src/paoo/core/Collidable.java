package paoo.core;

public interface Collidable extends Entity {
    void onCollisionEnter(Collision collision);

    Collider getCollider();
}
