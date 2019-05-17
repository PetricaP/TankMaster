package paoo.game;

import paoo.core.*;
import paoo.core.collisions.Collidable;
import paoo.core.collisions.CollisionEngine;
import paoo.core.json.JsonObject;
import paoo.game.animations.BurnAnimation;
import paoo.game.animations.TankExplosionAnimation;
import paoo.game.entities.bullets.Bullet;
import paoo.game.entities.Enemy;
import paoo.game.entities.Player;
import paoo.game.entities.staticentities.GreenBush;
import paoo.game.entities.staticentities.MediumTree;
import paoo.game.entities.staticentities.SmallTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends JFrame implements Application, AttackListener, DeathListener {
	enum State {
        PLAY,
		EXIT
	}

    public Game() {
		DrawCanvas canvas = new DrawCanvas();
    	setSize(800, 800);
	    getContentPane().add(canvas);
    	addKeyListener(KeyboardManager.getInstance());
    	setLocation(600, 100);

    	setVisible(true);
    	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		loadLevel("Level1.json");
		state = State.PLAY;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			    if(state != State.EXIT) {
			    	saveToFile();
				}
			}
		});
	}

	private void loadLevel(String filePath) {
		Level level = Level.loadFromFile(filePath);
		entities.clear();
		for(Entity entity : level.getEntities()) {
			if(entity instanceof Collidable) {
				collisionEngine.addObject((Collidable)entity);
			}
			if(entity instanceof Enemy) {
				((Enemy)entity).addAttackListener(this);
			}
			if(entity instanceof Player) {
				((Player) entity).addAttackListener(this);
				camera = new Camera(entity);
			}
			entity.attachDeathListener(this);
		}
		entities.addAll(level.getEntities());
	}

	@Override
    public void run() {
    	while(state != State.EXIT) {
    	    if(KeyboardManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)) {
    	    	state = State.EXIT;
			}
    		try {
    			Thread.sleep(10);
		    } catch(InterruptedException e) {
    		    e.printStackTrace();
		    }
    		update();
            repaint();
            Toolkit.getDefaultToolkit().sync();
	    }
    	saveToFile();
    	System.exit(0);
    }

	private void update() {
		lock.lock();
    	entities.removeIf(entity -> !entity.isAlive());
        for(Entity entity : entities) {
        	entity.update();
		}
        entities.addAll(entityQueue);
		lock.unlock();
        entityQueue.clear();

		collisionEngine.update();
		camera.update();
	}

	@Override
	public void onShoot(Bullet bullet) {
    	bullet.attachDeathListener(this);
        entityQueue.add(bullet);
        collisionEngine.addObject(bullet);
	}

	@Override
	public void onDeath(Entity entity) {
		entityQueue.add(entity);
	}

	private void saveToFile() {
	    try {
	        JsonObject.Builder builder = JsonObject.build();
			PrintWriter file = new PrintWriter("Backup.json");
			int enemyNumber = 0;
			int bulletNumber = 0;
			int greenBushNumber = 0;
			int mediumTreeNumber = 0;
			int smallTreeNumber = 0;
			int burnAnimationNumber = 0;
			int tankExplosionAnimationNumber = 0;
			lock.lock();
			for(Entity entity : entities) {
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

	class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GRAY);
			g.fillRect(0, 0, 800, 800);

			if(camera != null) {
				g.translate((int) camera.getOffset().x, (int) camera.getOffset().y);
				lock.lock();
				for (Entity entity : entities) {
					if (entity.getPosition().x + camera.getOffset().x + entity.getDimensions().x > 0 &&
                        entity.getPosition().y + camera.getOffset().y + entity.getDimensions().y > 0 &&
                        entity.getPosition().x + camera.getOffset().x < 800 &&
                        entity.getPosition().y + camera.getOffset().y < 800)
						entity.draw(g);
				}
				lock.unlock();
			}
        }
    }

    private Lock lock = new ReentrantLock();
	private Camera camera;
	private ArrayList<Entity> entities = new ArrayList<>();
	private ArrayList<Entity> entityQueue = new ArrayList<>();
	private CollisionEngine collisionEngine = new CollisionEngine();
	private State state;
}
