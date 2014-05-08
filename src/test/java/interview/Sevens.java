package interview;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Sevens {

    int expected = 341658;

    private boolean hasNoSevenInItString(int number){
        return !((""+number).contains("7"));
    }

    private boolean hasNoSevenInItNoString(int number) {
        if(number < 10){
            return number != 7;
        }

        int rightMost = number % 10;
        if(rightMost == 7){
            return false;
        }

        return hasNoSevenInItNoString((number - rightMost) / 10);
    }

    @Test public void withStrings(){
        int answer = IntStream.range(0, 1000)
                .filter(this::hasNoSevenInItNoString)
                .sum();

        System.out.println("answer: " + answer);
        assertEquals(expected, answer);
    }

    @Test public void test(){
        //IntPredicate f = this::hasNoSevenInItString;
        IntPredicate f = this::hasNoSevenInItNoString;

        // false conditions
        assertFalse(f.test(7));
        assertFalse(f.test(70));
        assertFalse(f.test(700));
        assertFalse(f.test(701));
        assertFalse(f.test(7000));
        assertFalse(f.test(1007));
        assertFalse(f.test(1070));
        assertFalse(f.test(1700));

        // true conditions
        assertTrue(f.test(1234));
        assertTrue(f.test(2345));
        assertTrue(f.test(1));
        assertTrue(f.test(10_000));
        assertTrue(f.test(68_686));

        // edge cases
        assertTrue(f.test(0));
        assertTrue(f.test(10_000));
    }
}
