package paoo.game;

import paoo.core.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

		PopulateEntities();
		state = State.PLAY;
	}

	private void PopulateEntities() {
		player.addListener(this);
		entities.add(player);
		collisionEngine.addObject(player);

		Enemy enemy = new Enemy(Tank.Type.ENEMY_BLUE_1, new Vector2D(600, 200), 1);
		Enemy enemy2 = new Enemy(Tank.Type.ENEMY_BLUE_2, new Vector2D(600, 400), 1);
		Enemy enemy3 = new Enemy(Tank.Type.ENEMY_RED_3, new Vector2D(600, 600), 2);

		enemy.addAttackListener(this);
		enemy2.addAttackListener(this);
		enemy3.addAttackListener(this);
		enemy.attachDeathListener(this);
		enemy2.attachDeathListener(this);
		enemy3.attachDeathListener(this);

		entities.add(enemy);
		entities.add(enemy2);
		entities.add(enemy3);

		collisionEngine.addObject(enemy);
		collisionEngine.addObject(enemy2);
		collisionEngine.addObject(enemy3);

		Wall top = new Wall(new Vector2D(0, 0), new Vector2D(800, 20));
		collisionEngine.addObject(top);
		entities.add(top);

		Wall bottom = new Wall(new Vector2D(0, 745), new Vector2D(800, 20));
		collisionEngine.addObject(bottom);
		entities.add(bottom);

		Wall left = new Wall(new Vector2D(0, 20), new Vector2D(20, 760));
		collisionEngine.addObject(left);
		entities.add(left);

		Wall right = new Wall(new Vector2D(780, 20), new Vector2D(20, 760));
		collisionEngine.addObject(right);
		entities.add(right);
	}

	@Override
    public void run() {
    	while(state != State.EXIT) {
    		try {
    			Thread.sleep(10);
		    } catch(InterruptedException e) {
    		    e.printStackTrace();
		    }
    		update();
    		EventQueue.invokeLater(() -> {
    		    repaint();
                Toolkit.getDefaultToolkit().sync();
			});
	    }
    }

	private void update() {
    	collisionEngine.update();

        lock.lock();
    	entities.removeIf(entity -> !entity.isAlive());
        for(Entity entity : entities) {
        	entity.update();
		}
        entities.addAll(entityQueue);
        lock.unlock();
        entityQueue.clear();
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

	class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GRAY);
			g.fillRect(0, 0, 800, 800);

			lock.lock();
			for(Entity entity : entities) {
				entity.draw(g);
			}
			lock.unlock();
        }
    }

    private Lock lock = new ReentrantLock();
	private Player player = new Player(new Vector2D(400, 400));
	private ArrayList<Entity> entities = new ArrayList<>();
	private ArrayList<Entity> entityQueue = new ArrayList<>();
	private CollisionEngine collisionEngine = new CollisionEngine();
	private State state;
}
