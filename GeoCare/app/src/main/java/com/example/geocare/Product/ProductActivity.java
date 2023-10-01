package com.example.geocare.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Typeface;
import android.os.Bundle;

import com.example.geocare.R;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Item> itemList; // Replace 'Item' with your data model

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_acitivity);

        recyclerView = findViewById(R.id.Product_recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        // Initialize your item list (populate it with data)
        itemList = new ArrayList<>();
        itemList.add(new Item("MILKY WAY 10% AHA + Oat Soothing Exfoliating Serum", false, R.drawable.exfoliating));
        itemList.add(new Item("CLOUD MILK Coconut + Maca Firming Body Cream", false, R.drawable.bodycream));
        itemList.add(new Item("PINK CLOUD Soft Moisture Cream", false, R.drawable.moisture));
        itemList.add(new Item("PINK CLOUD Rosewater + Tremella Creamy Jelly Cleanser", false, R.drawable.cleanser));
        itemList.add(new Item("NOVA 15% Vitamin C + Turmeric Brightening Serum", false, R.drawable.brightening));
        itemList.add(new Item("MOON DEW 1% Bakuchiol + Peptides Retinol Alternative Firming Eye Cream", false, R.drawable.eyecream));

        adapter = new MyAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

}