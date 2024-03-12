package com.github.hh.backend.handler;

import com.github.hh.backend.exception.NoSuchChangeException;
import com.github.hh.backend.exception.NoSuchProductException;
import com.github.hh.backend.model.ErrorMessage;
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
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                "Product with ID " + exception.getMessage() + " does not exist",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoSuchChangeException.class})
    public ResponseEntity<ErrorMessage> handleNoSuchChangeException(NoSuchChangeException exception,
                                                                    WebRequest webRequest){
        ErrorMessage errorMsg = new ErrorMessage(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Change with ID " + exception.getMessage() + " does not exist. How did you get this message? Seriously, how?",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
