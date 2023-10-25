package Engine;

public class Controller implements Runnable {
    public MainLoop mainLoop;

    private boolean isRunning = true;

    /*
       OPEN TO DOs:
     */

    public Controller() {
        mainLoop = new MainLoop(GameWindow.getInstance());
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