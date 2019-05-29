package paoo.game;

import paoo.core.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class TileManager {
    public static final int GROUND_INDEX = 0;
    public static final int STONE_INDEX = 1;
    public static final int DESERT_INDEX = 2;
    public static final int WATER_INDEX = 3;
    public static final int LAVA_INDEX = 4;
    public static final int WALL_INDEX = 5;

    public static BufferedImage getTileImage(int index) {
        return tiles[index];
    }

    private static BufferedImage[] tiles;
    static {
        tiles = new BufferedImage[6];
        try {
            BufferedImage tileImage = ImageLoader.getInstance().loadImage("res/images/tiles.png");

            tiles[GROUND_INDEX] = tileImage.getSubimage(0, 0, 65, 62);
            tiles[STONE_INDEX] = tileImage.getSubimage(0, 65, 65, 65);
            tiles[DESERT_INDEX] = tileImage.getSubimage(0, 130, 65, 65);
            tiles[WATER_INDEX] = tileImage.getSubimage(0, 195, 65, 65);
            tiles[LAVA_INDEX] = tileImage.getSubimage(0, 260, 65, 65);
            tiles[WALL_INDEX] = tileImage.getSubimage(0, 325, 65, 65);
        } catch(IOException e) {
            System.err.println("Couldn't load tile image.");
            System.exit(-1);
        }
    }
}
