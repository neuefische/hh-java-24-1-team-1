package com.github.hh.backend.handler;

import com.github.hh.backend.exception.NoEmptyStorageSpaceException;
import com.github.hh.backend.exception.NoSuchProductChangeException;
import com.github.hh.backend.exception.NoSuchProductException;
import com.github.hh.backend.exception.NoSuchStorageSpaceException;
import com.github.hh.backend.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchProductException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleNoSuchProductException(NoSuchProductException exception){
        return new ErrorMessage(
                "Product with ID " + exception.getMessage() + " does not exist",
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(NoSuchProductChangeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleNoSuchChangeException(NoSuchProductChangeException exception){
        return new ErrorMessage(
                "Change with ID " + exception.getMessage() + " does not exist. How did you get this message? Seriously, how?",
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(NoEmptyStorageSpaceException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ErrorMessage handleNoEmptyStorageSpaceException(){
        return new ErrorMessage(
                "No empty storage space available",
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(NoSuchStorageSpaceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleNoSuchStorageSpaceException(NoSuchStorageSpaceException exception){
        return new ErrorMessage(
                "Storage space with ID " + exception.getMessage() + " does not exist",
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleIllegalStateException(IllegalStateException exception){
        return new ErrorMessage(
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

}
