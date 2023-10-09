package com.example.geocare.Home;

import com.example.geocare.R;

import java.util.ArrayList;
import java.util.List;

public class Tips {
    private String title;
    private String description;
    private String detail;
    private int ImageID;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDetail() {
        return detail;
    }

    public int getImageID() {
        return ImageID;
    }

    public Tips(String title, String description, String detail, int imageID) {
        this.title = title;
        this.description = description;
        this.detail = detail;
        ImageID = imageID;
    }

    public static List<Tips> getTipsList(){
        List<Tips> tipsList = new ArrayList<>();
        tipsList.add(new Tips("Drink More Water & Fruit Juices", "Staying hydrated by drinking water", "AAAAAAA", R.drawable.tip1));
        tipsList.add(new Tips("Drink More Water & Fruit Juices", "Staying hydrated by drinking water", "AAAAAAA", R.drawable.tip2));
        tipsList.add(new Tips("Drink More Water & Fruit Juices", "Staying hydrated by drinking water", "AAAAAAA", R.drawable.tip3));
        tipsList.add(new Tips("Drink More Water & Fruit Juices", "Staying hydrated by drinking water", "AAAAAAA", R.drawable.tip4));
        return  tipsList;
    }
}
