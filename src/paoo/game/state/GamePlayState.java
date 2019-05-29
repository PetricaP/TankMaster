package paoo.game.state;

import paoo.core.Entity;
import paoo.core.collisions.CollisionEngine;
import paoo.game.AttackListener;
import paoo.game.DeathListener;
import paoo.game.animations.AnimationEntity;
import paoo.game.animations.TankExplosionAnimation;
import paoo.game.entities.bullets.Bullet;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static paoo.game.Game.BOARD_SIZE;

public abstract class GamePlayState implements GameState, DeathListener, AttackListener {
    public GamePlayState() {
        entityQueue = new ArrayList<>();
        info = new GamePlayInfo();
        info.collisionEngine = new CollisionEngine();
        info.nEnemies = 0;
        info.entities = new ArrayList<>();
    }

    @Override
    public void update() {
        lock.lock();
        info.entities.removeIf(entity -> !entity.isAlive());
        for (Entity entity : info.entities) {
            entity.update();
        }
        info.entities.addAll(entityQueue);
        lock.unlock();
        entityQueue.clear();

        info.collisionEngine.update();
        info.camera.update();
    }

    @Override
    public void draw(Graphics g) {
        if(info != null && info.camera != null) {
            g.translate((int) info.camera.getOffset().x, (int) info.camera.getOffset().y);
            lock.lock();
            for (Entity entity : info.entities) {
                if (entity.getPosition().x + info.camera.getOffset().x + entity.getDimensions().x > 0 &&
                        entity.getPosition().y + info.camera.getOffset().y + entity.getDimensions().y > 0 &&
                        entity.getPosition().x + info.camera.getOffset().x < BOARD_SIZE &&
                        entity.getPosition().y + info.camera.getOffset().y < BOARD_SIZE)
                    entity.draw(g);
            }
            lock.unlock();
            g.translate(-(int) info.camera.getOffset().x, -(int) info.camera.getOffset().y);
            g.setColor(Color.BLACK);
        }
    }

    @Override
    public void onDeath(Entity entity) {
        if(entity instanceof TankExplosionAnimation) {
            --info.nEnemies;
            if(info.player.isAlive()) {
                ++info.score;
            }
        }
        if(entity instanceof AnimationEntity) {
            ((AnimationEntity)entity).setPlayerPosition(info.player.getPosition());
        }
        entityQueue.add(entity);
    }

    @Override
    public void onShoot(Bullet bullet) {
        bullet.attachDeathListener(this);
        entityQueue.add(bullet);
        info.collisionEngine.addObject(bullet);
    }

    GamePlayInfo info;
    private ArrayList<Entity> entityQueue;
    Lock lock = new ReentrantLock();
}
