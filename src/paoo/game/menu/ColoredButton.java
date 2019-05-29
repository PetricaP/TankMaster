package paoo.game.menu;

import paoo.core.utils.Vector2D;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ColoredButton extends Button {
    ColoredButton(Vector2D position, Vector2D dimensions, ClickAction action,
                         Color defaultColor, Color selectedColor) {
        super(position, dimensions, action);
        this.defaultColor = defaultColor;
        this.selectedColor = selectedColor;
        currentColor = defaultColor;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(currentColor);
        graphics.fillRect((int)getPosition().x, (int)getPosition().y,
                          (int)getDimensions().x, (int)getDimensions().y);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if(isSelected()) {
            currentColor = selectedColor;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        currentColor = defaultColor;
    }

    private Color currentColor;
    private Color defaultColor;
    private Color selectedColor;
}
