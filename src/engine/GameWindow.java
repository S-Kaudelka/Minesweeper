package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.awt.event.*;

public class GameWindow extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener, JavaUiInitializer {
    private final int GAME_WINDOW_WIDTH;
    private final int GAME_WINDOW_HEIGHT;

    private final BufferStrategy strategy;
    private static GameWindow instance;

    private final JLabel labelFieldsMarked;
    private final JLabel labelNumberMines;

    private boolean restartGame;

    private final Vector<Picture> sprites = new Vector<>();

    private final KeyState keyState = new KeyState();

    /**
     * initializes the instance, if it doesn't exist
     */
    public static GameWindow getInstance(int height, int width) {
        if (instance == null) {
            instance = new GameWindow(height, width);
        }
        return instance;
    }

    /**
     * does not initialize the instance, if it doesn't yet exist
     *
     * @return the instance or null
     */
    public static GameWindow getInstance() {
        return instance;
    }

    private GameWindow(int height, int width) {
        GAME_WINDOW_HEIGHT = height;
        GAME_WINDOW_WIDTH = width;

        // create a frame to contain our game

        JFrame container = new JFrame("Game Window");
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // get hold the content of the frame and set up the 
        // resolution of the game
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT));
        panel.setLayout(null);

        // set up our canvas size and put it into the content of the frame
        setBounds(0, 0, GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT);
        panel.add(this);

        //initializing the UIElements
        int labelWidth = 110;
        int buttonWidth = 110;
        int xPositionLabel = (GAME_WINDOW_WIDTH - 2 * labelWidth) / 3;
        int xPositionButton = (GAME_WINDOW_WIDTH - buttonWidth) / 2;
        labelFieldsMarked = initializeLabel(xPositionLabel, 30, labelWidth, 20, 12, "Marked Fields: 0");
        labelFieldsMarked.setHorizontalAlignment(SwingConstants.CENTER);
        labelNumberMines = initializeLabel(2 * xPositionLabel + labelWidth, 30, labelWidth, 20, 12, "Placed Mines: 0");
        labelNumberMines.setHorizontalAlignment(SwingConstants.CENTER);
        JButton buttonRestart = initializeButton(xPositionButton, 10, buttonWidth, 20, 12, "Restart");
        buttonRestart.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                restartGame(evt);
            }
        });

        panel.add(labelFieldsMarked);
        panel.add(labelNumberMines);
        panel.add(buttonRestart);

        // Tell AWT not to bother repainting our canvas since we're
        // going to do that our self in accelerated mode
        setIgnoreRepaint(true);

        // finally make the window visible 
        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        container.add(this);

        // create the buffering strategy which will allow AWT
        // to manage our accelerated graphics
        createBufferStrategy(2);
        strategy = getBufferStrategy();

        new Thread(this).start();

        addKeyListener(this);

        addMouseMotionListener(this);

        addMouseListener(this);
    }

    public void run() {
        while (true) {
            // work out how long it's been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
//            long delta = System.currentTimeMillis() - lastLoopTime;
//            lastLoopTime = System.currentTimeMillis();

            //maybe do something with this at some point ¯\_(ツ)_/¯

            // Get hold of a graphics context for the accelerated 
            // surface and blank it out
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(new Color(238, 238, 238));
            g.fillRect(0, 0, GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT);

            synchronized (sprites) {
                for (Picture sprite : sprites) {
                    if (sprite.isVisible()) {
                        sprite.draw(g);
                    }
                }
            }

            // finally, we've completed drawing so clear up the graphics
            // and flip the buffer over
            g.dispose();
            strategy.show();

            // finally pause for a bit. Note: this should run us at about
            // 100 fps but on Windows this might vary each loop due to
            // a bad implementation of timer
            try {
                Thread.sleep(10);
            } catch (Exception ignored) {
            }
        }
    }

    public KeyState getKeyState() {
        return keyState;
    }

    public Picture addPicture(String fileName) {
        Picture picture = new Picture(fileName);
        addSprite(picture);
        return picture;
    }

    private void addSprite(Picture picture) {
        synchronized (sprites) {
            sprites.add(picture);
        }
    }

    public Picture changePicture(Picture picture, String fileName) {
        int x = picture.getX();
        int y = picture.getY();
        int height = picture.getHeight();
        int width = picture.getWidth();
        picture.setY(-height);
        Picture newPicture = addPicture(fileName);
        removePicture(picture);
        newPicture.setPosition(x, y);
        newPicture.setHeight(height);
        newPicture.setWidth(width);
        return newPicture;
    }

    public void removePicture(Picture picture) {
        sprites.remove(picture);
    }

    public void setNumberMines(int numberMines) {
        labelNumberMines.setText("Placed Mines: " + numberMines);
    }

    public void setNumberMarked(int numberMarked) {
        labelFieldsMarked.setText("Marked Fields: " + numberMarked);
    }

    private void restartGame(MouseEvent ignore) {
        restartGame = true;
    }

    public void gameHasBeenRestarted() {
        restartGame = false;
    }

    public void keyPressed(KeyEvent e) {
        keyState.add(e);
    }

    public void keyReleased(KeyEvent e) {
        keyState.remove(e);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        keyState.setNewMouseClick(e.getPoint(), e.getButton());
    }

    public void mouseMoved(MouseEvent e) {
        keyState.setMousePosition(e.getPoint());
    }

    public void mouseDragged(MouseEvent e) {

    }

    public boolean isRestartGame() {
        return restartGame;
    }

    public int getGameWindowWidth(){
        return GAME_WINDOW_WIDTH;
    }

    public void hideLabels() {
        labelFieldsMarked.setVisible(false);
        labelNumberMines.setVisible(false);
    }

    public void showLabels() {
        labelFieldsMarked.setVisible(true);
        labelNumberMines.setVisible(true);
    }
}


