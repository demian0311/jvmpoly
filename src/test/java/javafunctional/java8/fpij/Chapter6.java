package javafunctional.java8.fpij;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Chapter6 {

    private static int length(String valueIn) {
        System.out.println("finding length: " + valueIn);
        return valueIn.length();
    }

    private static String toUpper(String valueIn) {
        System.out.println("converting to upper: " + valueIn);
        return valueIn.toUpperCase();
    }


    /**
     * Observe that we don't find all of the lengths, as soon as we find
     * one that satisfies the first filter we pass it onto the next method.
     */
    @Test public void foo() {
        List<String> names = Arrays.asList(
                "Brad", "Kate", "Kim", "Jack", "Joe", "Mike", "Susan",
                "George", "Robert", "Julia", "Parker", "Benson");
        final String firstNameWithThreeLetters =
                names.stream()
                .filter(n -> length(n) == 3)
                .map(n -> toUpper(n))
                .findFirst()
                .get();
        assertEquals("KIM", firstNameWithThreeLetters);
    }
}
