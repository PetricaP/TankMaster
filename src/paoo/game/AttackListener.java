package paoo.game;

import paoo.game.entities.bullets.Bullet;

public interface AttackListener {
    void onShoot(Bullet bullet);
}
