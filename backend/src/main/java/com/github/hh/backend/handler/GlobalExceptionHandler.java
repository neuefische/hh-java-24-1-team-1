package com.github.hh.backend.handler;

import com.github.hh.backend.exception.NoSuchProductException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoSuchProductException.class})
    public ResponseEntity<ErrorMessage> handleNoSuchProductException(NoSuchProductException exception,
                                                                     WebRequest webRequest){
        ErrorMessage errorMsg = new ErrorMessage(
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
    }
}
