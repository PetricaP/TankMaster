package paoo.game.entities.staticentities;

import paoo.core.ImageLoader;
import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.animations.BurnAnimation;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class MediumTree extends StaticDestroyableEntity {
    public MediumTree(Vector2D position) {
        super(position, mediumTreeDimensions, null, mediumTreeHealth);
        if(mediumTreeImages == null) {
            try {
                mediumTreeImages = new BufferedImage[3];
                mediumTreeImages[2] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(0, 65, 65, 95);
                mediumTreeImages[1] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(130, 65, 65, 95);
                mediumTreeImages[0] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(65, 65, 65, 95);
            } catch(IOException e) {
                System.err.println("Couldn't load mediumTree image");
                System.exit(-1);
            }
        }
        setImage(mediumTreeImages[2]);
    }

    public MediumTree(Vector2D position, int initialHealth) {
        super(position, mediumTreeDimensions, null, initialHealth);
        if(mediumTreeImages == null) {
            try {
                mediumTreeImages = new BufferedImage[3];
                mediumTreeImages[2] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(0, 65, 65, 95);
                mediumTreeImages[1] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(130, 65, 65, 95);
                mediumTreeImages[0] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(65, 65, 65, 95);
            } catch(IOException e) {
                System.err.println("Couldn't load mediumTree image");
                System.exit(-1);
            }
        }
        setImage(mediumTreeImages[(getHealth() - 1) / 6]);
    }

    @Override
    protected void takeDamage(int damage) {
        super.takeDamage(damage);
        setImage(mediumTreeImages[(getHealth() - 1) / 6]);
        if(getHealth() <= 0) {
            getListener().onDeath(new BurnAnimation(
                    new Vector2D(getPosition()).add(new Vector2D(getDimensions()).div(2))));
        }
    }

    @Override
    public JsonObject toJson() {
        return JsonObject.build()
                .addAttribute("position", getPosition().toJson())
                .addAttribute("health", getHealth()).getObject();
    }

    private static int mediumTreeHealth = 18;
    private static Vector2D mediumTreeDimensions = new Vector2D(50, 80);
    private static BufferedImage[] mediumTreeImages = null;
}
