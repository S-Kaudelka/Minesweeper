package engine;

import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.Point;

public class KeyState {
    HashSet<String> set = new HashSet<>();
    Point lastMouseClickPosition;
    int buttonClicked;

    Point mousePosition = new Point(0, 0);

    public KeyState() {
    }

    public void add(KeyEvent keyEvent) {
        set.add(KeyEvent.getKeyText(keyEvent.getKeyCode()));
    }

    public void remove(KeyEvent keyEvent) {
        set.remove(KeyEvent.getKeyText(keyEvent.getKeyCode()));
    }

    public boolean isPressed(String key) {
        return set.contains(key);
    }

    public Point pickLastMouseClickPosition() {
        synchronized (this) {
            Point ret = lastMouseClickPosition;

            lastMouseClickPosition = null;
            return ret;
        }
    }

    public int getButtonClicked() {
        synchronized (this) {
            int button = buttonClicked;
            buttonClicked = 0;
            return button;
        }
    }

    public boolean isClickAvailable() {
        return lastMouseClickPosition != null;
    }

    public void setNewMouseClick(Point p, int button) {
        lastMouseClickPosition = p;
        buttonClicked = button;
    }

    public Point getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(Point p) {
        mousePosition = p;
    }
}
