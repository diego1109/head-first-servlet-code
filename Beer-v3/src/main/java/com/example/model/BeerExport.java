package com.example.model;

import static java.util.Arrays.asList;
import java.util.List;

public class BeerExport {

    public List getBrands(String color) {
        return color.equals("amber") ? asList("Jack Amber", "Rad Moose") : asList("Jail Pale Ale", "Gout Stout");
    }
}
