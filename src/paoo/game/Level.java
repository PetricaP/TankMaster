package paoo.game;

import paoo.core.Entity;
import paoo.core.json.JsonObject;
import paoo.core.json.JsonParser;
import paoo.core.utils.Vector2D;
import paoo.game.entities.Enemy;
import paoo.game.entities.Player;
import paoo.game.entities.bullets.Bullet;
import paoo.game.entities.staticentities.GreenBush;
import paoo.game.entities.staticentities.MediumTree;
import paoo.game.entities.staticentities.SmallTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class Level {
    private Level() {
        entities = new ArrayList<>();
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    static Level loadFromFile(String fileName) {
        Level level = new Level();
        try {
            String fileString = new String(Files.readAllBytes(Paths.get(fileName)));
            JsonObject game = JsonParser.parse(fileString);
            System.out.println(game);
            JsonObject jsonLevel = JsonParser.parse(fileString);
            for(Map.Entry<String, Object> entry : jsonLevel.getAttributes().entrySet()) {
                String name = entry.getKey();
                if (name.equals("Player")) {
                    JsonObject playerJson = (JsonObject) entry.getValue();
                    JsonObject position = playerJson.getJsonObject("position");
                    level.entities.add(new Player(
                            new Vector2D(position.getFloat("x"),
                                    position.getFloat("y")),
                            playerJson.getInt("direction")));

                } else if (name.substring(0, 5).equals("Enemy")) {
                    for (int i = 6; i < name.length(); ++i) {
                        if (!Character.isDigit(name.charAt(i))) {
                            break;
                        }
                    }
                    JsonObject enemyJson = (JsonObject) entry.getValue();
                    JsonObject position = enemyJson.getJsonObject("position");
                    level.entities.add(new Enemy(
                            Tank.Type.fromString(enemyJson.getString("type")),
                            new Vector2D(position.getFloat("x"), position.getFloat("y")),
                            enemyJson.getInt("direction")));

                } else if (name.substring(0, 6).equals("Bullet")) {
                    for (int i = 6; i < name.length(); ++i) {
                        if (!Character.isDigit(name.charAt(i))) {
                            break;
                        }
                    }
                    JsonObject bulletJson = (JsonObject) entry.getValue();
                    JsonObject position = bulletJson.getJsonObject("position");
                    level.entities.add(Bullet.create(
                            new Vector2D(position.getFloat("x"), position.getFloat("y")),
                            bulletJson.getInt("direction"),
                            bulletJson.getString("tag")));

                } else if (name.substring(0, 9).equals("GreenBush")) {
                    for (int i = 9; i < name.length(); ++i) {
                        if (!Character.isDigit(name.charAt(i))) {
                            break;
                        }
                    }
                    JsonObject entityJson = (JsonObject) entry.getValue();
                    JsonObject position = entityJson.getJsonObject("position");
                    level.entities.add(new GreenBush(
                            new Vector2D(position.getFloat("x"), position.getFloat("y")),
                            entityJson.getInt("health")));
                } else if (name.substring(0, 9).equals("SmallTree")) {
                    for (int i = 9; i < name.length(); ++i) {
                        if (!Character.isDigit(name.charAt(i))) {
                            break;
                        }
                    }
                    JsonObject entityJson = (JsonObject) entry.getValue();
                    JsonObject position = entityJson.getJsonObject("position");
                    level.entities.add(new SmallTree(
                            new Vector2D(position.getFloat("x"), position.getFloat("y")),
                            entityJson.getInt("health")));
                } else if (name.substring(0, 10).equals("MediumTree")) {
                    for (int i = 10; i < name.length(); ++i) {
                        if (!Character.isDigit(name.charAt(i))) {
                            break;
                        }
                    }
                    JsonObject entityJson = (JsonObject) entry.getValue();
                    JsonObject position = entityJson.getJsonObject("position");
                    level.entities.add(new MediumTree(
                            new Vector2D(position.getFloat("x"), position.getFloat("y")),
                            entityJson.getInt("health")));
                }
            }
        } catch(IOException e) {
            System.err.println("Couldn't load level from " + fileName);
            System.exit(-1);
        }
        return level;
    }

    protected ArrayList<Entity> entities;
}
