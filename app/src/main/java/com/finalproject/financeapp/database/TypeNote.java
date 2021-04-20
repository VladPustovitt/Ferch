package com.finalproject.financeapp.database;

public enum TypeNote {
    INCOME("Income"),
    CONSUMPTION("Consumption");
    private final String name;
    TypeNote(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
