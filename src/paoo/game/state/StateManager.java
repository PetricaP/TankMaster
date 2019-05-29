package paoo.game.state;

import paoo.game.DatabaseManager;
import paoo.game.Level;
import paoo.game.ScoreInfo;
import paoo.game.menu.GameMenu;
import paoo.game.menu.LevelMenu;

import java.awt.*;

interface StateListener extends GameMenu.Listener, LevelMenu.Listener,
                                PlayState.Listener, FinalState.Listener,
                                ScoresState.Listener, SaveScoreState.Listener {}

public class StateManager implements StateListener {
    public StateManager(Window listenOn, GameState initialState) {
        this.listenOn = listenOn;
        currentState = initialState;
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public GameState getState() {
        return currentState;
    }

    @Override
    public void onPlayClick() {
        currentState.cleanUp();
        currentState = new LevelChooserState(listenOn, this);
    }

    @Override
    public void onExitClick() {
        currentState.cleanUp();
        currentState = new ExitState();
    }

    @Override
    public void onChooseLevel(Level level) {
        currentState.cleanUp();
        currentState = new PlayState(level, this, listenOn);
    }

    @Override
    public void onPlayerDead(GamePlayInfo info) {
        currentState.cleanUp();
        currentState = new LostState(info, this, listenOn);
    }

    @Override
    public void onPlayerWon(GamePlayInfo info) {
        currentState.cleanUp();
        info.score += info.player.getHealth();
        currentState = new WinState(info, this, listenOn);
    }

    @Override
    public void onClickLevels() {
        currentState.cleanUp();
        currentState = new LevelChooserState(listenOn, this);
    }

    @Override
    public void onClickScores(int levelNumber, int score) {
        currentState.cleanUp();
        currentState = new ScoresState(levelNumber, score, this, listenOn);
    }

    @Override
    public void onBackPressed() {
        currentState.cleanUp();
        currentState = new MenuState(this, listenOn);
    }

    @Override
    public void onSaveScorePressed(int level, int score) {
        currentState.cleanUp();
        currentState = new SaveScoreState(level, score, this, listenOn);
    }

    @Override
    public void onEntered(int level, int score, String name, String date) {
        currentState.cleanUp();
        DatabaseManager.insertScore(new ScoreInfo(name, score, date), level);
        currentState = new ScoresState(level, -1, this, listenOn);
    }

    private Window listenOn;
    private GameState currentState;
}
