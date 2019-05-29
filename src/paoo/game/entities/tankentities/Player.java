package paoo.game.entities.tankentities;

import paoo.core.*;
import paoo.core.collisions.Collision;
import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.*;
import paoo.game.animations.TankExplosionAnimation;
import paoo.game.entities.bullets.Bullet;

import java.awt.event.KeyEvent;

public class Player extends TankEntity {
    public Player(Vector2D position, int lookingDirection, int fireRate, int bulletSpeed, int speed, int health) {
        super(Tank.Type.PLAYER_2, position, new Vector2D(50, 50),
				new Vector2D(0, 0), lookingDirection, "Player", fireRate, bulletSpeed, health);
        this.speed = speed;
        alive = true;
    }

	@Override
	public String getTag() {
		return "Player";
	}

	private long lastFireTime = 0;
	@Override
    public void update() {
    	velocity.y = 0;
		if(KeyboardManager.getInstance().isPressed(KeyEvent.VK_W)
		|| KeyboardManager.getInstance().isPressed(KeyEvent.VK_UP)) {
			velocity.y -= speed;
	    }
		if(KeyboardManager.getInstance().isPressed(KeyEvent.VK_S)
		|| KeyboardManager.getInstance().isPressed(KeyEvent.VK_DOWN)) {
			velocity.y += speed;
		}

		velocity.x = 0;
		if(velocity.y == 0) {
			if (KeyboardManager.getInstance().isPressed(KeyEvent.VK_A)
			|| KeyboardManager.getInstance().isPressed(KeyEvent.VK_LEFT)) {
				velocity.x -= speed;
			}
			if (KeyboardManager.getInstance().isPressed(KeyEvent.VK_D)
			|| KeyboardManager.getInstance().isPressed(KeyEvent.VK_RIGHT)) {
				velocity.x += speed;
			}
		}

		super.update();

		long currentTime = System.currentTimeMillis();
		if(KeyboardManager.getInstance().isPressed(KeyEvent.VK_SPACE)) {
            if(currentTime - lastFireTime > 1000 / getFireRate()) {
                lastFireTime = currentTime;
                shoot();
            }
		}
    }

	@Override
	public void onCollisionEnter(Collision collision) {
	    String tag = collision.getOtherObject().getTag();
	    if(tag.contains("Wall") || tag.equals("Enemy") || tag.contains("Static")) {
	        Collision.resolveCollision(this, collision.getOtherObject());
		}
	    if(tag.contains("Enemy") && tag.contains("Bullet")) {
	        Bullet bullet = (Bullet)collision.getOtherObject();
	        setHealth(getHealth() - bullet.getDamage());
	        if(getHealth() <= 0) {
	            alive = false;
	            getDeathListener().onDeath(new TankExplosionAnimation(getPosition(), 0));
			}
		}
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public JsonObject toJson() {
		return super.toJson().addAttribute("speed", speed);
	}

	private int speed;
	private boolean alive;
}
