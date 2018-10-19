package com.hibicode.beerstore.error;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
//@RequiredArgsConstructor(access = PRIVATE)
public class ErrorResponse {

    private final int statusCode;
    private final List<ApiError> errors;

    public ErrorResponse(int statusCode, List<ApiError> errors) {
        this.statusCode = statusCode;
        this.errors = errors;
    }

    public static ErrorResponse of(HttpStatus status, List<ApiError> errors) {
        return new ErrorResponse(status.value(), errors);
    }

    static ErrorResponse of(HttpStatus status, ApiError error) {
        return of(status, Collections.singletonList(error));
    }

    @JsonAutoDetect(fieldVisibility = ANY)
//    @RequiredArgsConstructor
    static class ApiError {
        private String code;
        private String message;

        public ApiError(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
