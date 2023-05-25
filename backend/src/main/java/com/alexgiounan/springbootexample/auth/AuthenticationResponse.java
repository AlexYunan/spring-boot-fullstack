package com.alexgiounan.springbootexample.auth;

import com.alexgiounan.springbootexample.dto.CustomerDTO;

public record AuthenticationResponse (
        String token,
        CustomerDTO customerDTO){
}