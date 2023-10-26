package entity;

import engine.GameWindow;
import engine.Picture;

import static entity.FieldNames.*;

public class Field {

    private static final String PREFIX = "field";
    private static final String SUFFIX = ".png";

    private Picture picture;
    private boolean isMine = false;
    private boolean isMarked = false;
    private boolean isFlipped = false;
    private int adjacentMines;

    public Field(int x, int y, int dimension) {
        picture = GameWindow.getInstance().addPicture(getFileName(NEW));
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
                changePicture(getFileName(MINE));
                return false;
            } else {
                changePicture(getFileName(OPEN));
                return true;
            }
        }
    }

    public void changeMarked() {
        if (!isFlipped) {
            isMarked = !isMarked;
            if (isMarked) {
                changePicture(getFileName(MARKED));
            } else {
                changePicture(getFileName(NEW));
            }
        }
    }

    public void revealField() {
        if (!isFlipped) {
            if (isMarked) {
                changePicture(getFileName(MARKED_REVEALED));
            } else {
                if (isMine) {
                    changePicture(getFileName(MINE));
                } else {
                    changePicture(getFileName(OPEN));
                }
            }
        }
    }

    private void changePicture(String fileName) {
        picture = GameWindow.getInstance().changePicture(picture, fileName);
    }

    private String getFileName(FieldNames fieldName) {
        String name;
        switch (fieldName) {
            case NEW:
            case MINE:
            case MARKED:
                name = fieldName.getName();
                break;
            case OPEN:
                name = fieldName.getName() + adjacentMines;
                break;
            case MARKED_REVEALED:
                name = fieldName.getName() + isMine;
                break;
            default:
                name = "";
        }
        return PREFIX + name + SUFFIX;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }
}
