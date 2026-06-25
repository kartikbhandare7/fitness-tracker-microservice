package com.fitness.gateway.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(String userId){
        log.info("Calling userService for {}", userId);

            return userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate" , userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .onErrorResume(WebClientResponseException.class , e ->{
                        if (e.getStatusCode()== HttpStatus.NOT_FOUND)
                            return Mono.error(new RuntimeException("User not found" + userId));
                        else if(e.getStatusCode() == HttpStatus.BAD_REQUEST)
                            return Mono.error(new RuntimeException("Invalid" + userId));
                        else return Mono.error(new RuntimeException("Unexpected Error" + userId));
                    });

    }

    public Mono<UserResponse> registerUser(RegisterRequest registerRequest) {
        log.info("Calling User Registration for {}", registerRequest.getEmail());
        return userServiceWebClient.post()
                .uri("/api/users/register")
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class , e ->{

                     if(e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("Bad Request" + e.getMessage()));
                    else return Mono.error(new RuntimeException("Unexpected Error" + e.getMessage()));
                });
    }
}
