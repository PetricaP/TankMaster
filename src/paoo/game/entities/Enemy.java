package paoo.game.entities;

import paoo.core.collisions.Collision;
import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.Direction;
import paoo.game.Tank;
import paoo.game.animations.TankExplosionAnimation;
import paoo.game.entities.bullets.Bullet;

public class Enemy extends TankEntity {
    public Enemy(Tank.Type type, Vector2D position, int speed) {
        super(type, position, new Vector2D(50, 50),
                new Vector2D(0, 0), Direction.DOWN, "Enemy", 3);
        health = 5;
        this.speed = speed;
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        String tag = collision.getOtherObject().getTag();
        if(tag.contains("Player") && tag.contains("Bullet")) {
            health -= ((Bullet)collision.getOtherObject()).getDamage();
            if(health <= 0) {
                setAlive(false);
                getDeathListener().onDeath(new TankExplosionAnimation(getPosition().add(getDimensions().div(2))));
            }
        }

        if(tag.equals("Enemy") || tag.equals("Player")
                || tag.equals("Wall") || tag.contains("Static")) {
            Collision.resolveCollision(this, collision.getOtherObject());
            velocity = new Vector2D(0, 0);
        }
    }

    private long fireLastTime = 0;
    private long changeDirectionLastTime = 0;
    @Override
    public void update() {
        if(moving) {
            super.update();
        }

        long currentTime = System.currentTimeMillis();

        if(currentTime - changeDirectionLastTime > 1000) {
            chooseRandomMovingDirection();
            tank.setLookingDirection(getLookingDirection());
            changeDirectionLastTime = currentTime;
        }

        if(currentTime - fireLastTime > 250) {
            fireLastTime = currentTime;
            if(Math.random() > 0.5) {
                shoot();
            }
        }
    }

    @Override
    public String getTag() {
        return "Enemy";
    }

    private void chooseRandomMovingDirection() {
        double random = Math.random();
        if(random < 0.25) {
            setLookingDirection(Direction.UP);
            velocity = new Vector2D(0, -speed);
        } else if(random < 0.5) {
            setLookingDirection(Direction.DOWN);
            velocity = new Vector2D(0, speed);
        } else if(random < 0.75) {
            setLookingDirection(Direction.RIGHT);
            velocity = new Vector2D(speed, 0);
        } else {
            setLookingDirection(Direction.LEFT);
            velocity = new Vector2D(-speed, 0);
        }
        double random2 = Math.random();

        moving = !(random2 < 0.15);
    }

    @Override
    public JsonObject toJson() {
        return JsonObject.build()
                .addAttribute("position", getPosition().toJson())
                .addAttribute("speed", speed)
                .addAttribute("direction", getLookingDirection())
                .addAttribute("type", getType().toString()).getObject();
    }

    private int speed;
    private boolean moving;
    private int health;
}
