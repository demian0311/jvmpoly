package jtrade;

import java.util.List;

public interface PortfolioFactory {
    public Portfolio create(String owner, List<Stock> stocks);
}
