package com.example.mvcSpring.model;

import lombok.Data;

@Data
public class Phone {
    private final int countryPrefix;
    private final long number;
}
