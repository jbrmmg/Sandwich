package com.jbr.sandwich.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class SandwichObjectNotFound extends RuntimeException {
    public SandwichObjectNotFound(String type, String id) {
        super("Sandwich Object (" + type + ") [" + id + "] not found.");
    }
}
