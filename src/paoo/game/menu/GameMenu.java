package paoo.game.menu;

import paoo.core.utils.Vector2D;

import java.awt.*;

public class GameMenu extends Menu {
    public GameMenu(PlayClickListener playListener, ExitClickListener exitListener) {
        super();
        Font font = new Font("Arial", Font.BOLD, 50);
        buttons.add(new ColoredTextButton(new Vector2D(300, 200),
                                                       new Vector2D(200, 100),
                                                       playListener::onPlayClick,
                                                       Color.BLACK, Color.DARK_GRAY,
                                                        "PLAY", Color.WHITE, font));

        buttons.add(new ColoredTextButton(new Vector2D(300, 350),
                                                       new Vector2D(200, 100),
                                                       exitListener::onExitClick,
                                                       Color.BLACK, Color.DARK_GRAY,
                                                      "EXIT", Color.WHITE, font));
    }

    public interface PlayClickListener {
        void onPlayClick();
    }

    public interface ExitClickListener {
        void onExitClick();
    }

    public interface Listener extends PlayClickListener, ExitClickListener {}
}
