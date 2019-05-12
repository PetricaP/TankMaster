package paoo.core;

public class AABBCollider implements Collider {
    public AABBCollider(Vector2D position, Vector2D dimensions) {
        this.dimensions = dimensions;
        this.center = new Vector2D(position).add(new Vector2D(dimensions).div(2));
        this.position = position;
    }

    @Override
    public Collision resolveCollision(Collider other) {
        if(other instanceof AABBCollider) {
            AABBCollider A = this;
            AABBCollider B = (AABBCollider)other;
            Vector2D centerDistance = new Vector2D(Math.abs(B.center.x - A.center.x),
                                                   Math.abs(B.center.y - A.center.y));
            Vector2D halfDimensionsSum = new Vector2D(A.dimensions.x / 2 + B.dimensions.x / 2,
                                                   A.dimensions.y / 2 + B.dimensions.y / 2);
            if(centerDistance.x < halfDimensionsSum.x
            && centerDistance.y < halfDimensionsSum.y) {
                return new Collision();
            }
        }
        return null;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
        this.center = new Vector2D(position).add(new Vector2D(dimensions).div(2));
    }

    @Override
    public void setDimensions(Vector2D dimensions) {
        this.dimensions = dimensions;
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
    public Vector2D getCenter() {
        return center;
    }

    private Vector2D position;
    private Vector2D center;
    private Vector2D dimensions;
}