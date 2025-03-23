package br.com.example.wallpark.controllers.login.dtos;

import java.time.Instant;

public record LoginResponse(Integer id,String accessToken, Instant expiresIn) {
    
}
