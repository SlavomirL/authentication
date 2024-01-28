package com.greenfoxacademy.backendapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SumDTO {
    private LocalDate localDate;
    private double sum;
}
