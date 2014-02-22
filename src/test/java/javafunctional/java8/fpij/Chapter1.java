package javafunctional.java8.fpij;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class Chapter1 {

    @Test public void habitualWay(){
        final List<String> cities = Arrays.asList(
                "Chicago",
                "New York",
                "Miami",
                "Denver",
                "New Orleans");

        boolean found = false;
        for(String currCity: cities){
            if(currCity.equals("Miami")){
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test public void betterWay(){
        final List<String> cities = Arrays.asList("Chicago", "New York", "Miami", "Denver", "New Orleans");
        boolean found = cities.contains("Miami");

        assertTrue(found);
    }

    @Test public void theOldWay(){
        final List<Integer> prices = Arrays.asList(10, 30, 17, 20, 15, 18, 45, 12);
        double totalDiscountedPrices = 0.0;

        for(int currPrice: prices){
           if(currPrice > 20){
               totalDiscountedPrices += currPrice * 0.9;
           }
        }

        assertEquals(67.5, totalDiscountedPrices, 0.5);
    }

    @Test public void betterWayAgain(){
        final List<Integer> prices = Arrays.asList(10, 30, 17, 20, 15, 18, 45, 12);

        final double totalDiscountedPrices = prices.stream()
                .filter(price -> price > 20)
                .mapToDouble(price -> price * 0.9)
                .sum();

        assertEquals(67.5, totalDiscountedPrices, 0.5);
    }
}
