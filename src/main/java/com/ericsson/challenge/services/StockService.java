package com.ericsson.challenge.services;

import com.ericsson.challenge.gateway.StockGateway;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockGateway stockGateway;

    private List<LinkedHashMap> stocks;

    public StockService(StockGateway gateway) {
        this.stockGateway = gateway;
    }

    public Boolean containStock(String stockId) {
        if (stocks == null) {
            stocks = stockGateway.getAllStocks();
        }
        return stocks.stream().anyMatch(stock -> {
            return stock.containsValue(stockId);
        });
    }

    public void deleteCache() {
        stocks = null;
    }

    public void notifyCreation() {
        stockGateway.notifyCreation();
    }
}
