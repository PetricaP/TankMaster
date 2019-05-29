package paoo.core.collisions;

import java.util.ArrayList;

public class CollisionEngine {
    public CollisionEngine() {
        collidables = new ArrayList<>();
    }

    public void update() {
        ArrayList<Collidable> toRemove = new ArrayList<>();
        for(Collidable collidable : collidables) {
            if(!collidable.isAlive()) {
                toRemove.add(collidable);
            }
        }
        collidables.removeAll(toRemove);

        for(int i = 0; i < collidables.size() - 1; ++i) {
            for(int j = i + 1; j < collidables.size(); ++j) {
                Collision collision = collidables.get(i).getCollider()
                                        .resolveCollision(collidables.get(j).getCollider());
                if(collision != null) {
                    Collision collision2 = new Collision(collision);

                    collision.setOtherObject(collidables.get(j));
                    collidables.get(i).onCollisionEnter(collision);

                    collision2.setOtherObject(collidables.get(i));
                    collidables.get(j).onCollisionEnter(collision2);
                }
            }
        }
    }

    public void reset() {
        collidables.clear();
    }

    public void addObject(Collidable collidable) {
        collidables.add(collidable);
    }

    private ArrayList<Collidable> collidables;
}
