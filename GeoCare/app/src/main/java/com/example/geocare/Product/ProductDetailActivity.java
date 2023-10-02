package com.example.geocare.Product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geocare.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView image;
    TextView product_type, product_name, product_brandname, product_ingrdients;

    ImageView add_button, buy_button, back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        image = findViewById(R.id.ProductDetail_product_image);
        product_type = findViewById(R.id.ProductDetail_product_type);
        product_name = findViewById(R.id.ProductDetail_product_name);
        product_brandname = findViewById(R.id.ProductDetail_product_brandname);
        product_ingrdients = findViewById(R.id.ProductDetail_product_ingrdients);
        add_button = findViewById(R.id.ProductDetail_add_button);
        buy_button = findViewById(R.id.ProductDetail_buy_button);
        back_button = findViewById(R.id.ProductDetail_back_button);

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.ProductDetail_homeActivitySheet));

        bottomSheetBehavior.setPeekHeight(650);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        Item item = (Item) getIntent().getSerializableExtra("item_data");

        image.setImageResource(item.getImageDetail());
        product_type.setText(item.getType());
        product_name.setText(item.getNameDetail());
        product_brandname.setText(item.getBrandname());
        product_ingrdients.setText(item.getIngredients());


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the current activity and return to the previous one
            }
        });
    }
}