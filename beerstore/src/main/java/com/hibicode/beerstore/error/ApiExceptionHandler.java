package com.hibicode.beerstore.error;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
//@RequiredArgsConstructor
public class ApiExceptionHandler {

    private static final String NO_MESSAGE_AVALILABLE = "No Message available";
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private final MessageSource apiErrorMessageSource;

    public ApiExceptionHandler(MessageSource apiErrorMessageSource) {
        this.apiErrorMessageSource = apiErrorMessageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception,
            Locale locale) {

        Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();

        List<ErrorResponse.ApiError> apiErrors = errors.map(ObjectError::getDefaultMessage)
                .map(code -> toApiError(code, locale))
                .collect(toList());

        return ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors));

    }

    private ErrorResponse.ApiError toApiError(String code, Locale locale) {
        String message;
        try {
            message = apiErrorMessageSource.getMessage(code, null, locale);
        } catch (NoSuchMessageException e) {
            LOGGER.error("Coldn't find any message for {} code under {} locale", code);
            message = NO_MESSAGE_AVALILABLE;
        }
        return new ErrorResponse.ApiError(code, message);
    }
}
