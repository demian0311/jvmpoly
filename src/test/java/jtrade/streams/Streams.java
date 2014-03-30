package jtrade.streams;

import jtrade.Stock;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {

    @Test public void helloTenTimes0(){
        for(int ii = 0; ii < 10; ii++){
            System.out.println(ii);
        }
    }

    @Test public void streamHelloTenTimes(){
        IntStream
                .range(0, 10)
                .forEach(ii -> System.out.println(ii));
    }

    @Test public void random(){
        new Random()
                .ints(0, 10)
                .limit(10)
                .forEach(ii -> System.out.println(ii));
    }

    @Test public void methodReferences(){
        IntStream
                .range(0, 10)
                .forEach(System.out::println);
    }

    @Test public void streamPipeline(){
        List<String> tickersToSell = Stock.portfolio.stream()
                .filter(s -> s.getValue() > 1000)
                .map(Stock::getTicker)
                .sorted((a, b) -> a.length() - b.length())
                .collect(Collectors.toList());

        List<String> expected = Arrays.asList("TWC", "GOOG", "AAPL", "MSFT");
        assertEquals(expected, tickersToSell);
    }

    @Test public void source(){
        List<String> stringList = Arrays.asList("FOO", "BAR", "BAZ");
        Stream<String> stream = stringList.stream();
        assertEquals(3, stream.count());
    }

    /////////////////////
    //
    // Intermediate Stream Methods
    //
    /////////////////////

    @Test public void filter(){
        List<String> stringList = Arrays.asList("FOO", "BAR", "BAZ")
                .stream()                        // source
                .filter(s -> s.startsWith("B"))  // intermediate
                .collect(Collectors.toList());   // terminal
        assertEquals(Arrays.asList("BAR", "BAZ"), stringList);
    }

    @Test public void map(){
        List<Integer> stringList = Arrays.asList("FOO", "BAR", "BAZ")
                .stream()                        // source
                .map(String::length)             // intermediate
                .collect(Collectors.toList());   // terminal
        assertEquals(Arrays.asList(3, 3, 3), stringList);
    }

}
