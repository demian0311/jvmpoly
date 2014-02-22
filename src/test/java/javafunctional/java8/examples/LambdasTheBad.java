package javafunctional.java8.examples;

import jtrade.Stock;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class LambdasTheBad {

    private List<Stock> stocks = Arrays.asList(
            new Stock("AAPL", 5.0, 1),
            new Stock("MSFT", 5.0, 1),
            new Stock("CSCO", 5.0, 1),
            new Stock("GOOG", 400.0, 1));

    /*
    @Test public void test() {
        try{
            stocks.stream().mapToInt(s -> s.getPrice() / 0).sum();
        }
        catch(ArithmeticException e){
            e.printStackTrace();
            System.out.println("stack trace length: " + e.getStackTrace().length);
        }
    }*/
/*
    @Test public void test1() {
        try {
            for (Stock currStock : stocks) {
                int foo = currStock.getPrice() / 0;
            }
        }
        catch(ArithmeticException e){
            e.printStackTrace();
            System.out.println("stack trace length: " + e.getStackTrace().length);
        }
    }
    */
}
