package com.mynt.logistics.controller;

import com.mynt.logistics.integration.DownstreamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ParcelControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {  IllegalArgumentException.class })
    public ResponseEntity<String> handleApplicationError(HttpServletRequest request, Exception exception) {
        log.error("Delivery Management API | handleApplicationError | {}" ,exception);
        return error(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }

    @ResponseStatus
    @ExceptionHandler(value = { DownstreamException.class  })
    public ResponseEntity<String> handleDpwnstreamError(HttpServletRequest request, Exception exception) {
        log.error("Delivery Management API | DownstreamException | : {}" ,exception.getLocalizedMessage());
        HttpStatus status  = HttpStatus.BAD_REQUEST;
        if (exception.getMessage().contains("[IO]")) {
            status = HttpStatus.FAILED_DEPENDENCY;
        }
        return error(status, exception.getMessage());
    }

    private ResponseEntity<String> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(message);
    }
}
