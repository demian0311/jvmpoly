package jtrade.otherfeatures.interfaces;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface Interface{
    void foo();

    default void bar() {
        foo();
        System.out.println("bar");
    }
}

@FunctionalInterface
interface SamInterface{
    void foo();
}

@FunctionalInterface
interface SamInterfaceWithDefault{
    void foo();

    default void bar(){
        foo();
        System.out.println("called foo too");
    }

    //void baz(); // multiple non-overriding abstract methods found in interface...
}

public class InterfaceTest {

    @Test public void usingForEachFromIterable(){
        List<String> stringList = Arrays.asList("foo", "bar", "baz");
        stringList.forEach(System.out::println); // default method on Iterable.
    }


}
