package com.example.geocare.Product;

public class Item {
    private String name;
    private boolean isFavorite;
    private int imageResourceId; // Resource ID for the product image

    public Item(String name, boolean isFavorite, int imageResourceId) {
        this.name = name;
        this.isFavorite = isFavorite;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
