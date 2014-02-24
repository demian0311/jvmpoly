package jtrade.collections;

import org.junit.Test;
import static org.junit.Assert.*;
import jtrade.Stock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * http://download.java.net/jdk8/docs/api/java/util/stream/CollectorsTest.html
 *
 * Collectors are a way to get out of the FP stream.
 * TODO: maybe rename this to ReductionOperations
 */
public class CollectorsTest {

    @Test public void collect(){
        List<String> tickerSymbols = Stock.portfolio.stream() // Stream for processing
                .map(Stock::getTicker)
                .collect(Collectors.toList()); // Turn it into a List

        assertEquals(
                Arrays.asList("TWC", "LVLT", "GOOG", "AAPL", "MSFT", "ORCL"),
                tickerSymbols );
    }

    @Test public void collect2(){
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

    @Test public void collect3(){
        String tickerSymbolsString = Stock.portfolio.stream()
                .map(Stock::getTicker)
                .collect(
                        StringBuilder::new,    // supplier
                        StringBuilder::append, // accumulator
                        StringBuilder::append) // combiner
                .toString();

        assertEquals("TWCLVLTGOOGAAPLMSFTORCL", tickerSymbolsString );
    }

    // TODO-DLN: Collector.toMap

    @Test public void test(){
        Map<Boolean, List<Stock>> theMap = Stock
                .portfolio
                .stream()
                .collect(Collectors.groupingBy(s -> s.getValue() > 1000));
        // The lambda will create a boolean, so we'll have 2 keys in
        // our map: true and false

        System.out.println("theMap: " + theMap);

        List<Stock> stuffLessThan1000 = theMap.get(false);
        System.out.println("stuffLessThan1000: " + stuffLessThan1000);
    }

    // TODO-DLN reduce
    // TODO-DLN min
    // TODO-DLN max
    // TODO-DLN count

}
