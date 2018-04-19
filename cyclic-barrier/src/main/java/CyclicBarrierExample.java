import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * This class shows the usage of the CyclicBarrier class in the form
 * of a simple three thread example. We wait for all threads to
 * reach the cyclic barrier and then the threads print a log line
 * and exit.
 *
 * notice that all threads wait at the barrier until the last one reaches there,
 * and then they all proceed.
 */
public class CyclicBarrierExample {
    // create a cyclic barrier that will wait for three calls before
    // unlocking
    private static final int NO_OF_THREADS = 3;
    private CyclicBarrier barrier = new CyclicBarrier(NO_OF_THREADS);

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrierExample instance = new CyclicBarrierExample();
        instance.init();
    }

    public void init() throws InterruptedException {
        // create three threads that will call await on the cyclic barrier
        for (int i=0; i<NO_OF_THREADS; ++i) {
            Thread th = new Thread(new MyWorkerThread(String.format("Worker-%d", i)), "Worker" + i);
            th.start();

            // to help visualise I add a delay between creation
            Thread.sleep(1000);
        }
    }

    /**
     * Here is the runnable used by the above threads, it just waits for the
     * barrier before proceeding to log a line and exit. Notice that the
     * barrier unlocked log line only occurs after all three threads reach
     * the barrier.
     */
    private class MyWorkerThread implements Runnable {
        private String name;

        public MyWorkerThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Thread started. " + name);

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println("Barrier unlocked. " + name);
        }
    }
}