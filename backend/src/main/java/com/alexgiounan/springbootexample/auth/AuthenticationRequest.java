package com.alexgiounan.springbootexample.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}