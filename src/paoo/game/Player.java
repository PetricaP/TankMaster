package paoo.game;

import paoo.core.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

class Player implements Entity, KeyListener, Collidable {
    Player(Vector2D position) {
        this.position = position;
        velocity = new Vector2D(0, 0);

		dimensions = new Vector2D(50, 50);

		tank = TankFactory.create("Player", Tank.Type.PLAYER_3);
		if(tank == null) {
			System.err.println("Couldn't create tank for player");
			System.exit(-1);
		}

        attackListeners = new ArrayList<>();
		collider = new AABBCollider(position, dimensions);
    }

    @Override
    public void draw(Graphics graphics) {
        tank.draw(graphics);
    }

	@Override
	public void attachDeathListener(DeathListener listener) {

	}

	@Override
	public String getTag() {
		return "Player";
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	@Override
	public Vector2D getDimensions() {
		return dimensions;
	}

	private long lastTime = 0;

	@Override
	public boolean isAlive() {
	    return true;
	}

	@Override
    public void update() {
    	final int speed = 3;
    	collider.setPosition(position);
    	velocity.y = 0;
		if(KeyboardManager.getInstance().isPressed('w')) {
			velocity.y -= speed;
	    }
		if(KeyboardManager.getInstance().isPressed('s')) {
			velocity.y += speed;
		}

		velocity.x = 0;
		if(velocity.y == 0) {
			if (KeyboardManager.getInstance().isPressed('a')) {
				velocity.x -= speed;
			}
			if (KeyboardManager.getInstance().isPressed('d')) {
				velocity.x += speed;
			}
		}
		if(velocity.x > 0) {
			tank.setLookingDirection(Direction.RIGHT);
		}
		if(velocity.x < 0) {
			tank.setLookingDirection(Direction.LEFT);
		}
		if(velocity.y > 0) {
			tank.setLookingDirection(Direction.DOWN);
		}
		if(velocity.y < 0) {
			tank.setLookingDirection(Direction.UP);
		}

        position.add(velocity);
		tank.setPosition(position);

		long currentTime = System.currentTimeMillis();
		if(currentTime - lastTime > 400) {
		    lastTime = currentTime;
			if(KeyboardManager.getInstance().isPressed(' ')) {
				for(Bullet bullet : tank.shoot()) {
					for (AttackListener listener : attackListeners) {
						listener.onShoot(bullet);
					}
				}
			}
		}
    }

    void addListener(AttackListener listener) {
    	attackListeners.add(listener);
	}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

	@Override
	public void onCollisionEnter(Collision collision) {
	    System.out.println("Player colliding");
	    String tag = collision.getOtherObject().getTag();
	    if(tag.contains("Wall") || tag.equals("Enemy")) {
	        Collision.resolveCollision(this, collision.getOtherObject());
		}
	}

	@Override
	public void setPosition(Vector2D position) {
	    this.position = position;
	    collider.setPosition(position);
	}

	@Override
	public Collider getCollider() {
	    return collider;
	}

    private Tank tank;
	private Vector2D position;
	private Vector2D dimensions;
	private Vector2D velocity;
	private ArrayList<AttackListener> attackListeners;
	private AABBCollider collider;
}
