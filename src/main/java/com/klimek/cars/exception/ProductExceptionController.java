package com.klimek.cars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ProductExceptionController {
    @ExceptionHandler(value = ProductNotfoundException.class)
    public ResponseEntity<Object> exception(ProductNotfoundException exception) {
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ProductNotRentException.class)
    public ResponseEntity<Object> exception(ProductNotRentException exception) {
        return new ResponseEntity<>("Product not rent", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = ProductRentedException.class)
    public ResponseEntity<Object> exception(ProductRentedException exception) {
        return new ResponseEntity<>("Product is rented", HttpStatus.BAD_REQUEST);
    }
}
