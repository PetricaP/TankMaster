package paoo.game.weapons;

import paoo.core.utils.Vector2D;
import paoo.game.entities.bullets.Bullet;
import paoo.game.Direction;
import paoo.game.entities.bullets.SimpleBullet;

import java.util.ArrayList;

public class SingleSimpleWeapon extends Weapon {
    public SingleSimpleWeapon(String owner, int bulletSpeed) {
    super(owner, bulletSpeed);

        ChangeOffset();
    }

    @Override
    public void setLookingDirection(int direction) {
        super.setLookingDirection(direction);

        ChangeOffset();
    }

    @Override
    public ArrayList<Bullet> fire() {
        ArrayList<Bullet> bullets = new ArrayList<>();
        bullets.add(new SimpleBullet(new Vector2D(getPosition()).add(offset), getLookingDirection(),
                getOwner() + "SimpleBullet", getBulletSpeed()));
        return bullets;
    }

    private void ChangeOffset() {
        offset.x = -35;
        offset.y = -2;
        switch (getLookingDirection()) {
            case Direction.UP:
                offset.x = -7;
                offset.y = -25;
                break;
            case Direction.RIGHT:
                offset.x = 10;
                offset.y = 0;
                break;
            case Direction.DOWN:
                offset.x = -7;
                offset.y = -10;
                break;
        }
    }

    private Vector2D offset = new Vector2D();
}
