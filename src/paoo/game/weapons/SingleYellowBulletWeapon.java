package paoo.game.weapons;

import paoo.core.utils.Vector2D;
import paoo.game.entities.bullets.Bullet;
import paoo.game.Direction;
import paoo.game.entities.bullets.YellowBullet;

import java.util.ArrayList;

public class SingleYellowBulletWeapon extends Weapon {
    public SingleYellowBulletWeapon(String owner) {
        super(owner);

        if(offsets == null) {
            offsets = new Vector2D[4];
            offsets[Direction.UP] = new Vector2D(0, -22);
            offsets[Direction.DOWN] = new Vector2D(-2, 7);
            offsets[Direction.RIGHT] = new Vector2D(7, 0);
            offsets[Direction.LEFT] = new Vector2D(-10, 0);
        }

        ChangeOffset();
    }

    @Override
    public ArrayList<Bullet> fire() {
        ArrayList<Bullet> bullets = new ArrayList<>();
        bullets.add(new YellowBullet(new Vector2D(getPosition()).add(offset), getLookingDirection(), 3, getOwner() + "YellowBullet"));
        return bullets;
    }

    private void ChangeOffset() {
        offset = offsets[getLookingDirection()];
    }

    private Vector2D offset;
    private static Vector2D[] offsets;
}
