package paoo.game;

import paoo.core.Vector2D;

import java.util.ArrayList;

public class DoubleFireWeapon extends Weapon {
    DoubleFireWeapon(String owner) {
        super(owner);
        final int bulletOffset = 5;
        offset1 = new Vector2D(-15, -bulletOffset);
        offset2 = new Vector2D(-15, bulletOffset);
        switch (getLookingDirection()) {
            case Direction.UP:
                offset1 = new Vector2D(-2 - bulletOffset, -20);
                offset2 = new Vector2D(-2 + bulletOffset, -20);
                break;
            case Direction.RIGHT:
                offset1 = new Vector2D(5, -bulletOffset);
                offset2 = new Vector2D(5, bulletOffset);
                break;
            case Direction.DOWN:
                offset1 = new Vector2D(-bulletOffset, 8);
                offset2 = new Vector2D(bulletOffset, 8);
                break;
        }
    }

    @Override
    public void setLookingDirection(int direction) {
        super.setLookingDirection(direction);
        final int bulletOffset = 5;
        offset1 = new Vector2D(-15, -bulletOffset);
        offset2 = new Vector2D(-15, bulletOffset);
        switch (getLookingDirection()) {
            case Direction.UP:
                offset1 = new Vector2D(-2 - bulletOffset, -20);
                offset2 = new Vector2D(-2 + bulletOffset, -20);
                break;
            case Direction.RIGHT:
                offset1 = new Vector2D(5, -bulletOffset);
                offset2 = new Vector2D(5, bulletOffset);
                break;
            case Direction.DOWN:
                offset1 = new Vector2D(-bulletOffset, 8);
                offset2 = new Vector2D(bulletOffset, 8);
                break;
        }
    }

    @Override
    ArrayList<Bullet> fire() {
        ArrayList<Bullet> bullets = new ArrayList<>();
        bullets.add(new FireBall(new Vector2D(getPosition()).add(offset1), getLookingDirection(),
                getOwner() + "FireBullet"));
        bullets.add(new FireBall(new Vector2D(getPosition()).add(offset2), getLookingDirection(),
                getOwner() + "FireBullet"));
        return bullets;
    }

    private Vector2D offset1;
    private Vector2D offset2;
}
