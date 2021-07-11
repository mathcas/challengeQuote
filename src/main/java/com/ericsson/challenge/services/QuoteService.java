package com.ericsson.challenge.services;

import com.ericsson.challenge.entities.DailyQuote;
import com.ericsson.challenge.entities.Quote;
import com.ericsson.challenge.repositories.QuoteRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {
    private final QuoteRepository repository;
    private final StockService stockService;

    public QuoteService(QuoteRepository quoteRepository, StockService service) {
        this.repository = quoteRepository;
        this.stockService = service;
    }

    public List<Quote> findAll() {
        return repository.findAll();
    }

    public Quote findByStockCode(String stockId) {
        return repository.findByStockCode(stockId);
    }

    public void saveQuote(String stockId, Integer value) {
        Boolean containStock = stockService.containStock(stockId);
        if (!containStock) {
            return;
        }
        Quote quote = repository.findByStockCode(stockId);
        DailyQuote dailyQuote = DailyQuote.builder()
                .date(Date.from(Instant.now()))
                .value(value).build();
        if (quote != null) {
            quote.getQuotes().add(dailyQuote);
        } else {
            ArrayList<DailyQuote> dailyQuotes = new ArrayList<>();
            dailyQuotes.add(dailyQuote);
            quote = Quote.builder()
                    .stockCode(stockId)
                    .quotes(dailyQuotes).build();
        }
        repository.save(quote);
        stockService.notifyCreation();
    }
}
