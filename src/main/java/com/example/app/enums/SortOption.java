package com.example.app.enums;

public enum SortOption {
    NAME_ASC("Name (A to Z)"),
    NAME_DESC("Name (Z to A)"),
    PRICE_ASC("Price (low to high)"),
    PRICE_DESC("Price (high to low)");

    public final String text;

    SortOption(String text) {
        this.text = text;
    }
}
