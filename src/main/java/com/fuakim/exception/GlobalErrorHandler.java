package com.fuakim.exception;

import com.fuakim.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalErrorHandler { //extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<CustomErrorResponse>> handleAllExceptions(Exception ex, WebRequest req){

        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(new GenericResponse<>(500, "BACKEND-ERROR", Arrays.asList(errorResponse)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ProblemDetail handleModelNotFoundException(ModelNotFoundException ex, WebRequest req){
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Model Not Found Exception");
        pd.setType(URI.create(req.getContextPath()));
        pd.setProperty("code", "404");
        pd.setProperty("message", "not-found");
        return pd;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<CustomErrorResponse>> handleArgumentNotValid(MethodArgumentNotValidException ex, WebRequest req){

        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(new GenericResponse<>(400, "BAD REQUEST", Arrays.asList(errorResponse)), HttpStatus.BAD_REQUEST);
    }

}
