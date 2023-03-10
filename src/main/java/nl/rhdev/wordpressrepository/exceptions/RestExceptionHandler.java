package nl.rhdev.wordpressrepository.exceptions;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Mono<ExceptionResponse> handleNoSuchElementException(NoSuchElementException e) {
        return Mono.just(new ExceptionResponse("Not found", HttpStatus.NOT_FOUND));
    }

}
