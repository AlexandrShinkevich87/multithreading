import java.util.concurrent.Semaphore;

/**
 * This example shows an example usage of a semaphore where three threads
 * are trying to gain access to a semaphore that only has two places. For
 * the sake of this example each thread takes a place and waits one second
 * before releasing the place.  We will see there is only ever two threads
 * that are within the block between acquire and release.
 * <p>
 * notice that there are only ever two threads in the semaphore
 */
public class SemaphoreExample {
    private static final int DEFAULT_PERMITS = 2;
    private static final int NO_OF_THREADS = 3;
    private Semaphore semaphore = new Semaphore(DEFAULT_PERMITS);

    private void init() {
        for (int i = 0; i < NO_OF_THREADS; ++i) {
            Thread th = new Thread(new SemaphoreTestThread(String.format("Thread-%d", i)));
            th.start();
        }
    }

    /**
     * Each thread will look a few times and attempt to take the semaphore.
     * What we will see is that there will never be more than two threads
     * in the sleep section at once.
     */
    private class SemaphoreTestThread implements Runnable {
        private String name;

        public SemaphoreTestThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < NO_OF_THREADS; i++) {
                    semaphore.acquire();
                    System.out.println("Semaphore acquired. " + name);

                    // only ever two threads here
                    Thread.sleep(1000);

                    semaphore.release();
                    System.out.println("Semaphore released. " + name);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SemaphoreExample example = new SemaphoreExample();
        example.init();
    }
}