package paoo.game.entities.tankentities;

import paoo.core.utils.Vector2D;

import java.awt.*;

public class HealthBar {
    HealthBar(int initialHealth, Vector2D position) {
        health = initialHealth;
        this.initialHealth = initialHealth;
        this.position = position;
    }

    void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int)position.x - 1, (int)position.y - 1, initialHealth * 5 + 2, 5 + 2);
        g.setColor(Color.RED);
        g.fillRect((int)position.x, (int)position.y, initialHealth * 5, 5);
        g.setColor(Color.GREEN);
        g.fillRect((int)position.x, (int)position.y, health * 5, 5);
    }

    void setHealth(int health) {
        this.health = health;
    }

    void setPosition(Vector2D position) {
        this.position = position;
    }

    private Vector2D position;
    private int health;
    private int initialHealth;
}
