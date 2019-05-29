package paoo.game.entities.staticentities;

import paoo.core.json.JsonObject;
import paoo.core.utils.Vector2D;
import paoo.game.TileManager;

import java.awt.*;
import java.util.ArrayList;

public class TiledWall extends Wall {
    public TiledWall(int type, Vector2D position, Vector2D dimensions, int tileSize) {
        super(position, dimensions);

        this.type = type;
        this.tileSize = tileSize;

        tilePositions = new ArrayList<>();
        for(int x = (int)position.x; x < position.x + dimensions.x; x += tileSize) {
            for(int y = (int)position.y; y < position.y + dimensions.y; y += tileSize) {
                tilePositions.add(new Vector2D(x, y));
            }
        }
    }

    @Override
    public void draw(Graphics graphics) {
        for(Vector2D position : tilePositions) {
            graphics.drawImage(TileManager.getTileImage(type), (int)position.x, (int)position.y, tileSize, tileSize, null);
        }
    }

    public JsonObject toJson() {
        return super.toJson()
                .addAttribute("type", type)
                .addAttribute("tileSize", tileSize);
    }

    private ArrayList<Vector2D> tilePositions;
    private int type;
    private int tileSize;
}
