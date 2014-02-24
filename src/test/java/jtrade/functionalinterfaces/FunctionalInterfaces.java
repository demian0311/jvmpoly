package jtrade.functionalinterfaces;

import jtrade.Stock;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This shows us creating our own FunctionalInterfaces.
 */
public class FunctionalInterfaces {
    @Test public void ourOwnPredicate(){
        ShouldSell shouldSell = s -> s.getQuantity() > 1000;
        assertTrue(shouldSell.analyze(new Stock("AAA", 20d, 1050)));
    }
}

/**
 * Single Abstract Method Interface.
 */
@FunctionalInterface interface ShouldSell{
    public Boolean analyze(Stock stock);
}

/**
 * If you annotate an interface with @FunctionalInterface then
 * you will get a compiler error if you have 2 abstract methods
 * defined.
 */
@FunctionalInterface interface Foo{
    // The Single Abstract Method
    public Boolean foo();

    /*
     * java: Unexpected @FunctionalInterface annotation
     *  jtrade.functionalinterfaces.Foo is not a functional interface
     *  multiple non-overriding abstract methods found in interface jtrade.functionalinterfaces.Foo
     */
    //public Boolean bar();

    /**
     * This is not an abstract method because it's a default
     * method and has a definition.
     */
    default Boolean baz(){ return true; }
}
