package Server;

import Manager.Manager;

/**
 *
 * @author wanderson
 */
public class Watcher extends Thread {

    private final long timer;
    private final int maxMinutes;

    public Watcher(long timer, int maxMinutes) {
        this.timer = timer;
        this.maxMinutes = maxMinutes;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(this.timer);
                Manager m = Manager.getInstance();
                System.out.println("Cleasing old servers...");
                m.removeOld(this.maxMinutes);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        } while(true);
    }
}
