package com.example.geocare.Schedule;

import com.example.geocare.R;

import java.util.ArrayList;

public class Type {
    // Declare productList as a static ArrayList
    public static ArrayList<ProductItem> productList_OilySunny = new ArrayList<>();

    // Static block to initialize the productList
    static {
        productList_OilySunny.add(new ProductItem(R.drawable.cleanser_oil,"Herbivore - PINK CLOUD Rosewater + Tremella Creamy Jelly Cleanser", "Pea-size pump", "Massage a pea-sized amount onto damp skin, then rinse.", "Cleanser", "Get rid of product"));
        productList_OilySunny.add(new ProductItem(R.drawable.moisturizer_oil,"Juice Beauty - Stem Cellular Anti-Wrinkle Moisturizer", "Small amount", "Apply a small amount evenly to your face and neck.", "Moisturizer", "Hydrate skin"));
        productList_OilySunny.add(new ProductItem(R.drawable.serum_oil,"innisfree - Youth Enhancing Serum", "2-3 drops", "Pat 2-3 drops until absorbed.", "Serum", "For youthful skin"));
        productList_OilySunny.add(new ProductItem(R.drawable.toner_oil,"innisfree - Rebalancing Toner with Blueberry", "Few drops", "Pat a few drops onto your face and neck.", "Toner", "Hydrate skin"));
        productList_OilySunny.add(new ProductItem(R.drawable.sunscreen_oil,"Juice Beauty - SPF 30 Tinted Mineral Moisturizer - BB Cream", "As directed", "Apply enough to cover face and neck as directed for SPF.", "Sunscreen", "Protect your skin"));
    }


    public static ArrayList<ProductItem> productList_OilyRainy = new ArrayList<>();

    // Static block to initialize the productList
    static {
        productList_OilyRainy.add(new ProductItem(R.drawable.blue_heart,"Herbivore - PINK CLOUD Rosewater + Tremella Creamy Jelly Cleanser", "Pea-size pump", "Massage a pea-sized amount onto damp skin, then rinse.", "Cleanser", "Get rid of product"));
        productList_OilyRainy.add(new ProductItem(R.drawable.blue_heart,"Juice Beauty - Stem Cellular Anti-Wrinkle Moisturizer", "Small amount", "Apply a small amount evenly to your face and neck.", "Moisturizer", "Hydrate skin"));
        productList_OilyRainy.add(new ProductItem(R.drawable.blue_heart,"innisfree - Youth Enhancing Serum", "2-3 drops", "Pat 2-3 drops until absorbed.", "Serum", "For youthful skin"));
        productList_OilyRainy.add(new ProductItem(R.drawable.blue_heart,"innisfree - Rebalancing Toner with Blueberry", "Few drops", "Pat a few drops onto your face and neck.", "Toner", "Hydrate skin"));
        productList_OilyRainy.add(new ProductItem(R.drawable.blue_heart,"Juice Beauty - SPF 30 Tinted Mineral Moisturizer - BB Cream", "As directed", "Apply enough to cover face and neck as directed for SPF.", "Sunscreen", "Protect your skin"));
    }
}

