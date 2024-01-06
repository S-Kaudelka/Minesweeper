package engine;

public class Controller implements Runnable {
    public MainLoop mainLoop;

    private boolean isRunning = true;
    private boolean roundActive = true;

    private final int height;
    private final int width;

    /*
       OPEN TO DOs: everything
       - improve distribution of mines
     */

    public Controller(int height, int width) {
        this.height = height;
        this.width = width;
        mainLoop = new MainLoop(height, width);
        new Thread(this).start();
    }

    public void run() {
        while (isRunning) {
            if (roundActive) {
                mainLoop.run();
            }

            if (mainLoop.isStopGame()) {
                roundActive = false;
                mainLoop.revealGameField();
            }
            GameWindow gameWindow = GameWindow.getInstance();
            if (gameWindow.isRestartGame()) {
                mainLoop = new MainLoop(height, width);
                roundActive = true;
                gameWindow.gameHasBeenRestarted();
            }
            try {
                //keep the game at a stable speed
                Thread.sleep(20);
            } catch (Exception ignored) {
                isRunning = false;
            }
        }
    }
}