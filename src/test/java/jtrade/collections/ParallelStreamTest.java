package jtrade.collections;

import com.google.common.base.Stopwatch;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.stream.IntStream;


/**
 * Output should be something like:
 * - parallel stream : 411ms
 * - serial stream   : 5066ms
 */
public class ParallelStreamTest {

    @Test public void stream(){
        Stopwatch sw = new Stopwatch().start();

        IntStream.range(0, 100)
                .forEach(ii -> sleepUninterrupted());

        Long stopTime = sw.stop().elapsedMillis();
        System.out.println("\nserial stream   : " + stopTime + "ms");
    }

    @Test public void parallelStream() throws Exception{
        Stopwatch sw = new Stopwatch().start();

        IntStream.range(0, 100)
                .parallel()
                .forEach(ii -> sleepUninterrupted());

        Long stopTime = sw.stop().elapsedMillis();
        System.out.println("\nparallel stream : " + stopTime + "ms");
    }

    /**
     * Parallelization is where you can get into trouble if you try
     * to get around the Java restriction of not referencing
     * 'effectively final' variables within a lamda.
     */
    @Test public void singleElementArrayTrick(){
        int expected = IntStream.range(0, 100)
                .parallel()
                .reduce(Integer::sum)
                .getAsInt();
        assertEquals(4950, expected);

        int[] actual = {0};
        IntStream.range(0, 100)
                .forEach(i -> actual[0] += i);
        assertEquals(expected, actual[0]);

        int[] actualParallel = {0};
        IntStream.range(0, 100)
                .parallel()
                .forEach(i -> actualParallel[0] += i);
        System.out.println("actualParallel: " + actualParallel[0]);
        assertNotEquals(expected, actualParallel[0]);
    }

    private void sleepUninterrupted(){
        try{
            Thread.sleep(50);
        } catch(Throwable t){
            System.out.println(t.getMessage());
        }
    }
}
