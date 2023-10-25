package Engine;

import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.Point;

public class KeyState {
    HashSet<String> set = new HashSet<>();
    Point lastMouseClickPosition;

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

    public boolean isClickAvailable() {
        return lastMouseClickPosition != null;
    }

    public void setNewMouseClick(Point p) {
        lastMouseClickPosition = p;
    }

    public Point getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(Point p) {
        mousePosition = p;
    }
}
