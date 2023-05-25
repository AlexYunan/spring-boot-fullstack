package com.alexgiounan.springbootexample.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ApiError (
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {
}
