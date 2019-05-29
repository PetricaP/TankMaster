package paoo.game.entities.staticentities;

import paoo.core.Entity;
import paoo.core.ImageLoader;
import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.DeathListener;
import paoo.game.TileManager;

import java.awt.*;
import java.util.ArrayList;

public class BackGround implements Entity {
    public BackGround(int type, Vector2D position, Vector2D dimensions, int tileSize) {
        this.type = type;
        this.tileSize = tileSize;
        this.position = position;
        this.dimensions = dimensions;
        tilePositions = new ArrayList<>();
        for(int x = (int)position.x; x < position.x + dimensions.x; x += tileSize) {
            for(int y = (int)position.y; y < position.y + dimensions.y; y += tileSize) {
                tilePositions.add(new Vector2D(x, y));
            }
        }
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public void update() {

    }

    public void draw(Graphics graphics) {
        for(Vector2D position : tilePositions) {
            graphics.drawImage(TileManager.getTileImage(type), (int)position.x, (int)position.y, tileSize, tileSize, null);
        }
    }

    @Override
    public void attachDeathListener(DeathListener listener) {}

    @Override
    public String getTag() { return "Background"; }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Vector2D getDimensions() {
        return dimensions;
    }

    private Vector2D position;
    private Vector2D dimensions;
    private int tileSize;
    private int type;
    private ArrayList<Vector2D> tilePositions;

    public JsonObject toJson() {
        return JsonObject.build()
                .addAttribute("type", type)
                .addAttribute("position", position.toJson())
                .addAttribute("dimensions", dimensions.toJson())
                .addAttribute("tileSize", tileSize)
                .getObject();
    }
}
