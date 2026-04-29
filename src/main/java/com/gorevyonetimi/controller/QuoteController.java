package com.gorevyonetimi.controller;

import com.gorevyonetimi.service.QuoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quote")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/motivational")
    public String getMotivationalQuote() {
        return quoteService.fetchMotivationalQuote();
    }
}
