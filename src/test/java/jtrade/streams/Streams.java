package jtrade.streams;

import jtrade.Stock;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;
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

    @Test public void mapToInt(){
        int lengthOfStrings = Arrays.asList("FOO", "BAR", "BAZ")
                .stream()                 // source
                .mapToInt(String::length) // intermediate
                .sum();                   // terminal
        assertEquals(9, lengthOfStrings);
    }

    @Test public void flatMap(){
        List<List<String>> twoDimensionalList = Arrays.asList(
                Arrays.asList("a0", "a1", "a2"),
                Arrays.asList("b0", "b1", "b2"),
                Arrays.asList("c0", "c1", "c2"));

        List<String> actual = twoDimensionalList
                .stream()                      // source
                .flatMap(l -> l.stream())      // intermediate
                .collect(Collectors.toList()); // terminal

        List<String> expected =
                Arrays.asList(
                        "a0", "a1", "a2",
                        "b0", "b1", "b2",
                        "c0", "c1", "c2");

        assertEquals(expected, actual);
    }

    @Test public void sorted(){
        List<String> actual = Arrays.asList("FOO", "BAR", "BAZ")
                .stream()                         // source
                .sorted((a, b) -> a.compareTo(b)) // intermediate
                .collect(Collectors.toList());    // terminal

        List<String> expected = Arrays.asList("BAR", "BAZ", "FOO");

        assertEquals(expected, actual);
    }

    @Test public void forEach(){
        Arrays.asList("FOO", "BAR", "BAZ")
                .stream()
                .forEach(System.out::println);
    }

    @Test public void reduce(){
        Integer sum = Arrays.asList(1, 2, 3, 5, 8, 13)
                .stream()                 // source
                .filter(s -> s % 2 == 0)  // intermediate
                .reduce(0, Integer::sum); // terminal
        assertEquals(new Integer(10), sum);
    }

    @Test public void collect(){
        List<Integer> evenIntegers = Arrays.asList(1, 2, 3, 5, 8, 13)
                .stream()                      // source
                .filter(s -> s % 2 == 0)       // intermediate
                .collect(Collectors.toList()); // terminal

        List<Integer> expected = Arrays.asList(2, 8);
        assertEquals(expected, evenIntegers);
    }

    @Test public void collectMultiArgument(){
        List<Integer> evenIntegers = Arrays.asList(1, 2, 3, 5, 8, 13)
                .stream()                      // source
                .filter(s -> s % 2 == 0)       // intermediate
                .collect(                      // terminal
                        ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll);

        List<Integer> expected = Arrays.asList(2, 8);
        assertEquals(expected, evenIntegers);
    }

    @Test public void collectGroupingBy(){
        Map<Boolean, List<Integer>> evensOdds = Arrays.asList(1, 2, 3, 5, 8, 13)
                .stream()
                .collect(Collectors.groupingBy(i -> i % 2 == 0));

        assertEquals(Arrays.asList(1, 3, 5, 13), evensOdds.get(false));
        assertEquals(Arrays.asList(2, 8), evensOdds.get(true));
    }


}
