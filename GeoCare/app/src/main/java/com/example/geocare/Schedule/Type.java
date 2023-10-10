package com.example.geocare.Schedule;

import com.example.geocare.R;

import java.util.ArrayList;

public class Type {
    // Declare productList as a static ArrayList
    public static ArrayList<ProductItem> productList_OilySunnyMorning = new ArrayList<>();

    // Static block to initialize the productList
    static {
        productList_OilySunnyMorning.add(new ProductItem(R.drawable.cleanser_oil, "Herbivore - PINK CLOUD Rosewater + Tremella Creamy Jelly Cleanser", "Pea-size pump", "Massage a pea-sized amount onto damp skin, then rinse.", "Cleanser", "Get rid of dirt"));
        productList_OilySunnyMorning.add(new ProductItem(R.drawable.moisturizer_oil, "Juice Beauty - Stem Cellular Anti-Wrinkle Moisturizer", "Small amount", "Apply a small amount evenly to your face and neck.", "Moisturizer", "Hydrate skin"));
        productList_OilySunnyMorning.add(new ProductItem(R.drawable.serum_oil, "innisfree - Youth Enhancing Serum", "2-3 drops", "Pat 2-3 drops until absorbed.", "Serum", "For youthful skin"));
        productList_OilySunnyMorning.add(new ProductItem(R.drawable.toner_oil, "innisfree - Rebalancing Toner with Blueberry", "Few drops", "Pat a few drops onto your face and neck.", "Toner", "Hydrate skin"));
        productList_OilySunnyMorning.add(new ProductItem(R.drawable.sunscreen_oil, "Juice Beauty - SPF 30 Tinted Mineral Moisturizer - BB Cream", "As directed", "Apply enough to cover face and neck as directed for SPF.", "Sunscreen", "Protect your skin"));
    }

    public static ArrayList<ProductItem> productList_OilyRainyMorning = new ArrayList<>();

    // Static block to initialize the productList
    static {
        productList_OilyRainyMorning.add(new ProductItem(R.drawable.cleanser_oil, "Herbivore - PINK CLOUD Rosewater + Tremella Creamy Jelly Cleanser", "Pea-size pump", "Massage a pea-sized amount onto damp skin, then rinse.", "Cleanser", "Get rid of dirt"));
        productList_OilyRainyMorning.add(new ProductItem(R.drawable.toner_oil, "innisfree - Rebalancing Toner with Blueberry", "A few drops", "Pat a few drops onto your face and neck.", "Toner", "Hydrate skin"));
        productList_OilyRainyMorning.add(new ProductItem(R.drawable.serum_oil, "innisfree - Youth Enhancing Serum", "2-3 drops", "Pat 2-3 drops until absorbed.", "Serum", "For youthful skin"));
        productList_OilyRainyMorning.add(new ProductItem(R.drawable.moisturizer_oil, "Juice Beauty - Stem Cellular Anti-Wrinkle Moisturizer", "A small amount", "Apply a small amount evenly to your face and neck.", "Moisturizer", "Hydrate skin"));
    }

    public static ArrayList<ProductItem> productList_OilyRainyNight = new ArrayList<>();
    static {
        productList_OilyRainyNight.add(new ProductItem(R.drawable.micellar_oil, "Cocoon - Winter Melon Micellar Cleansing Water 500ml","One soaked cotton pad","Wipe makeup and impurities with a soaked cotton pad.","Micellar","Makeup removal and cleansing"));
        productList_OilyRainyNight.add(new ProductItem(R.drawable.cleanser_oil, "Herbivore - PINK CLOUD Rosewater + Tremella Creamy Jelly Cleanser", "Pea-size pump", "Massage a pea-sized amount onto damp skin, then rinse.", "Cleanser", "Get rid of dirt"));
        productList_OilyRainyNight.add(new ProductItem(R.drawable.toner_oil, "innisfree - Rebalancing Toner with Blueberry","A few drops", "Pat a few drops onto your face and neck.","Toner", "Hydrate skin" ));
        productList_OilyRainyNight.add(new ProductItem(R.drawable.serum_oil_1, "Juice Beauty - Antioxidant Serum", "2-3 drops", "Pat 2-3 drops until absorbed.", "Serum", "For youthful skin"));
        productList_OilyRainyNight.add(new ProductItem(R.drawable.mask_oil, "innisfree - Super Volcanic Pore Clay Mask","A teaspoon to a tablespoon","Apply a teaspoon to a tablespoon of clay mask evenly to target areas.","Mask","Hydrate and rejuvenate skin"));
    }

    public static ArrayList<ProductItem> productList_OilyNight = new ArrayList<>();
    static {
        productList_OilyNight.add(new ProductItem(R.drawable.micellar_oil, "Cocoon - Winter Melon Micellar Cleansing Water 500ml","One soaked cotton pad","Wipe makeup and impurities with a soaked cotton pad.","Micellar","Makeup removal and cleansing"));
        productList_OilyNight.add(new ProductItem(R.drawable.cleanser_oil, "Herbivore - PINK CLOUD Rosewater + Tremella Creamy Jelly Cleanser", "Pea-size pump", "Massage a pea-sized amount onto damp skin, then rinse.", "Cleanser", "Get rid of dirt"));
        productList_OilyNight.add(new ProductItem(R.drawable.blue_heart, "innisfree - Rebalancing Toner with Blueberry","A few drops", "Pat a few drops onto your face and neck.","Toner", "Hydrate skin" ));
        productList_OilyNight.add(new ProductItem(R.drawable.serum_oil, "innisfree - Youth Enhancing Serum", "2-3 drops", "Pat 2-3 drops until absorbed.", "Serum", "For youthful skin"));
        productList_OilyNight.add(new ProductItem(R.drawable.moisturizer_oil, "Juice Beauty - Stem Cellular Anti-Wrinkle Moisturizer", "A small amount", "Apply a small amount evenly to your face and neck.", "Moisturizer", "Hydrate skin"));
    }

    public static ArrayList<ProductItem> productList_DrySunnyMorning = new ArrayList<>();
    static {
        productList_DrySunnyMorning.add(new ProductItem(R.drawable.cleanser_dry,"innisfree - Pore Clearing Facial Foam","A pea-sized amount","Massage a pea-sized amount onto damp skin, then rinse.", "Cleanser", "Get rid of dirt"));
        productList_DrySunnyMorning.add(new ProductItem(R.drawable.toner_dry,"Cocoon - Hung Yen Turmeric Toner","A few drops","Pat a few drops onto your face and neck.", "Toner", "Hydrate skin"));
        productList_DrySunnyMorning.add(new ProductItem(R.drawable.serum_oil, "innisfree - Youth Enhancing Serum", "2-3 drops", "Pat 2-3 drops until absorbed.", "Serum", "For youthful skin"));
        productList_DrySunnyMorning.add(new ProductItem(R.drawable.moisturizer_dry,"Herbivore - Pink Cloud Soft Moisture Cream","A small amount", "Apply a small amount evenly to your face and neck.","Moisturizer","Hydrate skin"));
        productList_DrySunnyMorning.add(new ProductItem(R.drawable.sunscreen_dry,"Cocoon - Winter Melon Sunscreen","Enough to cover face and neck","Apply enough to cover your face and neck as directed for SPF protection.","Sunscreen","Protect your skin"));
    }


    public static ArrayList<ProductItem> productList_DryRainyMorning = new ArrayList<>();
    static {
        productList_DrySunnyMorning.add(new ProductItem(R.drawable.cleanser_dry,"innisfree - Pore Clearing Facial Foam","A pea-sized amount","Massage a pea-sized amount onto damp skin, then rinse.", "Cleanser", "Get rid of dirt"));
        productList_DrySunnyMorning.add(new ProductItem(R.drawable.toner_dry,"Cocoon - Hung Yen Turmeric Toner","A few drops","Pat a few drops onto your face and neck.", "Toner", "Hydrate skin"));
        productList_DrySunnyMorning.add(new ProductItem(R.drawable.serum_oil, "innisfree - Youth Enhancing Serum", "2-3 drops", "Pat 2-3 drops until absorbed.", "Serum", "For youthful skin"));
        productList_DryRainyMorning.add(new ProductItem(R.drawable.moisturizer_dry,"Herbivore - Pink Cloud Soft Moisture Cream","A small amount", "Apply a small amount evenly to your face and neck.","Moisturizer","Hydrate skin"));
    }


}

