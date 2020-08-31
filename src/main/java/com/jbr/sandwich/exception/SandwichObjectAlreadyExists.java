package com.jbr.sandwich.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class SandwichObjectAlreadyExists extends RuntimeException {
    public SandwichObjectAlreadyExists(String type, String id) {
        super("Sandwich Object (" + type + ") [" + id + "] already exists.");
    }
}
