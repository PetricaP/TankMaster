package paoo.game;

import paoo.core.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class Tank {
    enum Type {
        PLAYER_1,
        PLAYER_2,
        PLAYER_3,
        ENEMY_BLUE_1,
        ENEMY_BLUE_2,
        ENEMY_BLUE_3,
        ENEMY_RED_1,
        ENEMY_RED_2,
        ENEMY_RED_3
    }

    Tank(BufferedImage image, Weapon weapon) {
        assert(image != null);
        this.image = image;
        this.dimensions = defaultTankDimensions;

        imageCenter = new Vector2D(image.getWidth(null) / 2, image.getHeight(null) / 2);

        lookingDirection = Direction.DOWN;
        directionalImages[lookingDirection] = image;
        this.weapon = weapon;
    }

    void draw(Graphics graphics) {
        // TODO: Shouldn't have the need to do this check
        if(directionalImages[lookingDirection] != null && position != null && dimensions != null) {
            graphics.drawImage(directionalImages[lookingDirection],
                    position.x, position.y, dimensions.x, dimensions.y, null);
        }
    }

    ArrayList<Bullet> shoot() {
        return weapon.fire();
    }

    void setLookingDirection(int lookingDirection) {
        if(lookingDirection != this.lookingDirection) {
            weapon.setLookingDirection(lookingDirection);
            this.lookingDirection = lookingDirection;

            if(directionalImages[lookingDirection] == null) {
                double theta = 0;
                switch (lookingDirection) {
                    case Direction.UP:
                        theta = 180;
                        break;
                    case Direction.DOWN:
                        theta = 0;
                        break;
                    case Direction.LEFT:
                        theta = 90;
                        break;
                    case Direction.RIGHT:
                        theta = 270;
                        break;
                }

                AffineTransformOp imageTransformOperation = new AffineTransformOp(
                        AffineTransform.getRotateInstance(Math.toRadians(theta), imageCenter.x, imageCenter.y),
                        AffineTransformOp.TYPE_BILINEAR);
                directionalImages[lookingDirection] = imageTransformOperation.filter(image, null);
            }
        }
    }

    void setPosition(Vector2D position) {
        this.position = position;
        System.out.println("Setting tank position to " + position.x + ", " + position.y);
        System.out.println("Setting weapon position to " + (position.x + dimensions.x / 2)
                                                  + ", " + (position.y + dimensions.y / 2));
        weapon.setPosition(new Vector2D(position).add(new Vector2D(dimensions).div(2)));
    }

    private static final Vector2D defaultTankDimensions = new Vector2D(50, 50);
    private BufferedImage[] directionalImages = new BufferedImage[4];

    private Weapon weapon;
    private int lookingDirection;
    private Vector2D position;
    private Vector2D dimensions;
    private BufferedImage image;
    private Vector2D imageCenter;
}
