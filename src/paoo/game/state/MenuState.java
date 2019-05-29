package paoo.game.state;

import paoo.game.menu.GameMenu;

import java.awt.*;

public class MenuState implements GameState {
    public MenuState(GameMenu.Listener listener, Window listenOn) {
        this.listenOn = listenOn;
        menu = new GameMenu(listener, listener);
        listenOn.addMouseListener(menu);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        menu.draw(g);
    }

    @Override
    public void cleanUp() {
        listenOn.removeMouseListener(menu);
    }

    private Window listenOn;
    private GameMenu menu;
}
