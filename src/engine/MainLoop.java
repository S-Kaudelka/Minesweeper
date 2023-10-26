package engine;

import entity.Field;

import java.awt.Point;
import java.awt.event.MouseEvent;

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

        int x = click.x / dimension;
        int y = click.y / dimension;

        if (mouseButton == MouseEvent.BUTTON1) {
            boolean continueGame = gameField[x][y].flipField();
            if (!continueGame) {
                stopGame = true;
            }
        } else {
            gameField[x][y].changeMarked();
        }
    }

    private void initializeFields(int height, int width) {
        gameField = new Field[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gameField[i][j] = new Field(i * dimension, j * dimension, dimension);
            }
        }
    }

    public boolean isStopGame() {
        return stopGame;
    }
}