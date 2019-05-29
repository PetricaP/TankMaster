package paoo.game.entities.staticentities;

import paoo.core.ImageLoader;
import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.animations.BurnAnimation;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class GreenBush extends StaticDestroyableEntity {
    public GreenBush(Vector2D position) {
        super(position, bushDimensions, null, bushHealth);
        if(bushImages == null) {
            try {
                bushImages = new BufferedImage[3];
                bushImages[2] = ImageLoader.getInstance().loadImage("res/images/trees.png").getSubimage(0, 0, 65, 65);
                bushImages[1] = ImageLoader.getInstance().loadImage("res/images/trees.png").getSubimage(65, 0, 65, 65);
                bushImages[0] = ImageLoader.getInstance().loadImage("res/images/trees.png").getSubimage(130, 0, 65, 65);
            } catch(IOException e) {
                System.err.println("Couldn't load bush image");
                System.exit(-1);
            }
        }
        setImage(bushImages[2]);
    }

    public GreenBush(Vector2D position, int initialHealth) {
        super(position, bushDimensions, null, initialHealth);
        if(bushImages == null) {
            try {
                bushImages = new BufferedImage[3];
                bushImages[2] = ImageLoader.getInstance().loadImage("res/images/trees.png").getSubimage(0, 0, 65, 65);
                bushImages[1] = ImageLoader.getInstance().loadImage("res/images/trees.png").getSubimage(65, 0, 65, 65);
                bushImages[0] = ImageLoader.getInstance().loadImage("res/images/trees.png").getSubimage(130, 0, 65, 65);
            } catch(IOException e) {
                System.err.println("Couldn't load bush image");
                System.exit(-1);
            }
        }
        setImage(bushImages[Math.max((getHealth() - 2), 0) / 3]);
    }

    @Override
    protected void takeDamage(int damage) {
        super.takeDamage(damage);
        setImage(bushImages[Math.max((getHealth() - 2), 0) / 3]);
        if(getHealth() <= 0) {
            getListener().onDeath(new BurnAnimation(
                    new Vector2D(getPosition()).add(new Vector2D(getDimensions()).div(2)), 0));
        }
    }

    @Override
    public JsonObject toJson() {
        return JsonObject.build()
                .addAttribute("position", getPosition().toJson())
                .addAttribute("health", getHealth()).getObject();
    }

    private static int bushHealth = 10;
    private static Vector2D bushDimensions = new Vector2D(50, 50);
    private static BufferedImage[] bushImages = null;
}
