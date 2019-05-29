package paoo.game.menu;

import paoo.core.utils.Vector2D;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EditText extends KeyAdapter {
    public EditText(Vector2D position, Vector2D dimensions, Font font, int charLimit) {
        builder = new StringBuilder();
        this.position = position;
        this.dimensions = dimensions;
        this.font = font;
        this.charLimit = charLimit;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int)position.x - 2, (int)position.y - 2, (int)dimensions.x + 4, (int)dimensions.y + 4);
        g.setColor(Color.WHITE);
        g.fillRect((int)position.x, (int)position.y, (int)dimensions.x, (int)dimensions.y);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(builder.toString(), (int)position.x + 5, (int)position.y + (int)dimensions.y * 3 / 4);
    }

    public String getText() {
        return builder.toString();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
        if(e.getKeyChar() == '\b') {
            if(builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
        } else if(Character.isDigit(e.getKeyChar()) || Character.isAlphabetic(e.getKeyChar())) {
            if(charLimit == -1 || builder.length() < charLimit) {
                builder.append(e.getKeyChar());
            }
        }
    }

    private int charLimit;
    private Vector2D position;
    private Vector2D dimensions;
    private StringBuilder builder;
    private Font font;
}
