package paoo.core;

public class Collision {
    private Collidable otherObject;

    Collision() {

    }

    Collision(Collision other) {
        this.otherObject = other.otherObject;
    }

    public Collidable getOtherObject() {
        return otherObject;
    }

    void setOtherObject(Collidable other) {
        otherObject = other;
    }

    public static void resolveCollision(Collidable entity1, Collidable entity2) {
        Collider collider1 = entity1.getCollider();

        int xc = collider1.getCenter().x;
        int yc = collider1.getCenter().y;

        Collider collider2 = entity2.getCollider();

        int x1 = collider2.getPosition().x;
        int y1 = collider2.getPosition().y;

        int x1p = x1 + collider2.getDimensions().x;
        int y1p = y1 + collider2.getDimensions().y;

        int x2 = x1;
        int y2 = y1p;

        int x2p = x1p;
        int y2p = y1;

        int yc1 = y1 - (y1p - y1) * (x1 - xc) / (x1p - x1);
        int yc2 = y2 - (y2p - y2) * (x2 - xc) / (x2p - x2);

        int d1 = yc1 - yc;
        int d2 = yc2 - yc;

        // The coordinates of the corners for the two objects
        x1 = collider1.getPosition().x;
        y1 = collider1.getPosition().y;
        x1p = x1 + collider1.getDimensions().x;
        y1p = y1 + collider1.getDimensions().y;

        x2 = collider2.getPosition().x;
        y2 = collider2.getPosition().y;
        x2p = x2 + collider2.getDimensions().x;
        y2p = y2 + collider2.getDimensions().y;

        Vector2D delta;
        if(d1 > 0 && d2 < 0) {
            // Right quadrant (1)
            delta = new Vector2D(x2p - x1, 0);
        } else if(d1 > 0 && d2 > 0) {
            // Top quadrant (2)
            delta = new Vector2D(0, y2 - y1p);
        } else if(d1 < 0 && d2 > 0) {
            // Left quadrant (3)
            delta = new Vector2D(x2 - x1p, 0);
        } else {
            // Bottom quadrant (4) not working
            delta = new Vector2D(0, y2p - y1);
        }
        entity1.setPosition(new Vector2D(entity1.getPosition()).add(delta));
    }
}
