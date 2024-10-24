package com.test.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class ClientController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("circuitBreaker/testCiruiteBreaker")
	@CircuitBreaker(name = "clientService", fallbackMethod = "handleError")
	public ResponseEntity<String> testCiruiteBreaker() {
		return restTemplate.getForEntity("http://localhost:8081/circuitBreaker/test", String.class);
	}

	public ResponseEntity<?> handleError(Exception exception) {
		return new ResponseEntity<String>("Fallback Method is Active", HttpStatus.OK);
	}

}
