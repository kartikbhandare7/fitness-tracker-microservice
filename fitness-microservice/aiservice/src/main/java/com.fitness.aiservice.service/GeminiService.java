package com.fitness.aiservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service

public class GeminiService {

    private final WebClient webClient;
    @Value("${gemini.api.url}")
    private String geminiUrl;
    @Value("${gemini.api.key}")
    private String geminiKey;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient=webClientBuilder.build();
    }

    public String getRecommendations(String details){
        Map<String , Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts" , new Object[]{
                                Map.of("text", details)
                        })
                }
        );
        String response = webClient.post()
                .uri(geminiUrl)
                .header("Content-type", "application/json")
                .header("X-goog-api-key" , geminiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }
}
