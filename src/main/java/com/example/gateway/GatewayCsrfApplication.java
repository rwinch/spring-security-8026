package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@SpringBootApplication
@Controller
public class GatewayCsrfApplication {

  public static void main(String[] args) {
		SpringApplication.run(GatewayCsrfApplication.class, args);
	}

  @GetMapping(value = "/")
  public Mono<String> index(ServerWebExchange exchange, Model model) {
    return getCsrfToken(exchange)
        .doOnNext(
            csrfToken -> {
              if (csrfToken != null) {
                model.addAttribute("csrfToken", csrfToken.getToken());
              }
            })
        .thenReturn("index");
	}

	private Mono<CsrfToken> getCsrfToken(ServerWebExchange exchange) {
		return exchange.getAttributeOrDefault(CsrfToken.class.getName(), Mono.empty());
	}

}
