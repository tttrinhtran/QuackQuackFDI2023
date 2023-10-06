package com.example.geocare.Schedule;

public class ProductItem {
    private String name;
    private String amount;
    private String description;
    private String category;
    private String shortDescription;
    private int imageIDResource;

    public ProductItem(int imageIDResource, String name, String amount, String description, String category, String shortDescription) {
        this.imageIDResource = imageIDResource;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.shortDescription = shortDescription;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public int getImageIDResource(){
        return imageIDResource;
    }
}
