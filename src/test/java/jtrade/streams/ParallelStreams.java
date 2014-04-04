package jtrade.streams;

import jtrade.Stock;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class ParallelStreams {

    private void sell(String ticker){
        try{
            Thread.sleep(500);
            System.out.println("sold: " + ticker);
        } catch(Throwable t){
            System.out.println(t.getMessage());
        }
    }

    @Test public void test(){
        Long begin = System.currentTimeMillis();

        Long numSold = Stock.portfolio.stream()
                .filter(s -> s.getValue() > 10_000)
                .map(Stock::getTicker)
                .peek(this::sell)
                .count();

        assertTrue(2 == numSold);
        Long duration = System.currentTimeMillis() - begin;
        System.out.println("serial: " + duration + "ms");
    } // 1062, 1062, 1063

    @Test public void testParallel(){
        Long begin = System.currentTimeMillis();

        Long numSold = Stock.portfolio.parallelStream()
                .filter(s -> s.getValue() > 10_000)
                .map(Stock::getTicker)
                .peek(this::sell)
                .count();

        assertTrue(2 == numSold);
        Long duration = System.currentTimeMillis() - begin;
        System.out.println("parallel: " + duration + "ms");
    } // 508, 508, 508

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
        System.out.println("actual: " + actual[0]);

        int[] actualParallel = {0};
        IntStream.range(0, 100)
                .parallel()
                .forEach(i -> actualParallel[0] += i);
        assertNotEquals(expected, actualParallel[0]);
        System.out.println("actualParallel: " + actualParallel[0]);
    }
}
