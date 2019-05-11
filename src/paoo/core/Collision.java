package paoo.core;

public class Collision {
    private Collidable otherObject;

    Collision() {

    }

    Collision(Collision other) {
    }

    public Collidable getOtherObject() {
        return otherObject;
    }

    void setOtherObject(Collidable other) {
        otherObject = other;
    }
}
