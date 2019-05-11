package paoo.game;

import paoo.core.Vector2D;

import java.util.ArrayList;

public class SingleFireWeapon extends Weapon {
    SingleFireWeapon(String owner) {
        super(owner);

        offset = new Vector2D(-35, -2);
        switch(getLookingDirection()) {
            case Direction.UP:
                offset = new Vector2D(-7, -25);
                break;
            case Direction.RIGHT:
                offset = new Vector2D(10, 0);
                break;
            case Direction.DOWN:
                offset = new Vector2D(-7, -10);
                break;
        }
    }

    @Override
    public void setLookingDirection(int direction) {
        super.setLookingDirection(direction);

        offset = new Vector2D(-35, -2);
        switch(getLookingDirection()) {
            case Direction.UP:
                offset = new Vector2D(-7, -25);
                break;
            case Direction.RIGHT:
                offset = new Vector2D(10, 0);
                break;
            case Direction.DOWN:
                offset = new Vector2D(-7, -10);
                break;
        }
    }

    @Override
    ArrayList<Bullet> fire() {
        ArrayList<Bullet> bullets = new ArrayList<>();
        bullets.add(new FireBall(new Vector2D(getPosition()).add(offset), getLookingDirection(),
                getOwner() + "FireBullet"));
        return bullets;
    }

    private Vector2D offset;
}
