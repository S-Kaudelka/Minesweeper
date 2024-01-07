package engine;

public class Controller implements Runnable {
    public MainLoop mainLoop;

    private boolean isRunning = true;
    private boolean roundActive = true;
    private boolean hasBeenRevealed = false;

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

            if (mainLoop.isStopGame() && !hasBeenRevealed) {
                roundActive = false;
                mainLoop.revealGameField();
                hasBeenRevealed = true;
            }
            GameWindow gameWindow = GameWindow.getInstance();
            if (gameWindow.isRestartGame()) {
                mainLoop.hideVictoryImage();
                mainLoop = new MainLoop(height, width);
                roundActive = true;
                gameWindow.gameHasBeenRestarted();
                hasBeenRevealed = false;
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