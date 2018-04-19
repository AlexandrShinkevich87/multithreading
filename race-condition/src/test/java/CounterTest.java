import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;

import static org.junit.Assert.assertEquals;

@RunWith(ConcurrentTestRunner.class)
public class CounterTest {
    private final Counter counter = new Counter();
    private final static int THREAD_COUNT = 5;


    @Test
    @ThreadCount(THREAD_COUNT)
    public void testAdd() {
        counter.addOne();
    }

    @After
    public void testCount() {
        assertEquals("Value should be 6", 7, counter.getI());
    }


}