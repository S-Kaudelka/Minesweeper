package engine;

public class Controller implements Runnable {
    public MainLoop mainLoop;

    private boolean isRunning = true;

    /*
       OPEN TO DOs: everything
       - add Error Text below the start button in the main menu
     */

    public Controller(int height, int width) {
        mainLoop = new MainLoop(height, width);
        new Thread(this).start();
    }

    public void run() {
        while (isRunning) {
            mainLoop.run();

            if (mainLoop.isStopGame()) {
                stop();
            }
            try {
                //keep the game at a stable speed
                Thread.sleep(20);
            } catch (Exception ignored) {
            }
        }
    }

    public void stop() {
        isRunning = false;
    }
}