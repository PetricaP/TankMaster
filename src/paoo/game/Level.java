package paoo.game;

import paoo.core.Entity;
import paoo.core.json.JsonObject;
import paoo.core.json.JsonParser;
import paoo.core.utils.Vector2D;
import paoo.game.animations.BulletExplosionAnimation;
import paoo.game.animations.BurnAnimation;
import paoo.game.animations.TankExplosionAnimation;
import paoo.game.entities.tankentities.Enemy;
import paoo.game.entities.tankentities.Player;
import paoo.game.entities.bullets.Bullet;
import paoo.game.entities.staticentities.*;

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

    public static Level loadFromFile(String fileName, int levelNumber) {
        Level level = new Level();
        level.levelNumber = levelNumber;
        try {
            String fileString = new String(Files.readAllBytes(Paths.get(fileName)));
            JsonObject jsonLevel = JsonParser.parse(fileString);
            for(Map.Entry<String, Object> entry : jsonLevel.getAttributes().entrySet()) {
                String name = entry.getKey();
                if (name.equals("Player")) {
                    parsePlayer(level, entry);
                } else if (name.startsWith("Enemy")) {
                    parseEnemy(level, entry);
                } else if (name.startsWith("Bullet")) {
                    parseBullet(level, entry);
                } else if (name.startsWith("GreenBush")) {
                    parseGreenBush(level, entry);
                } else if (name.startsWith("SmallTree")) {
                    parseSmallTree(level, entry);
                } else if (name.startsWith("MediumTree")) {
                    parseMediumTree(level, entry);
                } else if(name.startsWith("TankExplosionAnimation")) {
                    parseTankExplosionAnimation(level, entry);
                } else if(name.startsWith("BurnAnimation")) {
                    parseBurnAnimation(level, entry);
                } else if(name.startsWith("BulletExplosionAnimation")) {
                    parseBulletExplosionAnimation(level, entry);
                } else if(name.startsWith("Wall")) {
                    parseWall(level, entry);
                } else if(name.startsWith("TiledWall")) {
                    parseTiledWall(level, entry);
                } else if(name.equals("BackGround")) {
                    parseBackground(level, entry);
                }
            }
        } catch(IOException e) {
            System.err.println("Couldn't load level from " + fileName);
            System.exit(-1);
        }
        return level;
    }

    private static void parseBulletExplosionAnimation(Level level, Map.Entry<String, Object> entry) {
        JsonObject entityJson = (JsonObject) entry.getValue();
        JsonObject position = entityJson.getJsonObject("position");
        level.entities.add(new BulletExplosionAnimation(
                new Vector2D(position.getFloat("x"), position.getFloat("y")), entityJson.getInt("frame")));
    }

    private static void parseBurnAnimation(Level level, Map.Entry<String, Object> entry) {
        JsonObject entityJson = (JsonObject) entry.getValue();
        JsonObject position = entityJson.getJsonObject("position");
        level.entities.add(new BurnAnimation(
                new Vector2D(position.getFloat("x"), position.getFloat("y")), entityJson.getInt("frame")));
    }

    private static void parseTankExplosionAnimation(Level level, Map.Entry<String, Object> entry) {
        JsonObject entityJson = (JsonObject) entry.getValue();
        JsonObject position = entityJson.getJsonObject("position");
        level.entities.add(new TankExplosionAnimation(
                new Vector2D(position.getFloat("x"), position.getFloat("y")), entityJson.getInt("frame")));
    }

    private static void parseMediumTree(Level level, Map.Entry<String, Object> entry) {
        JsonObject entityJson = (JsonObject) entry.getValue();
        JsonObject position = entityJson.getJsonObject("position");
        level.entities.add(new MediumTree(
                new Vector2D(position.getFloat("x"), position.getFloat("y")),
                entityJson.getInt("health")));
    }

    private static void parseSmallTree(Level level, Map.Entry<String, Object> entry) {
        JsonObject entityJson = (JsonObject) entry.getValue();
        JsonObject position = entityJson.getJsonObject("position");
        level.entities.add(new SmallTree(
                new Vector2D(position.getFloat("x"), position.getFloat("y")),
                entityJson.getInt("health")));
    }

    private static void parseGreenBush(Level level, Map.Entry<String, Object> entry) {
        JsonObject entityJson = (JsonObject) entry.getValue();
        JsonObject position = entityJson.getJsonObject("position");
        level.entities.add(new GreenBush(
                new Vector2D(position.getFloat("x"), position.getFloat("y")),
                entityJson.getInt("health")));
    }

    private static void parseBullet(Level level, Map.Entry<String, Object> entry) {
        JsonObject bulletJson = (JsonObject) entry.getValue();
        JsonObject position = bulletJson.getJsonObject("position");
        level.entities.add(Bullet.create(
                new Vector2D(position.getFloat("x"), position.getFloat("y")),
                bulletJson.getInt("direction"),
                bulletJson.getString("tag"),
                bulletJson.getInt("speed")));
    }

    private static void parsePlayer(Level level, Map.Entry<String, Object> entry) {
        JsonObject playerJson = (JsonObject) entry.getValue();
        JsonObject position = playerJson.getJsonObject("position");
        level.entities.add(new Player(
                new Vector2D(position.getFloat("x"),
                             position.getFloat("y")),
                playerJson.getInt("direction"),
                playerJson.getInt("fireRate"),
                playerJson.getInt("bulletSpeed"),
                playerJson.getInt("speed"),
                playerJson.getInt("health")));
    }

    private static void parseEnemy(Level level, Map.Entry<String, Object> entry) {
        JsonObject enemyJson = (JsonObject) entry.getValue();
        JsonObject position = enemyJson.getJsonObject("position");
        level.entities.add(new Enemy(
                Tank.Type.fromString(enemyJson.getString("type")),
                new Vector2D(position.getFloat("x"), position.getFloat("y")),
                enemyJson.getInt("direction"),
                enemyJson.getInt("speed"),
                enemyJson.getInt("fireRate"),
                enemyJson.getInt("bulletSpeed"),
                enemyJson.getInt("health")));
    }

    private static void parseWall(Level level, Map.Entry<String, Object> entry) {
        JsonObject wallJson = (JsonObject) entry.getValue();
        JsonObject position = wallJson.getJsonObject("position");
        JsonObject dimensions = wallJson.getJsonObject("dimensions");
        level.entities.add(new Wall(new Vector2D(position.getFloat("x"), position.getFloat("y")),
                                    new Vector2D(dimensions.getFloat("x"), dimensions.getFloat("y"))));
    }

    private static void parseTiledWall(Level level, Map.Entry<String, Object> entry) {
        JsonObject wallJson = (JsonObject) entry.getValue();
        JsonObject position = wallJson.getJsonObject("position");
        JsonObject dimensions = wallJson.getJsonObject("dimensions");
        level.entities.add(new TiledWall(wallJson.getInt("type"),
                new Vector2D(position.getFloat("x"), position.getFloat("y")),
                new Vector2D(dimensions.getFloat("x"), dimensions.getFloat("y")),
                wallJson.getInt("tileSize")));
    }

    private static void parseBackground(Level level, Map.Entry<String, Object> entry) {
        JsonObject backGroundJson = (JsonObject) entry.getValue();
        JsonObject position = backGroundJson.getJsonObject("position");
        JsonObject dimensions = backGroundJson.getJsonObject("dimensions");
        level.entities.add(new BackGround(backGroundJson.getInt("type"),
                new Vector2D(position.getFloat("x"), position.getFloat("y")),
                new Vector2D(dimensions.getFloat("x"), dimensions.getFloat("y")),
                backGroundJson.getInt("tileSize")));
    }

    public int getNumber() {
        return levelNumber;
    }

    private int levelNumber = 0;
    protected ArrayList<Entity> entities;
}
