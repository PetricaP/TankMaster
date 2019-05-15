package paoo.game.entities;

import paoo.core.*;
import paoo.core.collisions.AABBCollider;
import paoo.core.collisions.Collidable;
import paoo.core.collisions.Collider;
import paoo.core.collisions.Collision;
import paoo.core.json.JsonConvertible;
import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.*;
import paoo.game.entities.bullets.Bullet;

import java.awt.*;
import java.util.ArrayList;

public class Player implements Entity, Collidable, JsonConvertible {
    public Player(Vector2D position, int lookingDirection) {
        this.position = position;
        velocity = new Vector2D(0, 0);

		dimensions = new Vector2D(50, 50);

		tank = TankFactory.create("Player", Tank.Type.PLAYER_3);
		if(tank == null) {
			System.err.println("Couldn't create tank for player");
			System.exit(-1);
		}
		tank.setLookingDirection(lookingDirection);
		this.lookingDirection = lookingDirection;

        attackListeners = new ArrayList<>();
		collider = new AABBCollider(position, dimensions);
		fireRate = 3;
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
			lookingDirection = Direction.RIGHT;
			tank.setLookingDirection(lookingDirection);
		}
		if(velocity.x < 0) {
			lookingDirection = Direction.LEFT;
			tank.setLookingDirection(lookingDirection);
		}
		if(velocity.y > 0) {
			lookingDirection = Direction.DOWN;
			tank.setLookingDirection(lookingDirection);
		}
		if(velocity.y < 0) {
			lookingDirection = Direction.UP;
			tank.setLookingDirection(lookingDirection);
		}

        position.add(velocity);
		tank.setPosition(position);

		long currentTime = System.currentTimeMillis();
		if(KeyboardManager.getInstance().isPressed(' ')) {
            if(currentTime - lastTime > 1000 / fireRate) {
                lastTime = currentTime;
                for(Bullet bullet : tank.shoot()) {
                    for (AttackListener listener : attackListeners) {
                        listener.onShoot(bullet);
                    }
                }
            }
		}
    }

    public void addAttackListener(AttackListener listener) {
    	attackListeners.add(listener);
	}

	@Override
	public void onCollisionEnter(Collision collision) {
	    System.out.println("Player colliding");
	    String tag = collision.getOtherObject().getTag();
	    if(tag.contains("Wall") || tag.equals("Enemy") || tag.contains("Static")) {
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

	private float fireRate;
    private Tank tank;
	private Vector2D position;
	private Vector2D dimensions;
	private Vector2D velocity;
	private ArrayList<AttackListener> attackListeners;
	private AABBCollider collider;
	private int lookingDirection;

	@Override
	public JsonObject toJson() {
	    return JsonObject.build()
				.addAttribute("position", position.toJson())
				.addAttribute("direction", lookingDirection)
				.getObject();
	}
}
