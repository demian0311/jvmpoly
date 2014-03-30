package jtrade.lambdas;

import jtrade.Stock;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This shows how we get to Lamdas from instantiating
 * Functional interfaces.
 */
public class Lambdas {

    @Test public void lambdasOnTheirOwn(){
        Runnable r = () -> System.out.println("hello world");
        r.run();

        Predicate<String> isEmpty = (s) -> s.isEmpty();
        assertFalse(isEmpty.test("HELLO"));
    }

    @Test public void functions() {
        Function<Integer, Integer> f = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer num) {
                return num * 2;
            }
        };

        Integer result = f.apply(2);
        assertEquals(Long.valueOf(4), Long.valueOf(result));
    }

    @Test public void function1() {
        Function<Integer, Integer> f = (Integer num) -> {
            return num * 2;
        };

        Integer result = f.apply(2);
        assertEquals(Long.valueOf(4), Long.valueOf(result));
    }

    @Test public void function2() {
        Function<Integer, Integer> f = num -> num * 2;

        Integer result = f.apply(2);
        assertEquals(Long.valueOf(4), Long.valueOf(result));
    }

    private List<String> expected = Arrays.asList("TWC", "GOOG", "AAPL", "MSFT");

    @Test public void withStreams0(){
        Predicate<Stock> myPredicate = new Predicate<Stock>() {
            @Override
            public boolean test(Stock s) {
                return s.getValue() > 1000;
            }
        };

        Function<Stock, String> myFunction = new Function<Stock, String>() {
            @Override
            public String apply(Stock s) {
                return s.getTicker();
            }
        };

        List<String> stocksToSell = Stock.portfolio.stream()
                .filter(myPredicate)
                .map(myFunction)
                .collect(Collectors.toList());

        assertEquals(expected, stocksToSell);
    }

    @Test public void withStreams1(){
        Predicate<Stock> myPredicate = (Stock s) -> {return s.getValue() > 1000; };

        Function<Stock, String> myFunction = (Stock s) -> {return s.getTicker(); };

        List<String> stocksToSell = Stock.portfolio.stream()
                .filter(myPredicate)
                .map(myFunction)
                .collect(Collectors.toList());

        assertEquals(expected, stocksToSell);
    }


    @Test public void withStreams2(){
        Predicate<Stock> myPredicate = s -> s.getValue() > 1000;

        Function<Stock, String> myFunction = s -> s.getTicker();

        List<String> stocksToSell = Stock.portfolio.stream()
                .filter(myPredicate)
                .map(myFunction)
                .collect(Collectors.toList());

        assertEquals(expected, stocksToSell);
    }


    @Test public void withStreams3(){
        List<String> stocksToSell = Stock.portfolio.stream()
                .filter(s -> s.getValue() > 1000)
                .map(s -> s.getTicker())
                .collect(Collectors.toList());

        assertEquals(expected, stocksToSell);
    }
}
