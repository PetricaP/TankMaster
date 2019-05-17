package paoo.game.entities;

import paoo.core.*;
import paoo.core.collisions.Collision;
import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.*;
import paoo.game.entities.bullets.Bullet;

import java.awt.event.KeyEvent;

public class Player extends TankEntity {
    public Player(Vector2D position, int lookingDirection) {
        super(Tank.Type.PLAYER_3, position, new Vector2D(50, 50),
				new Vector2D(0, 0), lookingDirection, "Player", 3);
    }

	@Override
	public String getTag() {
		return "Player";
	}

	private long lastFireTime = 0;
	@Override
    public void update() {
        final int speed = 2;
    	velocity.y = 0;
		if(KeyboardManager.getInstance().isPressed(KeyEvent.VK_W)) {
			velocity.y -= speed;
	    }
		if(KeyboardManager.getInstance().isPressed(KeyEvent.VK_S)) {
			velocity.y += speed;
		}

		velocity.x = 0;
		if(velocity.y == 0) {
			if (KeyboardManager.getInstance().isPressed(KeyEvent.VK_A)) {
				velocity.x -= speed;
			}
			if (KeyboardManager.getInstance().isPressed(KeyEvent.VK_D)) {
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
	}

	@Override
	public JsonObject toJson() {
		return JsonObject.build()
				.addAttribute("position", getPosition().toJson())
				.addAttribute("direction", getLookingDirection())
				.getObject();
	}
}
