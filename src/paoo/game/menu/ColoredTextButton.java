package paoo.game.menu;

import paoo.core.utils.Vector2D;

import java.awt.*;

public class ColoredTextButton extends ColoredButton {
    public ColoredTextButton(Vector2D position, Vector2D dimensions, ClickAction action,
                      Color defaultColor, Color selectedColor, String text, Color textColor,
                      Font font) {
        super(position, dimensions, action, defaultColor, selectedColor);
        this.text = text;
        this.textColor = textColor;
        this.font = font;
    }

    @Override
    public void draw(Graphics graphics) {
        super.draw(graphics);
        graphics.setColor(textColor);
        graphics.setFont(font);

        if(textWidth == -1) {
            textWidth = graphics.getFontMetrics(font).stringWidth(text);
            textHeight = (int)(graphics.getFontMetrics(font).getHeight() * 3.0 / 4.0);
            System.out.println(textHeight);
        }

        final int textX = (int)(getPosition().x + getDimensions().x / 2 - textWidth / 2);
        final int textY = (int)(getPosition().y + getDimensions().y / 2 + textHeight / 2);
        graphics.drawString(text, textX, textY);
    }

    private String text;
    private Color textColor;
    private Font font;
    private int textWidth = -1;
    private int textHeight = -1;
}
