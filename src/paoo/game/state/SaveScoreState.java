package paoo.game.state;

import paoo.core.utils.Vector2D;
import paoo.game.DatabaseManager;
import paoo.game.menu.EditText;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SaveScoreState extends KeyAdapter implements GameState {
    public SaveScoreState(int level, int score, Listener listener, Window listenOn) {
        this.level = level;
        this.score = score;
        this.listener = listener;
        this.listenOn = listenOn;
        listenOn.addKeyListener(this);
        editText = new EditText(new Vector2D(300, 400), new Vector2D(200, 50), font, 12);
        listenOn.addKeyListener(editText);
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics g) {
        editText.draw(g);
    }

    @Override
    public void cleanUp() {
        listenOn.removeKeyListener(this);
        listenOn.removeKeyListener(editText);
    }

    private static Font font = new Font("Manaspace", Font.BOLD, 25);

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == '\n') {
            String text = editText.getText();
            if(!text.isEmpty()) {
                listener.onEntered(level, score, text, DatabaseManager.returnCurrentDate());
            }
        }
    }

    interface Listener {
        void onEntered(int level, int score, String name, String date);
    }

    private Window listenOn;
    private Listener listener;
    private int level;
    private int score;
    private EditText editText;
}
