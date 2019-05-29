package paoo.game;

import paoo.core.*;

import javax.swing.*;
import java.awt.*;

import paoo.game.state.*;

public class Game extends JFrame implements Application {
    public Game() {
		DrawCanvas canvas = new DrawCanvas();
    	addKeyListener(KeyboardManager.getInstance());
    	setLocation(600, 100);

		add(canvas);

    	setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
    	pack();
    	setLocationRelativeTo(null);

    	BORDER_SIZE = BOARD_SIZE - getContentPane().getSize().height;

    	setVisible(true);
    	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		stateManager = new StateManager(this, null);
		stateManager.setState(new MenuState(stateManager, this));
	}

	public void run() {
		while(!(stateManager.getState() instanceof ExitState)) {
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			update();
			repaint();
			Toolkit.getDefaultToolkit().sync();
		}
	}

	private void update() {
	    stateManager.getState().update();
	}


	class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(stateManager != null) {
				if(stateManager.getState() != null) {
					stateManager.getState().draw(g);
				}
			}
		}
    }

	public static final int BOARD_SIZE = 800;
	public static int BORDER_SIZE;

    private StateManager stateManager;
}
