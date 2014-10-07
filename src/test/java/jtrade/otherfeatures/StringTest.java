package jtrade.otherfeatures;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class StringTest {

    @Test public void stringJoin() {
        List<String> stringList = Arrays.asList("this", "that", "theOther");

        String allTogether = String.join(", ", stringList);
        System.out.println("allTogether: " + allTogether);

        assertEquals("this, that, theOther", allTogether);
    }

    @Test public void stringJoiner() {
        StringJoiner sj = new StringJoiner(", ");
        sj.add("this");
        sj.add("that");
        sj.add("theOther");

        assertEquals("this, that, theOther", sj.toString());
    }

}
