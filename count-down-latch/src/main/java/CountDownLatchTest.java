
import java.util.concurrent.*;
import java.util.*;
import java.text.*;

/**
 * Main thread creates 10 Database Connections and Creates 10 different Threads
 * and assigns those DB connection to the threads one each.
 * But the Main thread must wait until all 10 Threads finish their DB Operation before closing the DB Connections.
 * Children will exit after performing the DB Operation.
 * A CountDownLatch can be used in this scenario.
 */
public class CountDownLatchTest {
    private static final int MAX_THREADS = 5;

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(MAX_THREADS);

        System.out.println("Spawning Threads");
        for (int i = 0; i < MAX_THREADS; i++) {
            Thread t = new Thread(new WorkerThread(countDownLatch, String.format("Thread-%d", i)));
            t.start();
        }
        System.out.println("Spawning Finished");
        System.out.println("Waiting All Threads to Finish");
        countDownLatch.await(); // Await is void
        System.out.println("All Threads are Finished");
    }

    private static class WorkerThread implements Runnable {
        private CountDownLatch countDownLatch;

        private String name;

        public WorkerThread(CountDownLatch countDownLatch, String name) {
            this.name = name;
            this.countDownLatch = countDownLatch;
        }

        public void run() {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                System.out.printf("%s : Doing Some Work on %s\n", getFormattedDate(sdf), name);
                Thread.sleep(getRandomWaitTime());
                System.out.printf("%s : Doing Some more work on %s\n", getFormattedDate(sdf), name);
                Thread.sleep(getRandomWaitTime());
                System.out.printf("%s : Finished work on %s\n", getFormattedDate(sdf), name);
                countDownLatch.countDown();
                System.out.printf("%s : Count Down Latch count on %s is %d\n", getFormattedDate(sdf), name, countDownLatch.getCount());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String getFormattedDate(SimpleDateFormat sdf) {
            return sdf.format(new Date());
        }

        private int getRandomWaitTime() {
            return (int) ((Math.random() + 1) * 1000);
        }

    }
}
