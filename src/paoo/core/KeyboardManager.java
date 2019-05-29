package paoo.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardManager implements KeyListener {
    public boolean isPressed(int c) {
        return keysPressed[c];
    }

    public static KeyboardManager getInstance() {
        if(instance == null) {
            instance = new KeyboardManager();
        }
        return instance;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    private KeyboardManager() {}

    private boolean[] keysPressed = new boolean[2000];
    private static KeyboardManager instance = null;
}
