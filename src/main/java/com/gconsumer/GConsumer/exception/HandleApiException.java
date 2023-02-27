package com.gconsumer.GConsumer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLDataException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@RestControllerAdvice
@RestController
public class HandleApiException {

    @ExceptionHandler(SQLDataException.class)
    public ResponseEntity<ErrorDetails> handleApiException(SQLDataException me, WebRequest request) {
        Map<String, String> mapError = new HashMap<>();
       mapError.put("error_message", String.valueOf(me.getCause()));
        ErrorDetails details =  ErrorDetails.build(mapError, "Internal Error", request.getDescription(false));
       // details.addError("SQL");
        return new ResponseEntity<>(details, INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(SQLDataException.class)
//    public ResponseEntity<ErrorDetails> handleApiException(SQLDataException ex, WebRequest request) {
//        Map<String, String> mapError = new HashMap<>();
//        mapError.put("error", ex.getMessage());
//
//
//        ErrorDetails errorDetails = ErrorDetails.build(mapError, "Validation Error", request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleRequiredFieldsException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> mapError = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((FieldError error) ->
                mapError.put(error.getField(), error.getDefaultMessage())
        );
        ErrorDetails errorDetails = ErrorDetails.build(mapError, "Required Fields Error", request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleApiException(RuntimeException me, WebRequest request) {
        Map<String, String> mapError = new HashMap<>();
        mapError.put("error_message", me.getLocalizedMessage());
        ErrorDetails details = ErrorDetails.build(mapError, me.getClass().getSimpleName(), request.getDescription(false));
        return new ResponseEntity<>(details, INTERNAL_SERVER_ERROR);
    }


}
