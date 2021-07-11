package com.ericsson.challenge.controllers.mappers;

import com.ericsson.challenge.controllers.dto.response.QuoteResponse;
import com.ericsson.challenge.entities.Quote;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuoteMapper {

    public List<QuoteResponse> toResponse(List<Quote> quotes) {
        List<QuoteResponse> quoteResponses = new ArrayList<>();
        quotes.forEach(quote -> {
            quoteResponses.add(toResponse(quote));
        });
        return quoteResponses;
    }

    public QuoteResponse toResponse(Quote stock) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> dailyQuotes = new HashMap<>();
        stock.getQuotes().forEach(quote -> {
                    dailyQuotes.put(
                            simpleDateFormat.format(quote.getDate()),
                            quote.getValue().toString()
                    );
                }
        );
        return QuoteResponse.builder()
                .id(stock.getId().toString())
                .stockId(stock.getStockCode())
                .quotes(dailyQuotes)
                .build();
    }
}
