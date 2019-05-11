package paoo.game;

import paoo.core.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class TankFactory {
    static Tank create(String owner, Tank.Type type) {
        String filePath = "res/images/tanks.png";
        Rectangle rect;

        switch(type) {
            case PLAYER_1:
                rect = new Rectangle(0, 0, 150, 200);
                break;
            case PLAYER_2:
                rect = new Rectangle(180, 0, 150, 200);
                break;
            case PLAYER_3:
                rect = new Rectangle(360, 0, 150, 200);
                break;
            case ENEMY_BLUE_1:
                rect = new Rectangle(0, 230, 150, 180);
                break;
            case ENEMY_BLUE_2:
                rect = new Rectangle(180, 230, 150, 180);
                break;
            case ENEMY_BLUE_3:
                rect = new Rectangle(360, 230, 150, 180);
                break;
            case ENEMY_RED_1:
                rect = new Rectangle(0, 430, 150, 175);
                break;
            case ENEMY_RED_2:
                rect = new Rectangle(180, 430, 150, 175);
                break;
            case ENEMY_RED_3:
                rect = new Rectangle(360, 430, 150, 175);
                break;
            default:
            return null;
        }

        try {
            BufferedImage tankImage = ImageLoader.getInstance().loadImage(filePath)
                    .getSubimage(rect.x, rect.y, rect.width, rect.height);
            return new Tank(tankImage, new DoubleFireWeapon(owner));
        } catch(IOException e) {
            System.err.println("Couldn't load resource " + filePath);
            return null;
        }
    }
}
