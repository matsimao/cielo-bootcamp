package com.cielo.bootcamp.prospect.application.dtos;

public record ExceptionDTO(
        String message,
        Integer statusCode
) {
}
