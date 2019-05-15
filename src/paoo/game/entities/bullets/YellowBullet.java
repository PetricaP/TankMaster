package paoo.game.entities.bullets;

import paoo.core.ImageLoader;
import paoo.core.utils.Vector2D;
import paoo.game.Direction;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class YellowBullet extends Bullet {
    public YellowBullet(Vector2D position, int direction, int speed, String tag) {
        super(position, DIMENSIONS, direction, speed, tag);

        this.direction = direction;
        if(direction == Direction.UP || direction == Direction.DOWN) {
            float dim1 = getDimensions().y;
            float dim2 = getDimensions().x;
            setDimension(new Vector2D(dim1, dim2));
        }
        if(images == null) {
            images = new BufferedImage[4];
            if(images[Direction.RIGHT] == null) {
                try {
                    images[Direction.RIGHT] = ImageLoader.getInstance().loadImage("res/images/bullets.png");
                } catch (IOException e) {
                    System.err.println("Couldn't load image for YellowBullet");
                    System.exit(-1);
                }
            }
            if(images[direction] == null) {
                double theta;
                switch(direction) {
                    case Direction.UP:
                        theta = 270;
                        break;
                    case Direction.DOWN:
                        theta = 90;
                        break;
                    default:
                        theta = 180;
                }
                Vector2D imageCenter = new Vector2D(images[Direction.RIGHT].getWidth(null) / 2,
                        images[Direction.RIGHT].getHeight(null) / 2);

                AffineTransformOp imageTransformOperation = new AffineTransformOp(
                        AffineTransform.getRotateInstance(Math.toRadians(theta), imageCenter.x, imageCenter.y),
                        AffineTransformOp.TYPE_BILINEAR);
                images[direction] = imageTransformOperation.filter(images[Direction.RIGHT], null);
            }
        }
    }

    @Override
    public int getDamage() {
        return 2;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(images[direction], (int)getPosition().x, (int)getPosition().y,
                           (int)getDimensions().x, (int)getDimensions().y, null);
    }

    private int direction;
    private static BufferedImage[] images;
    private static final Vector2D DIMENSIONS = new Vector2D(20, 10);
}
