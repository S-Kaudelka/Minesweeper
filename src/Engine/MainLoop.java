package Engine;

import java.awt.Point;

public class MainLoop {

    private final GameWindow gameWindow;
    private final KeyState keyState;

    private boolean stopGame = false;

    public MainLoop(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        keyState = this.gameWindow.getKeyState();
    }

    public void run() {
        checkKeyInput();

        checkMouseInput();

    }

    public void checkKeyInput() {
        if (keyState.isPressed("B")) {
            //Do something
        }
    }

    public void checkMouseInput() {
        Point click = keyState.pickLastMouseClickPosition();
        if (click == null) {
            return;
        }

        if (pointIsInBetween(click, 1, 10, 1, 10)) {
            //Do something
        }
    }

    private boolean pointIsInBetween(Point p, int minX, int maxX, int minY, int maxY) {
        return p.x >= minX && p.x <= maxX && p.y >= minY && p.y <= maxY;
    }

    public boolean isStopGame() {
        return stopGame;
    }
}