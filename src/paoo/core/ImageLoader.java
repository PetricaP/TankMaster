package paoo.core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class ImageLoader {
    public static ImageLoader getInstance() {
        if(instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    private ImageLoader() {
        images = new HashMap<>();
    }

    public BufferedImage loadImage(String path) throws IOException {
        if(!images.containsKey(path)) {
            images.put(path, ImageIO.read(new File(path)));
        }
        return images.get(path);
    }

    private static ImageLoader instance = null;
    private Map<String, BufferedImage> images;
}
