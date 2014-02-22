package jtrade.functionalinterfaces;

import jtrade.Stock;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This shows us creating our own FunctionalInterfaces.
 */
public class FunctionalInterfaces {
    @Test public void ourOwnPredicate(){
        ShouldSell shouldSell = s -> s.getQuantity() > 1000;
        assertTrue(shouldSell.analyze(new Stock("AAA", 20d, 1050)));
    }
}

@FunctionalInterface interface ShouldSell{
    public Boolean analyze(Stock stock);
}
