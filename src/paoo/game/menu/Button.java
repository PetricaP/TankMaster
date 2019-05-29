package paoo.game.menu;

import paoo.core.utils.Vector2D;
import paoo.game.Game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Button extends MouseAdapter {
    Button(Vector2D position, Vector2D dimensions, ClickAction action) {
        this.position = position;
        this.dimensions = dimensions;
        this.action = action;
    }

    public abstract void draw(Graphics graphics);

    @Override
    public void mousePressed(MouseEvent e) {
        if(isInside(e.getX(), e.getY())) {
            selected = true;
        }
    }

    private boolean isInside(int x, int y) {
        return x > position.x &&
                x < position.x + dimensions.x &&
                y > position.y + Game.BORDER_SIZE &&
                y < position.y + dimensions.y + Game.BORDER_SIZE;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(selected && isInside(e.getX(), e.getY())) {
            action.onClick();
        }
        selected = false;
    }

    boolean isSelected() {
        return selected;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getDimensions() {
        return dimensions;
    }

    private boolean selected;
    private ClickAction action;
    private Vector2D position;
    private Vector2D dimensions;
}
