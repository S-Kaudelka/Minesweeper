package engine;

import entity.Field;

import java.awt.Point;

public class MainLoop {

    private static final int dimension = 25;

    private final GameWindow gameWindow;
    private final KeyState keyState;

    //height x width
    private Field[][] gameField;

    private boolean stopGame = false;

    public MainLoop(int height, int width) {
        int actualWidth = width * dimension;
        int actualHeight = height * dimension;
        this.gameWindow = GameWindow.getInstance(actualHeight, actualWidth);
        keyState = this.gameWindow.getKeyState();
        initializeFields(height, width);
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
        int mouseButton = keyState.getButtonClicked();
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

    private void initializeFields(int height, int width) {
        gameField = new Field[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gameField[i][j] = new Field(i * dimension, j * dimension);
            }
        }
    }

    public boolean isStopGame() {
        return stopGame;
    }
}