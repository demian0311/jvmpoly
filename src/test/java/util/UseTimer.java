package util;

import org.junit.Test;
import java.util.stream.IntStream;
import static org.junit.Assert.*;

public class UseTimer {

    /**
     * Micro-benchmarks are always a bad idea on the JVM
     */
    @Test public void testBadMath() {

        Timer timerFor = new Timer("for");
        int sum = 0;
        for(int ii = 0; ii < Integer.MAX_VALUE; ii++){
            sum += ii;
        }
        System.out.println(timerFor);

        assertEquals(1073741825, sum);

        Timer timerRange = new Timer("range");
        int sum2 = IntStream.range(0, Integer.MAX_VALUE).sum();
        System.out.println(timerRange);

        assertEquals(1073741825, sum2);
    }
}
