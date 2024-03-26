package com.example.geocare.Scan;

import com.example.geocare.Product.Item;

import java.util.ArrayList;

public class ProductDatabase {
    public static ArrayList<Item> products = new ArrayList<>();

    static {
        products.add(new Item("Rose Cleansing Oil", "Rose Cleansing Oil", false, "rosecleansingoil.png", "Cleanser", "Cocoon", "Pure rose oil, vitamin E", "rosecleansingoil.png", "null"));
        products.add(new Item("Polemo Hair Tonic", "Polemo Hair Tonic Nước dưỡng tóc tinh dầu bưởi", false, "pomelo.png", "Hair", "Cocoon", "Vitamin B5, Xylishine, Baicapil, Bisabolo", "polemo.png", "null"));
        products.add(new Item("Winter Melon Micellar Water","Winter Melon Micellar Water-Nước tẩy trang bí đao",false,"wintermelon.png","Cleanser","Cocoon","Winter melon exact, Centella extract, tea tree oil","wintermelon.png","null"));
        products.add(new Item("Dak Lak coffee body polish","Dak Lak coffee body polish- Cà Phê Dak Lak làm sạch da chết cơ thể",false,"daklak.png","Scrub","Cocoon","Pure coffee bean","daklak.png","null"));
    }
}
