package edu.ort.da.obligatorio.Excepciones;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final int errorCode = 299;

    @ExceptionHandler(PeajeException.class)
    public ResponseEntity<String> handleException(PeajeException ex) {
        return ResponseEntity.status(errorCode).body(ex.getMessage());
    }
}
