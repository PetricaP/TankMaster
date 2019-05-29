package paoo.game.state;

import paoo.core.Entity;
import paoo.core.utils.Vector2D;
import paoo.game.DatabaseManager;
import paoo.game.ScoreInfo;
import paoo.game.entities.tankentities.TankEntity;
import paoo.game.menu.ColoredTextButton;

import java.awt.*;

public class FinalState extends GamePlayState {
    FinalState(GamePlayInfo info, String message, Listener listener, Window listenOn) {
        this.info = info;
        this.listenOn = listenOn;
        this.message = message;
        info.entities.forEach((Entity entity) -> entity.attachDeathListener(this));
        info.entities.forEach((Entity entity) -> {
            if(entity instanceof TankEntity) {
                ((TankEntity)entity).addAttackListener(this);
            }
        });
        levelsButton = new ColoredTextButton(new Vector2D(200, 600), new Vector2D(100, 50),
                listener::onClickLevels, Color.GRAY, Color.BLACK, "Levels", Color.WHITE, buttonFont);

        scoresButton = new ColoredTextButton(new Vector2D(500, 600), new Vector2D(100, 50),
                () -> listener.onClickScores(info.levelNumber, info.score), Color.GRAY, Color.BLACK, "Scores", Color.WHITE, buttonFont);

        listenOn.addMouseListener(levelsButton);
        listenOn.addMouseListener(scoresButton);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(message, 200, 200);
        levelsButton.draw(g);
        scoresButton.draw(g);
    }

    @Override
    public void cleanUp() {
        listenOn.removeMouseListener(levelsButton);
        listenOn.removeMouseListener(scoresButton);
    }

    public interface Listener {
        void onClickLevels();
        void onClickScores(int levelNumber, int scores);
    }

    private Window listenOn;
    private ColoredTextButton levelsButton;
    private ColoredTextButton scoresButton;
    private String message;
    private static Font font = new Font("Manaspace", Font.BOLD, 70);
    private static Font buttonFont = new Font("Manaspace", Font.BOLD, 15);
}
