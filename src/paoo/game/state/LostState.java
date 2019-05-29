package paoo.game.state;

import java.awt.*;

public class LostState extends FinalState {
    LostState(GamePlayInfo info, FinalState.Listener listener, Window listenOn) {
        super(info, "You Lost", listener, listenOn);
    }
}
