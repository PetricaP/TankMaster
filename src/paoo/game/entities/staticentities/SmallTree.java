package paoo.game.entities.staticentities;

import paoo.core.ImageLoader;
import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.animations.BurnAnimation;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class SmallTree extends StaticDestroyableEntity {
    public SmallTree(Vector2D position) {
        super(position, smallTreeDimensions, null, smallTreeHealth);
        if(smallTreeImages == null) {
            try {
                smallTreeImages = new BufferedImage[3];
                smallTreeImages[2] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(260, 0, 65, 65);
                smallTreeImages[1] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(390, 0, 65, 65);
                smallTreeImages[0] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(325, 0, 65, 65);
            } catch(IOException e) {
                System.err.println("Couldn't load smallTree image");
                System.exit(-1);
            }
        }
        setImage(smallTreeImages[2]);
    }

    public SmallTree(Vector2D position, int initialHealth) {
        super(position, smallTreeDimensions, null, initialHealth);
        if(smallTreeImages == null) {
            try {
                smallTreeImages = new BufferedImage[3];
                smallTreeImages[2] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(260, 0, 65, 65);
                smallTreeImages[1] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(390, 0, 65, 65);
                smallTreeImages[0] = ImageLoader.getInstance().loadImage("res/images/trees.png")
                        .getSubimage(325, 0, 65, 65);
            } catch(IOException e) {
                System.err.println("Couldn't load smallTree image");
                System.exit(-1);
            }
        }
        setImage(smallTreeImages[(getHealth() - 3) / 6]);
    }

    @Override
    protected void takeDamage(int damage) {
        super.takeDamage(damage);
        setImage(smallTreeImages[(getHealth() - 3) / 6]);
        if(getHealth() <= 0) {
            getListener().onDeath(new BurnAnimation(
                    new Vector2D(getPosition()).add(new Vector2D(getDimensions()).div(2)), 0));
        }
    }

    private static int smallTreeHealth = 20;
    private static Vector2D smallTreeDimensions = new Vector2D(50, 50);
    private static BufferedImage[] smallTreeImages = null;

    @Override
    public JsonObject toJson() {
        return JsonObject.build()
                .addAttribute("position", getPosition().toJson())
                .addAttribute("health", getHealth()).getObject();
    }
}
