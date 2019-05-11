package paoo.core;

public class Vector2D {
    public int x;
    public int y;

    public Vector2D() {
        x = 0;
        y = 0;
    }

    public Vector2D(int x, int y) {
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

    public Vector2D div(int factor) {
        x /= factor;
        y /= factor;
        return this;
    }

    public Vector2D mul(int factor) {
        x *= factor;
        y *= factor;
        return this;
    }
}
