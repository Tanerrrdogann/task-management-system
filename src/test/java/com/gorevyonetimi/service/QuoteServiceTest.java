package com.gorevyonetimi.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QuoteServiceTest {

    private final QuoteService quoteService = new QuoteService();

    @Test
    void fetchQuoteShouldNotBeNull() {
        String quote = quoteService.fetchMotivationalQuote();
        assertNotNull(quote);
    }
}
