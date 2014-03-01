package javafunctional.java8.vjug.projectlambda;

import jtrade.Stock;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import static org.junit.Assert.assertEquals;

/**
 * Functional
 * Programming
 * Constructs
 * And
 * Simpler
 * Concurrency
 * In Java
 */
public class Fpcascij {
    /**
     * - client controls iteration
     * - inherently serial
     * - not thread safe
     */
    @Test public void externalIteration(){
        Double highestValue = Double.MIN_VALUE;
        for(Stock s: Stock.portfolio){
            if(s.getQuantity() > 50){
                if(s.getValue() > highestValue){
                    highestValue = s.getValue();
                }
            }
        }
        assertEquals(9228.28, highestValue, 1);
    }

    /**
     */
    @Test public void internalIteration(){
        Double highestValue = Stock.portfolio.stream()
                .filter(s -> s.getQuantity() > 50)
                .mapToDouble(s -> s.getValue())
                .max().getAsDouble();
        assertEquals(9228.28, highestValue, 1);
    }
}
