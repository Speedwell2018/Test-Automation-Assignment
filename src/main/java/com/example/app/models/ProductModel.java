package com.example.app.models;

public class ProductModel {
    private final String title;
    private final String price;


    public ProductModel(String title, String price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

}
