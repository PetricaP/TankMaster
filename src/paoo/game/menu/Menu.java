package paoo.game.menu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Menu extends MouseAdapter {
    Menu() {
        buttons = new ArrayList<>();
    }

    public void draw(Graphics graphics) {
        for(Button button : buttons) {
            button.draw(graphics);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(Button button : buttons) {
            button.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Button button : buttons) {
            button.mouseReleased(e);
        }
    }

    ArrayList<Button> buttons;
}
