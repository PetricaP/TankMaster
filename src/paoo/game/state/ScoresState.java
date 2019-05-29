package paoo.game.state;

import paoo.core.utils.Vector2D;
import paoo.game.DatabaseManager;
import paoo.game.ScoreInfo;
import paoo.game.menu.ColoredTextButton;

import java.awt.*;
import java.util.ArrayList;

public class ScoresState implements GameState {
    public ScoresState(int level, int score, Listener listener, Window listenOn) {
         scoreInfos = DatabaseManager.getScores(level);
         this.level = level;
         this.score = score;
         this.listenOn = listenOn;

         backButton = new ColoredTextButton(new Vector2D(200, 600), new Vector2D(100, 50),
                listener::onBackPressed, Color.GRAY, Color.BLACK, "Back", Color.WHITE, font);
         listenOn.addMouseListener(backButton);
         if(score > 0) {
             saveScoreButton = new ColoredTextButton(new Vector2D(500, 600), new Vector2D(150, 50),
                     () -> listener.onSaveScorePressed(level, score), Color.GRAY, Color.BLACK, "Save Score", Color.WHITE, font);
             listenOn.addMouseListener(saveScoreButton);
         }
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics g) {
        int y = 200;
        scoreInfos.sort((ScoreInfo info1, ScoreInfo info2) -> info2.getScore() - info1.getScore());
        g.setFont(font);
        g.drawString("Level " + level, 300, 100);
        for(ScoreInfo scoreInfo : scoreInfos) {
            String message = scoreInfo.getName() + " ".repeat(15 - scoreInfo.getName().length())
                    + scoreInfo.getScore() + "    " + scoreInfo.getDate();
            g.setColor(Color.BLACK);
            g.drawString(message, 100, y);
            y += 100;
            if(y > 500) {
                break;
            }
        }
        backButton.draw(g);
        if(score > 0) {
            saveScoreButton.draw(g);
        }
    }

    @Override
    public void cleanUp() {
        listenOn.removeMouseListener(backButton);
        listenOn.removeMouseListener(saveScoreButton);
    }

    public interface Listener {
        void onBackPressed();
        void onSaveScorePressed(int level, int score);
    }

    private static Font font = new Font("Manaspace", Font.BOLD, 20);

    private Window listenOn;
    private ColoredTextButton backButton;
    private ColoredTextButton saveScoreButton;
    private ArrayList<ScoreInfo> scoreInfos;
    private int level;
    private int score;
}
