package entity;

import engine.GameWindow;
import engine.Picture;

public class Field {

    private static final String PREFIX = "field";
    private static final String SUFFIX = ".png";

    private Picture picture;
    private boolean isMine = false;
    private boolean isMarked = false;
    private boolean isFlipped = false;
    private int adjacentMines;

    public Field(int x, int y, int dimension) {
        picture = GameWindow.getInstance().addPicture(getFileName("New"));
        picture.setPosition(x, y);
        picture.setHeight(dimension);
        picture.setWidth(dimension);
    }

    /**
     * returns false, if it is a mine, else it returns true
     *
     * @return if the game continues
     */
    public boolean flipField() {
        if (isFlipped || isMarked) {
            return true;
        } else {
            isFlipped = true;
            if (isMine) {
                changePicture(getFileName("Mine"));
                return false;
            } else {
                changePicture(getFileName("Open" + adjacentMines));
                return true;
            }
        }
    }

    public void changeMarked() {
        if (!isFlipped) {
            isMarked = !isMarked;
            if (isMarked) {
                changePicture(getFileName("Marked"));
            } else {
                changePicture(getFileName("New"));
            }
        }
    }

    public void revealField() {
        if (!isFlipped) {
            if (isMarked) {
                changePicture(getFileName("Marked" + isMine));
            } else {
                changePicture(getFileName("Mine"));
            }
        }
    }

    private void changePicture(String fileName) {
        picture = GameWindow.getInstance().changePicture(picture, fileName);
    }

    private String getFileName(String name) {
        return PREFIX + name + SUFFIX;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }
}
