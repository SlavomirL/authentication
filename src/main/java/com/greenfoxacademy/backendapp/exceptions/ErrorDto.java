package com.greenfoxacademy.backendapp.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDto {
    private Integer statusCode;
    private String message;
    private Date timestamp;
}