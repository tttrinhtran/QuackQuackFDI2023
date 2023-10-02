package com.example.geocare.Product;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private boolean isFavorite;
    private int imageResourceId;
    private String type;
    private String brandname;
    private String ingredients;
    private int imageDetail;
    private String nameDetail;

    public Item(String name,String nameDetail, boolean isFavorite, int imageResourceId, String type, String brandname, String ingredients, int imageDetail) {
        this.name = name;
        this.isFavorite = isFavorite;
        this.imageResourceId = imageResourceId;
        this.type = type;
        this.brandname = brandname;
        this.ingredients = ingredients;
        this.imageDetail = imageDetail;
        this.nameDetail = nameDetail;
    }

    // Getter and setter methods for all fields

    // Implementing Serializable
    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrandname() {
        return brandname;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getType() {
        return type;
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

    public int getImageDetail(){return imageDetail;}

    public String getNameDetail(){return nameDetail;}

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
