package com.gorevyonetimi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.gorevyonetimi.util.FileLogger;

@Service
public class QuoteService {

    private static final String QUOTE_API_URL = "https://zenquotes.io/api/random";

    public String fetchMotivationalQuote() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            JsonNode response = restTemplate.getForObject(QUOTE_API_URL, JsonNode.class);
            if (response != null && response.isArray() && response.size() > 0) {
                String quote = response.get(0).get("q").asText();
                String author = response.get(0).get("a").asText();
                String fullQuote = quote + " - " + author;
                FileLogger.log("Dış API'den motivasyon sözü alındı: " + fullQuote);
                return fullQuote;
            }
        } catch (Exception e) {
            FileLogger.log("Motivasyon sözü alınırken hata oluştu: " + e.getMessage());
        }
        return "Motivasyon sözü alınamadı.";
    }
}
