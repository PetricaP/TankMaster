package paoo.core;

import paoo.core.utils.Vector2D;

public class Camera {
    public Camera(Entity entityToFollow) {
        assert(entityToFollow != null);
        followedEntity = entityToFollow;
        update();
    }

    public void update() {
        offset = new Vector2D(400, 400).sub(
                new Vector2D(followedEntity.getPosition())
                .add(new Vector2D(followedEntity.getDimensions()).div(2)));
    }

    public Vector2D getOffset() {
        return offset;
    }

    private Vector2D offset;
    private Entity followedEntity;
}
