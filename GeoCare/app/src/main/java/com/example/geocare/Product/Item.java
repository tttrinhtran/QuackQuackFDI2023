package com.example.geocare.Product;

import android.content.Context;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private boolean isFavorite;
    private String imageResourceId;
    private String type;
    private String brandname;
    private String ingredients;
    private String imageDetail;
    private String nameDetail;
    private String uri;

    public Item() {
    }

    public Item(String name, String nameDetail, boolean isFavorite, String imageResourceId, String type, String brandname, String ingredients, String imageDetail, String uri) {
        this.name = name;
        this.isFavorite = isFavorite;
        this.imageResourceId = imageResourceId;
        this.type = type;
        this.brandname = brandname;
        this.ingredients = ingredients;
        this.imageDetail = imageDetail;
        this.nameDetail = nameDetail;
        this.uri = uri;
    }

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

    public String getImageResourceId() {
        return imageResourceId;
    }

    public String getImageDetail(){return imageDetail;}

    public String getNameDetail(){return nameDetail;}

    public void setImageResourceId(String imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
    public  void setFavourite(boolean isFavorite){
        this.isFavorite = isFavorite;
    }
    public  String getUri(){return uri;}

    public int getImageResourceIdByInt(Context context)
    {
        int id = context.getResources().getIdentifier(this.imageResourceId, "drawable", context.getPackageName());
        return id;
    }
    public int getImageDetailResourceIdByInt(Context context)
    {
        int id = context.getResources().getIdentifier(this.imageDetail, "drawable", context.getPackageName());
        return id;
    }
}
