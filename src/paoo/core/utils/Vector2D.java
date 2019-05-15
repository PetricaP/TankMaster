package paoo.core.utils;

import paoo.core.json.JsonConvertible;
import paoo.core.json.JsonObject;

public class Vector2D implements JsonConvertible {
    public float x;
    public float y;

    public Vector2D() {
        x = 0;
        y = 0;
    }

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other) {
        x = other.x;
        y = other.y;
    }

    public Vector2D add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2D sub(Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2D div(float factor) {
        x /= factor;
        y /= factor;
        return this;
    }

    public Vector2D mul(float factor) {
        x *= factor;
        y *= factor;
        return this;
    }

    @Override
    public JsonObject toJson() {
        return JsonObject.build().addAttribute("x", x).addAttribute("y", y).getObject();
    }
}
