package com.blog.controllers;

import com.blog.entities.RestTemplateUser;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Hidden
@RestController
@RequestMapping("/webFlux")
public class WebFluxController {

    private final WebClient webClient;

    public WebFluxController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com").build();
    }

    //Mono and Flux are both implementations of the Publisher interface. In simple terms, we can say that when we’re doing something
    // like a computation or making a request to a database or an external service, and expecting a maximum of one result, then we should use Mono.
    //When we’re expecting multiple results from our computation, database, or external service call, then we should use Flux.
    //Mono is more relatable to the Optional class in Java since it contains 0 or 1 value, and Flux is more relatable to List since it can have N number of values.
    //https://www.baeldung.com/java-reactor-flux-vs-mono
    @GetMapping("/example")
    public Mono<String> example() {
        return webClient.get().uri("/users").retrieve().bodyToMono(String.class);
    }

    @GetMapping("/example1")
    public Flux<RestTemplateUser> getUsers() {
        return webClient.get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(RestTemplateUser.class);
    }
}
