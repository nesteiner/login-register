package com.example.loginregister.configure;

import com.example.loginregister.exception.LoginException;
import com.example.loginregister.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<String> handleException(LoginException exception) {
        String message = exception.getMessage();
        return Result.Err(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleException(ConstraintViolationException exception) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        constraintViolations.forEach(constraintViolation -> {
            String _message = constraintViolation.getMessage();
            message.append("[").append(_message).append("]");
        });

        return Result.Err(message.toString());

    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleException(IOException exception) {
        String message = exception.getMessage();
        return Result.Err(message);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleException(UsernameNotFoundException exception) {
        String message = exception.getMessage();
        return Result.Err(message);
    }

}
