package paoo.game.weapons;

import paoo.core.utils.Vector2D;
import paoo.game.entities.bullets.Bullet;
import paoo.game.Direction;
import paoo.game.entities.bullets.SimpleBullet;

import java.util.ArrayList;

public class DoubleSimpleWeapon extends Weapon {
    private static final int bulletOffset = 5;

    public DoubleSimpleWeapon(String owner) {
        super(owner);

        if(offsets1 == null) {
            offsets1 = new Vector2D[4];
            offsets1[Direction.UP] = new Vector2D(-2 - bulletOffset, -15);
            offsets1[Direction.RIGHT] = new Vector2D(10, bulletOffset);
            offsets1[Direction.DOWN] = new Vector2D(-3 - bulletOffset, 10);
            offsets1[Direction.LEFT] = new Vector2D(-20, bulletOffset);

            offsets2 = new Vector2D[4];
            offsets2[Direction.UP] = new Vector2D(-2 + bulletOffset, -15);
            offsets2[Direction.RIGHT] = new Vector2D(10, -bulletOffset);
            offsets2[Direction.DOWN] = new Vector2D(-3 + bulletOffset, 10);
            offsets2[Direction.LEFT] = new Vector2D(-20, -bulletOffset);
        }

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
        bullets.add(new SimpleBullet(new Vector2D(getPosition()).add(offset1),
                    getLookingDirection(), getOwner() + "SimpleBullet"));
        bullets.add(new SimpleBullet(new Vector2D(getPosition()).add(offset2),
                getLookingDirection(), getOwner() + "SimpleBullet"));
        return bullets;
    }

    private void ChangeOffset() {
        offset1 = offsets1[getLookingDirection()];
        offset2 = offsets2[getLookingDirection()];
    }

    private static Vector2D[] offsets1;
    private static Vector2D[] offsets2;
    private Vector2D offset1;
    private Vector2D offset2;
}
