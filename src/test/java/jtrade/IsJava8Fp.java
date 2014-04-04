package jtrade;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public class IsJava8Fp extends Assert{

    public int factorial(int i){
        return (i == 0)? 1: factorial(i - 1) * i;
    }

    @Test public void recursion() {
        assertEquals(120, factorial(5));
    }


    public int timesTwo(int i){
        return i * 2;
    }

    @Test public void pureFunctions() {
        assertEquals(10, timesTwo(5));
    }

    @Test public void firstClassFunctions() {
        Function<Integer, Integer> add7 = (x -> x + 7);
        assertEquals(new Integer(12), add7.apply(5));
    }

    public Integer functionWrapper(Function<Integer, Integer> f, Integer arg){
        return f.apply(arg);
    }

    @Test public void functionsThatTakeOtherFunctio() {
        Function<Integer, Integer> times3 = (x -> x * 3);
        assertEquals(new Integer(15), functionWrapper(times3, 5));
    }

    public Function<Integer, Integer> multiplyBy(Integer arg) {
        return (x -> x * arg);
    }

    @Test public void functionsThatCreateFunctions() {
        Function<Integer, Integer> multiplyBy5 = multiplyBy(5);
        assertEquals(new Integer(30), multiplyBy5.apply(6));
    }
}
