package jtrade.lambdas;

import jtrade.Stock;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Demonstrate functions that create other functions and functions that take in functions.
 */
public class HigherOrderFunctions {

    /**
     * This higher-order function returns a function.
     */
    public Function<Stock, Stock> createBalanceStockAlgorithm(Double maximumValueOfStock){
        return (Stock s) -> {
            Double overMaxValue = s.getValue() - maximumValueOfStock;
            if(overMaxValue > 0) {
                Integer numberToSell = (int)Math.ceil(overMaxValue / s.getPrice());
                System.out.println(String.format(">> SELL %s shares of %s", numberToSell, s.getTicker()));
                return new Stock(s.getTicker(), s.getPrice(), s.getQuantity() - numberToSell, s.getExchange());
            } else {
                return s;
            }
        };
    }

    @Test public void shouldNotSell(){
        Function<Stock, Stock> alg = createBalanceStockAlgorithm(5.0);
        Stock result = alg.apply(new Stock("FOO", 3.0, 1, "FOO"));
        assertEquals("1", result.getQuantity().toString());
    }

    @Test public void shouldSell() {
        Function<Stock, Stock> alg = createBalanceStockAlgorithm(5.0);
        Stock result = alg.apply(new Stock("FOO", 3.0, 3, "FOO"));
        assertTrue(result.getValue() <= 5.0);
    }

    @Test public void shoudlSellExactMatch(){
        Function<Stock, Stock> alg = createBalanceStockAlgorithm(5.0);
        Stock result = alg.apply(new Stock("FOO", 5.0, 2, "FOO"));
        assertTrue(result.getValue() <= 5.0);
        assertEquals("1", result.getQuantity().toString());
    }

    /**
     * This higher-order function takes in a function as an argument.
     */
    public List<Stock> maintainPortfolio(Function<Stock, Stock> algorithm, List<Stock> portfolio){

        return portfolio.stream()
                .peek(s -> System.out.println("EVALUATING: " + s.getTicker()))
                .map(algorithm)
                .collect(Collectors.toList());
    }

    @Test public void maintainPortfolioTest() {
        Function<Stock, Stock> algorithm = createBalanceStockAlgorithm(1000.0);
        List<Stock> result = maintainPortfolio(algorithm, Stock.portfolio);
        result.stream().forEach(s -> assertTrue(s.getValue() <= 1000.0));
    }
}
