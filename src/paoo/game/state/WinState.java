package paoo.game.state;

import java.awt.*;

public class WinState extends FinalState {
    WinState(GamePlayInfo info, FinalState.Listener listener, Window listenOn) {
        super(info, "You Won", listener, listenOn);
    }
}

