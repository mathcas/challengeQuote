package com.ericsson.challenge.services;

import com.ericsson.challenge.gateway.StockGateway;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StockServiceTest {

    private StockService stockService;
    private StockGateway stockGateway;

    @BeforeEach
    void setUp() {
        stockGateway = Mockito.mock(StockGateway.class);
        stockService = new StockService(stockGateway);
    }

    @Test
    @DisplayName("Don´t find stocks")
    void containStockWithoutStock() {
        // Arrange
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("petr4", "10");
        linkedHashMap.put("petr5", "10");
        linkedHashMap.put("petr6", "10");
        linkedHashMap.put("petr7", "10");
        ArrayList<LinkedHashMap<String, String>> arrayList = new ArrayList();
        arrayList.add(linkedHashMap);
        Mockito.when(stockGateway.getAllStocks()).thenReturn(arrayList);

        // Act
        Boolean contains = stockService.containStock("");

        // Assert
        assertEquals(false, contains);
        Mockito.verify(stockGateway, Mockito.times(1)).getAllStocks();
    }

    @Test
    @DisplayName("Find stock")
    void containStockWithStock() {
        // Arrange
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("petr4", "10");
        linkedHashMap.put("petr5", "10");
        linkedHashMap.put("petr6", "10");
        linkedHashMap.put("petr7", "12");
        ArrayList<LinkedHashMap<String, String>> arrayList = new ArrayList();
        arrayList.add(linkedHashMap);
        Mockito.when(stockGateway.getAllStocks()).thenReturn(arrayList);

        // Act
        Boolean contains = stockService.containStock("12");

        // Assert
        assertEquals(true, contains);
        Mockito.verify(stockGateway, Mockito.times(1)).getAllStocks();
    }

    @Test
    @DisplayName("Delete Cache and verify if all Stocks is called twice")
    void containStockAndDeleteCache() {
        // Arrange
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("petr4", "10");
        linkedHashMap.put("petr5", "10");
        linkedHashMap.put("petr6", "10");
        linkedHashMap.put("petr7", "12");
        ArrayList<LinkedHashMap<String, String>> arrayList = new ArrayList();
        arrayList.add(linkedHashMap);
        Mockito.when(stockGateway.getAllStocks()).thenReturn(arrayList);

        // Act
        Boolean contains = stockService.containStock("12");
        stockService.deleteCache();
        contains = stockService.containStock("12");

        // Assert
        assertEquals(true, contains);
        Mockito.verify(stockGateway, Mockito.times(2)).getAllStocks();
    }

    @Test
    @DisplayName("Don´t Delete Cache and verify if all Stocks is called twice")
    void containStockAndNotDeleteCache() {
        // Arrange
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("petr4", "10");
        linkedHashMap.put("petr5", "10");
        linkedHashMap.put("petr6", "10");
        linkedHashMap.put("petr7", "12");
        ArrayList<LinkedHashMap<String, String>> arrayList = new ArrayList();
        arrayList.add(linkedHashMap);
        Mockito.when(stockGateway.getAllStocks()).thenReturn(arrayList);

        // Act
        Boolean contains = stockService.containStock("12");
        contains = stockService.containStock("12");

        // Assert
        assertEquals(true, contains);
        Mockito.verify(stockGateway, Mockito.times(1)).getAllStocks();
    }

    @Test
    void notifyCreation() {
        // Act
        stockService.notifyCreation();

        // Assert
        Mockito.verify(stockGateway, Mockito.times(1)).notifyCreation();
    }
}