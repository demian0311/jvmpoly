package jtrade;

import java.util.List;

public class Portfolio {
    String owner;
    List<Stock> stocks;

    public Portfolio(String owner, List<Stock> stocks) {
        this.owner = owner;
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "owner='" + owner + '\'' +
                ", stocks=" + stocks +
                '}';
    }
}

