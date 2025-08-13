package com.nc.resume._project.resume.service;

import reactor.core.publisher.Mono;

public interface AiClient {
    String call(String userMessage);
    Mono<String> callAsync(String userMessage);
}