package jtrade.otherfeatures.interfaces;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

interface Foo{
    void foo();
}

@FunctionalInterface
interface Bar{
    void bar();
    //void baz(); // multiple non-overriding abstract methods foudn in interface...
}

public class InterfaceTest {

    @Test public void usingForEachFromIterable(){
        List<String> stringList = Arrays.asList("foo", "bar", "baz");
        stringList.forEach(System.out::println); // default method on Iterable.
    }


}
