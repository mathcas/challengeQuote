package com.ericsson.challenge.services;

import com.ericsson.challenge.entities.DailyQuote;
import com.ericsson.challenge.entities.Quote;
import com.ericsson.challenge.gateway.StockGateway;
import com.ericsson.challenge.repositories.QuoteRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QuoteServiceTest {
    private QuoteService quoteService;
    private QuoteRepository repository;
    private StockService stockService;
    private StockGateway stockGateway;
    private Quote quote;

    @BeforeEach
    void setUp() {
        stockGateway = Mockito.mock(StockGateway.class);
        repository = Mockito.mock(QuoteRepository.class);
        stockService = Mockito.mock(StockService.class);
        quoteService = new QuoteService(repository, stockService);

        ArrayList<DailyQuote> dailyQuotes = new ArrayList<>();
        DailyQuote dailyQuote = new DailyQuote(1L, new Date(1580515200000L), 10);
        dailyQuotes.add(dailyQuote);

        quote = new Quote(1L, "12", dailyQuotes);
    }

    @Test
    @DisplayName("Find all quotes from repository")
    void testMethodFindAll() {
        //Arrange
        List<Quote> listQuote = new ArrayList<>();
        listQuote.add(quote);
        Mockito.when(repository.findAll()).thenReturn(listQuote);

        //Act

        List<Quote> list = quoteService.findAll();

        //Assert
        assertEquals(list, listQuote);

    }

    @Test
    @DisplayName("Find quote by stock code")
    void testMethodFindByStockCode() {
        //Arrange
        Mockito.when(repository.findByStockCode(quote.getStockCode())).thenReturn(quote);

        //Act
        Quote quoteAct = quoteService.findByStockCode(quote.getStockCode());

        //Assert
        assertEquals(quote, quoteAct);
    }

    @Test
    @DisplayName("Find quote by stock code but the code doesn´t exist")
    void testMethodFindByStockCodeWhenStockCodeDoesNotExist() {
        //Arrange
        Mockito.when(repository.findByStockCode(quote.getStockCode())).thenReturn(null);

        //Act
        Quote quoteAct = quoteService.findByStockCode(quote.getStockCode());

        //Assert
        assertNull(quoteAct);
    }

    @Test
    @DisplayName("Trying save quote but the doesnt contain stock")
    void testMethodSaveQuoteWhenDoesntContainStock() {
        //Arrange
        Mockito.when(stockService.containStock(quote.getStockCode())).thenReturn(false);

        //Act
        quoteService.saveQuote(quote.getStockCode(), 10);

        //Assert
        Mockito.verify(stockService, Mockito.times(1)).containStock(quote.getStockCode());
    }

    @Test
    @DisplayName("Saving quote but is a update daily quote")
    void testMethodSaveQuoteButIsAUpdate() {
        //Arrange
        Mockito.when(stockService.containStock(quote.getStockCode())).thenReturn(true);
        Mockito.when(repository.findByStockCode(quote.getStockCode())).thenReturn(quote);

        //Act
        quoteService.saveQuote(quote.getStockCode(), 10);

        //Assert
        Mockito.verify(stockService, Mockito.times(1)).containStock(quote.getStockCode());
        Mockito.verify(repository, Mockito.times(1)).findByStockCode(quote.getStockCode());
        Mockito.verify(repository, Mockito.times(1)).save(quote);
    }

    @Test
    @DisplayName("Saving a new quote")
    void testMethodSaveQuote() {
        //Arrange
        Mockito.when(stockService.containStock(quote.getStockCode())).thenReturn(true);
        Mockito.when(repository.findByStockCode(quote.getStockCode())).thenReturn(null);

        //Act
        quoteService.saveQuote(quote.getStockCode(), 10);

        //Assert
        Mockito.verify(stockService, Mockito.times(1)).containStock(quote.getStockCode());
        Mockito.verify(repository, Mockito.times(1)).findByStockCode(quote.getStockCode());
    }

}