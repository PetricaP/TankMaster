package paoo.game.menu;

import paoo.core.utils.Vector2D;
import paoo.game.Level;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelMenu extends Menu {
    public LevelMenu(Listener listener) {
        int x = 100;
        int y = 100;
        int levelNumber = 1;
        if(new File("Backup.json").exists() && new File("Level.txt").exists()) {
            buttons.add(new ColoredTextButton(new Vector2D(100, 400), new Vector2D(150, 50),
                    () -> {
                        try {
                            Scanner scanner = new Scanner(new File("Level.txt"));
                            int level = scanner.nextInt();
                            listener.onChooseLevel(Level.loadFromFile("Backup.json", level));
                        } catch(Exception ignored) {
                        } finally {
                            new File("Backup.json").delete();
                        }
                    },
                    Color.BLACK, Color.DARK_GRAY, "Backup", Color.WHITE, font));
        }
        for(String levelFile : levelFiles) {
            int finalLevelNumber = levelNumber;
            buttons.add(new ColoredTextButton(new Vector2D(x, y), new Vector2D(50, 50),
                    () -> listener.onChooseLevel(Level.loadFromFile(levelFile, finalLevelNumber)),
                    Color.BLACK, Color.DARK_GRAY, Integer.toString(levelNumber), Color.WHITE, font));
            ++levelNumber;
            x += 70;
            if(x > 700) {
                x = 0;
                y += 70;
            }
        }
    }

    private static ArrayList<String> levelFiles;
    private static Font font;
    static {
        levelFiles = new ArrayList<>();
        try {
            Files.list(new File(".").toPath()).forEach(path -> {
                if(path.toString().startsWith("./Level") && path.toString().endsWith(".json")) {
                    levelFiles.add(path.toString());
                }
            });
            levelFiles.sort(CharSequence::compare);
        } catch(IOException e) {
            System.err.println("Couldn't load levels");
            System.exit(-1);
        }

        font = new Font("SansSerif", Font.BOLD, 20);
    }

    public interface Listener {
        void onChooseLevel(Level level);
    }
}
