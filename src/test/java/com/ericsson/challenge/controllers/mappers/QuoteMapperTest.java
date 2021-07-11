package com.ericsson.challenge.controllers.mappers;

import com.ericsson.challenge.controllers.dto.response.QuoteResponse;
import com.ericsson.challenge.entities.DailyQuote;
import com.ericsson.challenge.entities.Quote;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuoteMapperTest {

    private QuoteMapper quoteMapper;

    @BeforeEach
    void setUp() {
        quoteMapper = new QuoteMapper();
    }

    @Test
    @DisplayName("Converting List Quote in List QuoteResponse")
    void toResponse() {
        // Arrange
        ArrayList<DailyQuote> dailyQuotes = new ArrayList<>();
        DailyQuote dailyQuote = new DailyQuote(1L, new Date(1580515200000L), 10);
        dailyQuotes.add(dailyQuote);
        Quote quote = new Quote(1L, "12", dailyQuotes);
        Quote quote2 = new Quote(2L, "13", dailyQuotes);
        Quote quote3 = new Quote(3L, "14", dailyQuotes);
        Quote quote4 = new Quote(4L, "16", dailyQuotes);
        ArrayList<Quote> quotes = new ArrayList<>();
        quotes.add(quote);
        quotes.add(quote2);
        quotes.add(quote3);
        quotes.add(quote4);

        // Act
        List<QuoteResponse> quoteResponse = quoteMapper.toResponse(quotes);

        // Assert
        assertEquals(quotes.size(), quoteResponse.size());
    }

    @Test
    @DisplayName("Converting Quote in QuoteResponse")
    void testToResponse() {
        // Arrange
        ArrayList<DailyQuote> dailyQuotes = new ArrayList<>();
        DailyQuote dailyQuote = new DailyQuote(1L, new Date(1580515200000L), 10);
        dailyQuotes.add(dailyQuote);
        Quote quote = new Quote(1L, "12", dailyQuotes);

        // Act
        QuoteResponse quoteResponse = quoteMapper.toResponse(quote);

        // Assert
        assertEquals("1", quoteResponse.getId());
        assertEquals("10", quoteResponse.getQuotes().values().stream().findFirst().get());
        assertEquals("2020-02-01", quoteResponse.getQuotes().keySet().stream().findFirst().get());
    }
}