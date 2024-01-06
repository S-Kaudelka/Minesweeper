package engine;

import entity.Field;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class MainLoop {

    private static final int dimension = 25;
    private static final double mineRatio = 0.17;
    private static final int offsetAtTop = 50;

    private final KeyState keyState;

    private int numberMarked = 0;

    //height x width
    private Field[][] gameField;

    private boolean stopGame = false;

    public MainLoop(int height, int width) {
        int actualWidth = width * dimension;
        int actualHeight = height * dimension + offsetAtTop;
        //initialize GameWindow and save reference to keyState
        keyState = GameWindow.getInstance(actualHeight, actualWidth).getKeyState();
        initializeFields(width, height);
    }

    public void run() {
        checkMouseInput();
    }

    public void checkMouseInput() {
        Point click = keyState.pickLastMouseClickPosition();
        int mouseButton = keyState.getButtonClicked();
        if (click == null) {
            return;
        }

        if (click.y - offsetAtTop < 0) {
            return;
        }

        int x = click.x / dimension;
        int y = (click.y - offsetAtTop) / dimension;

        if (mouseButton == MouseEvent.BUTTON1) {
            boolean continueGame = gameField[x][y].flipField();
            if (continueGame) {
                revealAdjacentFields(x, y);
            } else {
                stopGame = true;
            }
        } else {
            gameField[x][y].changeMarked();
            if (gameField[x][y].isMarked()) {
                numberMarked++;
            } else {
                numberMarked--;
            }

            GameWindow.getInstance().setNumberMarked(numberMarked);
        }
    }

    private void revealAdjacentFields(int x, int y) {
        if (gameField[x][y].getAdjacentMines() == 0) {
            if (x > 0) {
                automaticallyFlipField(x - 1, y);
                if (y > 0) {
                    automaticallyFlipField(x - 1, y - 1);
                }
                if (y < gameField[0].length - 1) {
                    automaticallyFlipField(x - 1, y + 1);
                }
            }
            if (y > 0) {
                automaticallyFlipField(x, y - 1);
            }
            if (y < gameField[0].length - 1) {
                automaticallyFlipField(x, y + 1);
            }
            if (x < gameField.length - 1) {
                automaticallyFlipField(x + 1, y);
                if (y > 0) {
                    automaticallyFlipField(x + 1, y - 1);
                }
                if (y < gameField[0].length - 1) {
                    automaticallyFlipField(x + 1, y + 1);
                }
            }
        }
    }

    private void automaticallyFlipField(int x, int y) {
        if (!(gameField[x][y].isFlipped() || gameField[x][y].isMarked())) {
            gameField[x][y].flipField();
            revealAdjacentFields(x, y);
        }
    }

    private void initializeFields(int width, int height) {
        int maxNumberMines = (int) (height * width * mineRatio);
        gameField = new Field[width][height];
        int minesPlaced = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gameField[i][j] = new Field(i * dimension, j * dimension + offsetAtTop, dimension);
                if (minesPlaced < maxNumberMines && placeMineRNG()) {
                    minesPlaced++;
                    gameField[i][j].setMine(true);
                }
            }
        }

        GameWindow.getInstance().setNumberMines(minesPlaced);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                countAdjacentMines(i, j);
            }
        }
    }

    public boolean isStopGame() {
        return stopGame;
    }

    /**
     * generates a random number and checks it against the mineRatio to determine,
     * whether a Mine should be placed
     */
    private boolean placeMineRNG() {
        int random = (int) (Math.random() * 100);
        return random <= mineRatio * 100;
    }

    private void countAdjacentMines(int x, int y) {
        int numberMines = 0;

        if (x > 0) {
            numberMines += checkFieldForMine(x - 1, y);
            if (y > 0) {
                numberMines += checkFieldForMine(x - 1, y - 1);
            }
            if (y < gameField[0].length - 1) {
                numberMines += checkFieldForMine(x - 1, y + 1);
            }
        }
        if (y > 0) {
            numberMines += checkFieldForMine(x, y - 1);
        }
        if (y < gameField[0].length - 1) {
            numberMines += checkFieldForMine(x, y + 1);
        }
        if (x < gameField.length - 1) {
            numberMines += checkFieldForMine(x + 1, y);
            if (y > 0) {
                numberMines += checkFieldForMine(x + 1, y - 1);
            }
            if (y < gameField[0].length - 1) {
                numberMines += checkFieldForMine(x + 1, y + 1);
            }
        }

        gameField[x][y].setAdjacentMines(numberMines);
    }

    private int checkFieldForMine(int x, int y) {
        if (gameField[x][y].isMine()) {
            return 1;
        } else {
            return 0;
        }
    }

    public void revealGameField() {
        for (Field[] fields : gameField) {
            for (Field field : fields) {
                field.revealField();
            }
        }
    }
}