package jtrade.collections;

import org.junit.Test;
import static org.junit.Assert.*;
import jtrade.Stock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * http://download.java.net/jdk8/docs/api/java/util/stream/ReductionsTest.html
 *
 * Collectors are a way to get out of the FP stream.
 * Reductions are a special case of terminals.
 */
public class ReductionsTest {

    /**
     * If you go to the toList code you can see that it does
     * the same thing as the multiple argument method.
     */
    @Test public void collectWithCollector(){
        List<String> tickerSymbols = Stock.portfolio.stream() // Stream for processing
                .map(Stock::getTicker)
                .collect(Collectors.toList()); // Turn it into a List

        assertEquals(
                Arrays.asList("TWC", "LVLT", "GOOG", "AAPL", "MSFT", "ORCL"),
                tickerSymbols );
    }

    /**
     * This form of collect gives you more control over the creation of the collection.
     * You give it method references that allow the library to create the collection
     * in parallel.
     */
    @Test public void collectWithMethodReferencesArrayList(){
        List<String> tickerSymbols = Stock.portfolio.stream()
                .map(Stock::getTicker)
                .collect(
                        ArrayList::new,     // supplier
                        ArrayList::add,     // accumulator
                        ArrayList::addAll); // combiner

        assertEquals(
                Arrays.asList("TWC", "LVLT", "GOOG", "AAPL", "MSFT", "ORCL"),
                tickerSymbols );
    }

    /**
     * Here we call the same collect method but instead of creating a list
     * we append all the ticker symbol strings together.
     */
    @Test public void collectWithMethodReferencesStringAppend(){
        String tickerSymbolsString = Stock.portfolio.stream()
                .map(Stock::getTicker)
                .collect(
                        StringBuilder::new,    // supplier
                        StringBuilder::append, // accumulator
                        StringBuilder::append) // combiner
                .toString();

        assertEquals("TWCLVLTGOOGAAPLMSFTORCL", tickerSymbolsString );
    }

    /**
     * We create a map with 2 keys based on the lambda we pass into the
     * groupingBy function.
     */
    @Test public void collectWithGroupingBy(){
        Map<Boolean, List<Stock>> theMap = Stock.portfolio
                .stream()
                .collect(Collectors.groupingBy(s -> s.getValue() > 1_000));

        // Stock ticker symbols with a value greater than 1000
        List<String> expectedTrueList = Arrays.asList("TWC", "GOOG", "AAPL", "MSFT");
        List<String> trueList = theMap.get(true).stream().map(s -> s.getTicker()).collect(Collectors.toList());
        assertEquals(expectedTrueList, trueList);

        // Stock ticker symbols with a value equal to or less than 1000
        List<String> falseList = theMap.get(false).stream().map(s -> s.getTicker()).collect(Collectors.toList());
        List<String> expectedFalseList = Arrays.asList("LVLT", "ORCL");
        assertEquals(expectedFalseList, falseList);
    }

    // TODO-DLN: Collector.toMap
    @Test public void toMap(){
        /* GRRRR
        Map<String, List<Stock>> theMap = Stock.portfolio
                .stream()
                .collect(Collectors.toMap(
                        Stock::getTicker,
                        Function::identity));
                        */
    }

    // TODO-DLN reduce
    // TODO-DLN min
    // TODO-DLN max
    // TODO-DLN count
}
