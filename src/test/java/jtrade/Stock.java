package jtrade;

import java.util.Arrays;
import java.util.List;

public class Stock implements Commodity{
    final String ticker;
    final Double price;
    final Integer quantity;
    // TODO: include an Optional field

    public Stock(String tickerIn, Double priceIn, Integer quantityIn){
        ticker = tickerIn;
        price = priceIn;
        quantity = quantityIn;
    }

    public String getTicker(){ return ticker; }
    public @Override Double getPrice(){ return price; }
    public @Override Integer getQuantity() { return quantity;}

    public String toString(){
        return String.format("Stock(\"%s\", %s, %s)", ticker, price, quantity);
    }

    public static List<Stock> portfolio = Arrays.asList(
            new Stock("TWC", 135.71, 68),
            new Stock("LVLT", 33.96, 22),
            new Stock("GOOG", 1_150.53, 17),
            new Stock("AAPL", 540.67, 30),
            new Stock("MSFT", 36.38, 87),
            new Stock("ORCL", 38.21, 26));
}
