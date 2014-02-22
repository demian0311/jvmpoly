package jtrade;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.function.Function;

/**
 * Our own Functional Interface or Single Abstract Method.
 * The 'abstract' keyword is implied for interfaces and does not
 * need to be there.
 */
@FunctionalInterface interface FunctionTakesIntegerAndReturnsInteger{
    public abstract Integer abs(Integer arg);
}

/**
 * These are examples of using method references outside of passing them
 * to the collections API.  Really they make the most sense there.
 */
public class MethodReferences {

    /**
     * This uses a provided @FunctionalInterface that is part of the
     * Java8 library.  Other provided interfaces are in java.util.function.
     */
    @Test public void functionMethodReference(){
        // IDE wasn't helpful in finding a Function Interface.
        Function<Integer, Integer> absMethodReference = Math::abs;
        assertEquals(
                new Integer(Math.abs(-33)),
                new Integer(absMethodReference.apply(-33)));
    }

    /**
     * This shows us using our own @FunctionalInterface.  It's an
     * interface that has a Single Abstract Method.
     */
    @Test public void ourOwnFuntionalInterface(){
        FunctionTakesIntegerAndReturnsInteger ftiari = Math::abs;
        assertEquals(
                new Integer(Math.abs(-33)),
                new Integer(ftiari.abs(-33))
        );
    }

    /**
     * Not sure how this is compelling, if you have a factory usually that means
     * you're doing something interesting in the implementation and you don't want
     * callers to know about that complexity.
     */
    @Test public void constructorReferences(){
        PortfolioFactory portfolioFactory = Portfolio::new;
        Portfolio portfolio = portfolioFactory.create("Demian Neidetcher", Stock.portfolio);

        System.out.println("portfolio: " + portfolio);
    }

    /*
    // http://doanduyhai.wordpress.com/2012/07/14/java-8-lambda-in-details-part-iii-method-and-constructor-referencing/
    public static String toUpperStatic(String input){
        return input.toUpperCase();
    }

    @Test public void staticMethodReference(){
        MethodReferenceSam sam = MethodReferences::toUpperStatic;
        assertEquals("HELLO WORLD", sam.doit("hello world"));
    }

    public static String toUpperInstance(String input){
        return input.toUpperCase();
    }

    @Test public void instanceMethodReference(){
        MethodReferenceSam sam = MethodReferences::toUpperInstance;
        assertEquals("HELLO WORLD", sam.doit("Hello World"));
    }
    */
}


