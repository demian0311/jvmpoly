package jtrade.functionalinterfaces;

import jtrade.Stock;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Demonstrate using the functional interfaces in java.util.function.
 * A lot of times you won't need to declare functions such that you'd want to have
 * them typed as an interface but sometimes you might.  Here are some examples of
 * doing that.  You can also create your own Single Abstract Method Interfaces and
 * use them.
 */
public class ProvidedFunctionalInterfaces {

    /**
     * Predicate takes an argument of a type and always returns a Boolean.
     * This example usage is equivalent to declaring a Function<Stock, Boolean>.
     */
    @Test public void predicate() {
        Predicate<Stock> valueOver1000 = s -> s.getValue() > 1000;
        assertEquals(false, valueOver1000.test(new Stock("FOO", 11.0, 20)));
    }

    /**
     * Function needs the type it takes and the type it returns.
     */
    @Test public void function() {
        Function<Stock, Double> findValue = (Stock s) -> s.getPrice() * s.getQuantity();
        assertEquals(new Double("30"), findValue.apply(new Stock("GOOG", 3.0, 10)));
    }

    /**
     * Supplier takes nothing and returns the type you specify.
     */
    @Test public void supplier() {
        Supplier<Boolean> tradingWindowOpen = () -> true;
        assertTrue(tradingWindowOpen.get());
    }

    /**
     * Consumer takes the type you specify and returns nothing.  The implication here
     * is that the Consumer is hiding some side-effect.  Maybe it's dealing with IO or
     * doing a non-read operation to another system.
     */
    @Test public void consumer() {
        Consumer<Stock> sell = (arg) -> System.out.println("\tSELL ORDER: " + arg);
        sell.accept(new Stock("FOO", 3.0, 12));
    }
}
// TODO-DLN: show a couple of the functions that work with primitives.
// TODO: show an interface that doesn't compile when you try to add multiple abstract methods
// TODO: show that default methods are not counted as abstract methods
