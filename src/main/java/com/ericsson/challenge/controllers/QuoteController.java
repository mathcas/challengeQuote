package com.ericsson.challenge.controllers;

import com.ericsson.challenge.controllers.dto.request.QuoteRequest;
import com.ericsson.challenge.controllers.dto.response.QuoteResponse;
import com.ericsson.challenge.controllers.mappers.QuoteMapper;
import com.ericsson.challenge.entities.Quote;
import com.ericsson.challenge.services.QuoteService;
import com.ericsson.challenge.services.StockService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/quote"})
public class QuoteController {

    private final QuoteService service;
    private final StockService stockService;

    QuoteController(QuoteService service, StockService stockService) {
        this.stockService = stockService;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<QuoteResponse>> findAll() {
        List<Quote> quotes = service.findAll();
        return ResponseEntity.ok(new QuoteMapper().toResponse(quotes));
    }

    @GetMapping(path = {"/{stockCode}"})
    public ResponseEntity<QuoteResponse> findById(@PathVariable String stockCode) {
        Quote quote = service.findByStockCode(stockCode);
        if (quote == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new QuoteMapper().toResponse(quote));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody QuoteRequest quote) {
        service.saveQuote(quote.getStockId(), quote.getValue());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/stockcache")
    public ResponseEntity<HttpStatus> delete(@RequestBody QuoteRequest quote) {
        stockService.deleteCache();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
