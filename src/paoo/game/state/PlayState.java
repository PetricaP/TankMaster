package paoo.game.state;

import paoo.core.Camera;
import paoo.core.Entity;
import paoo.core.collisions.Collidable;
import paoo.core.json.JsonObject;
import paoo.game.*;
import paoo.game.animations.AnimationEntity;
import paoo.game.animations.BurnAnimation;
import paoo.game.animations.TankExplosionAnimation;
import paoo.game.entities.bullets.Bullet;
import paoo.game.entities.staticentities.*;
import paoo.game.entities.tankentities.Enemy;
import paoo.game.entities.tankentities.Player;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PlayState extends GamePlayState {
    class WndListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            saveToFile();
        }
    }

    PlayState(Level level, Listener listener, Window listenOn) {
        super();
        this.listener = listener;
        this.listenOn = listenOn;
        info.levelNumber = level.getNumber();
        loadLevel(level);
        wndListener = new WndListener();
        listenOn.addWindowListener(wndListener);
    }

    @Override
    public void update() {
        if(info.player.getHealth() <= 0) {
            listener.onPlayerDead(info);
            listenOn.removeWindowListener(wndListener);
        }
        if(info.nEnemies == 0) {
            listener.onPlayerWon(info);
        }
        super.update();
    }

    @Override
    public void cleanUp() {

    }

    private void loadLevel(Level level) {
        lock.lock();
        info.entities.clear();
        lock.unlock();
        info.collisionEngine.reset();
        info.nEnemies = 0;
        for(Entity entity : level.getEntities()) {
            if(entity instanceof Collidable) {
                info.collisionEngine.addObject((Collidable)entity);
            }
            if(entity instanceof AnimationEntity) {
                ((AnimationEntity)entity).setPlayerPosition(info.player.getPosition());
            }
            if(entity instanceof Enemy) {
                ++info.nEnemies;
                ((Enemy)entity).addAttackListener(this);
            }
            if(entity instanceof Player) {
                info.player = (Player)entity;
                info.player.addAttackListener(this);
                info.camera = new Camera(info.player);
            }
            entity.attachDeathListener(this);
        }
        info.entities.addAll(level.getEntities());
    }

    private void saveToFile() {
        try {
            PrintWriter lvlFile = new PrintWriter("Level.txt");
            lvlFile.println(info.levelNumber);
            lvlFile.println(info.score);
            lvlFile.close();
            JsonObject.Builder builder = JsonObject.build();
            PrintWriter file = new PrintWriter("Backup.json");
            int enemyNumber = 0;
            int bulletNumber = 0;
            int greenBushNumber = 0;
            int mediumTreeNumber = 0;
            int smallTreeNumber = 0;
            int burnAnimationNumber = 0;
            int tankExplosionAnimationNumber = 0;
            int tiledWallNumber = 0;
            lock.lock();
            for(Entity entity : info.entities) {
                if(entity instanceof Player) {
                    builder.addAttribute("Player", entity.toJson());
                } else if(entity instanceof Bullet) {
                    builder.addAttribute("Bullet" + bulletNumber++, entity.toJson());
                } else if(entity instanceof Enemy) {
                    builder.addAttribute("Enemy" + enemyNumber++, entity.toJson());
                } else if(entity instanceof GreenBush) {
                    builder.addAttribute("GreenBush" + greenBushNumber++, entity.toJson());
                } else if(entity instanceof MediumTree) {
                    builder.addAttribute("MediumTree" + mediumTreeNumber++, entity.toJson());
                } else if(entity instanceof SmallTree) {
                    builder.addAttribute("SmallTree" + smallTreeNumber++, entity.toJson());
                } else if(entity instanceof BurnAnimation) {
                    builder.addAttribute("BurnAnimation" + burnAnimationNumber++, entity.toJson());
                } else if(entity instanceof TankExplosionAnimation) {
                    builder.addAttribute("TankExplosionAnimation" + tankExplosionAnimationNumber++, entity.toJson());
                } else if(entity instanceof TiledWall) {
                    builder.addAttribute("TiledWall" + tiledWallNumber++, entity.toJson());
                } else if(entity instanceof BackGround) {
                    builder.addAttribute("BackGround", entity.toJson());
                }
            }
            lock.unlock();
            file.write(builder.getObject().toString());
            file.close();
        } catch(FileNotFoundException e) {
            System.err.println("Couldn't save game");
            e.printStackTrace();
        }
    }

    public interface Listener {
        void onPlayerDead(GamePlayInfo info);
        void onPlayerWon(GamePlayInfo info);
    }

    private WndListener wndListener;
    private Window listenOn;
    private Listener listener;
}
