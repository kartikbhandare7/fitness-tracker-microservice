package com.fitness.activityservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }


    @Bean
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder.baseUrl("http://USER-SERVICE")
                .build();
    }

}
