package com.example.geocare.Scan;

public class ProductInformation {
    private String brandName;
    private String productName;
    private String productType;
    private String ingredients;


    public ProductInformation(String brandName, String productName, String productType, String ingredients ) {
        this.brandName = brandName;
        this.productType = productType;
        this.ingredients = ingredients;
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getProductType() {
        return productType;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getProductName(){
        return productName;
    }
}

