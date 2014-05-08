package jtrade.lambdas;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.Assert.*;

/**
 * Breaking down what a SAM Interface is.
 */
public class SamInterfaces {

    // Your own functional interfaces

    @FunctionalInterface
    interface StringManip {
        String doIt(String arg);
        //String doThat(String arg);

        default public String toUpper(String s){
           return s.toUpperCase();
        }
    }

    @Test
    public void stringManip(){
        StringManip stringManip = (ii) -> ii.toUpperCase();
        assertEquals("HELLO", stringManip.doIt("hello"));
    }

    // SAM Interfaces were already in Java

    @Test public void runnable(){
        Runnable r = () -> System.out.println("Hello World");
        r.run();
    }

    @Test public void comparator(){
        Comparator<String> c = (a, b) -> a.length() - b.length();
        assertEquals(0, c.compare("AAA", "BBB"));
    }

    // New SAMs to make lambdas work better
    @Test public void function(){
        Function<String, Integer> getLength = (s) -> s.length();
        assertEquals(new Integer(3), getLength.apply("AAA"));
    }

    @Test public void predicate(){
        Predicate<String> isEmpty = (s) -> s.isEmpty();
        assertTrue(isEmpty.test(""));
    }

    @Test public void consumer(){
        Consumer<String> say = (s) -> System.out.println(s);
        say.accept("Hello World");
    }

    @Test public void supplier(){
        Supplier<String> currTime = () -> ""+System.currentTimeMillis();
        System.out.println("s: " + currTime.get());
    }



}




