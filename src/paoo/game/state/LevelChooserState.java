package paoo.game.state;

import paoo.game.menu.LevelMenu;

import java.awt.*;

public class LevelChooserState implements GameState {
    public LevelChooserState(Window listenOn, LevelMenu.Listener listener) {
        levelMenu = new LevelMenu(listener);
        this.listenOn = listenOn;
        listenOn.addMouseListener(levelMenu);
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics g) {
        levelMenu.draw(g);
    }

    @Override
    public void cleanUp() {
        listenOn.removeMouseListener(levelMenu);
    }

    private LevelMenu levelMenu;
    private Window listenOn;
}
