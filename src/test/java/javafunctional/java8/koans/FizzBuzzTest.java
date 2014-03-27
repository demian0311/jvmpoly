package javafunctional.java8.koans;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class FizzBuzzTest {

    public String fizzBuzz(int numberIn){
        String whatToSay = "";
        if (numberIn % 3 == 0){ whatToSay += "Fizz"; }
        if (numberIn % 5 == 0){ whatToSay += "Buzz"; }
        return whatToSay.isEmpty() ? ""+numberIn : whatToSay;
    }

    @Test public void runIt() {
        IntStream
                .range(0, 100)
                .forEach(ii -> System.out.println(fizzBuzz(ii)));
    }

    @Test public void testFizzBuzz() {
        assertEquals("79", fizzBuzz(79));
        assertEquals("2", fizzBuzz(2));

        assertEquals("Fizz", fizzBuzz(3));
        assertEquals("Fizz", fizzBuzz(-3));
        assertEquals("Fizz", fizzBuzz(33));

        assertEquals("Buzz", fizzBuzz(5));
        assertEquals("Buzz", fizzBuzz(-5));
        assertEquals("Buzz", fizzBuzz(25));

        assertEquals("FizzBuzz", fizzBuzz(0));
        assertEquals("FizzBuzz", fizzBuzz(45));

        assertEquals("-2147483648", fizzBuzz(Integer.MIN_VALUE));
        assertEquals("2147483647", fizzBuzz(Integer.MAX_VALUE));
    }
}
