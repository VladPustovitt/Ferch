package com.finalproject.frosch.database;

public enum TypeNote {
    INCOME("Income"),
    CONSUMPTION("Consumption"),
    HEADER("Header");

    private final String name;
    TypeNote(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
