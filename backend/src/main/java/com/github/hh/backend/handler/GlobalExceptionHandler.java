package com.github.hh.backend.handler;

import com.github.hh.backend.exception.NoSuchProductChangeException;
import com.github.hh.backend.exception.NoSuchProductException;
import com.github.hh.backend.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchProductException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleNoSuchProductException(NoSuchProductException exception, WebRequest webRequest){
        return new ErrorMessage(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                "Product with ID " + exception.getMessage() + " does not exist",
                LocalDateTime.now()
        );
    }


    @ExceptionHandler(NoSuchProductChangeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleNoSuchChangeException(NoSuchProductChangeException exception, WebRequest webRequest){
        return new ErrorMessage(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Change with ID " + exception.getMessage() + " does not exist. How did you get this message? Seriously, how?",
                LocalDateTime.now()
        );
    }

}
