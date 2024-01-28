package com.greenfoxacademy.backendapp.exceptions;

public class MealNotFoundException extends RuntimeException {

    private static final long serialVersionID = 1;

    public MealNotFoundException(String message) {
        super(message);
    }
}