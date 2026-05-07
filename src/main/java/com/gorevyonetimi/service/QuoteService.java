package com.gorevyonetimi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.gorevyonetimi.util.FileLogger;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {

    private static final String QUOTE_API_URL = "https://zenquotes.io/api/random";
    private static final long CACHE_TTL_MS = 10 * 60 * 1000;
    private String cachedQuote = "Motivasyon sözü hazırlanıyor.";
    private long cachedAt = 0;

    public String fetchMotivationalQuote() {
        long now = System.currentTimeMillis();
        if (now - cachedAt < CACHE_TTL_MS) {
            return cachedQuote;
        }

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1200);
        requestFactory.setReadTimeout(1200);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        try {
            JsonNode response = restTemplate.getForObject(QUOTE_API_URL, JsonNode.class);
            if (response != null && response.isArray() && response.size() > 0) {
                String quote = response.get(0).get("q").asText();
                String author = response.get(0).get("a").asText();
                cachedQuote = quote + " - " + author;
                cachedAt = now;
                FileLogger.log("Dış API'den motivasyon sözü alındı: " + cachedQuote);
                return cachedQuote;
            }
        } catch (Exception e) {
            FileLogger.log("Motivasyon sözü alınırken hata oluştu: " + e.getMessage());
        }

        return cachedQuote;
    }
}
