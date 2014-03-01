package javafunctional.java8.examples;

import jtrade.Stock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JustifyingLambdas {

    private List<Stock> portfolio = Arrays.asList(
            new Stock("GOOG", 400.0, 759, "FOO"),
            new Stock("AAPL", 5.0, 100, "FOO"),
            new Stock("MSFT", 5.0, 19, "FOO"),
            new Stock("CSCO", 5.0, 84, "FOO"));

    @Test public void isAppleThere() {
        boolean isAppleThere = false;

        for (Stock currStock : portfolio) {
            if (currStock.getTicker().equals("AAPL")) {
               isAppleThere = true;
            }
        }

        assertTrue(isAppleThere);
    }

    @Test public void isAppleThereFunctional() {
       Boolean isAppleThere = portfolio.stream().anyMatch(s -> s.getTicker().equals("AAPL"));
       assertTrue(isAppleThere);
    }

    @Test public void stocksWeHaveManyOf() {
        List<String> stocksWeHaveManyOf = new ArrayList<String>();

        portfolio.sort(new Comparator<Stock>() {
            @Override public int compare(Stock o1, Stock o2) {
                return o1.getQuantity() - o2.getQuantity();
            }
        });

        for (Stock currStock : portfolio) {
            if(currStock.getQuantity() >= 100){
                stocksWeHaveManyOf.add(currStock.getTicker());
            }
        }
        assertEquals(Arrays.asList("AAPL", "GOOG"), stocksWeHaveManyOf);
    }

    @Test public void stocksWeHaveManyOfFunctional() {
        List<String> stocksWeHaveManyOf = portfolio.stream()
                .filter(s -> s.getQuantity() >= 100)   // filter it
                .sorted(comparing(Stock::getQuantity)) // sort it
                .map(s -> s.getTicker())               // turn it into a String
                .collect(Collectors.toList());         // turn it into a List<String>
        assertEquals(Arrays.asList("AAPL", "GOOG"), stocksWeHaveManyOf);
    }

    /*
    @Test public void valueOfPortfolio(){
        Integer valueOfPortfolio = 0;

        for(Stock currStock: portfolio){
            valueOfPortfolio += currStock.getQuantity() * currStock.getPrice();
        }

        assertEquals(new Integer(304615), valueOfPortfolio);
    }

    @Test public void valueOfPortfolioFunctional(){
        Integer valueOfPortfolio = portfolio.stream()
                .mapToInt(s -> s.getQuantity() * s.getPrice())
                .sum();

        assertEquals(new Integer(304615), valueOfPortfolio);
    }*/
}
