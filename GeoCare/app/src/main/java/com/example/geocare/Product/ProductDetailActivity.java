package com.example.geocare.Product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocare.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView image;
    TextView product_type, product_name, product_brandname, product_ingrdients;

    ImageView add_button, buy_button, back_button, favourite_button;
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
        favourite_button = findViewById(R.id.ProductDetail_favourite_button);

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.ProductDetail_homeActivitySheet));

        Item item = (Item) getIntent().getSerializableExtra("item_data");

        String temp = item.getNameDetail();
        if (temp.length() >= 50){
            bottomSheetBehavior.setPeekHeight(880);
        }
        else if (temp.length() >= 35){
            bottomSheetBehavior.setPeekHeight(780);
        }
        else
            bottomSheetBehavior.setPeekHeight(670);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        image.setImageResource(item.getImageDetailResourceIdByInt(getApplicationContext()));
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

        favourite_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean temp = item.isFavorite();
                updateFavoriteIconForDetail(favourite_button,!temp);
                item.setFavourite(!temp);
            }
        });

        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductLink(item);
            }
        });
    }

    private void updateFavoriteIconForDetail(ImageView favoriteIcon, boolean isFavorite) {
        if (isFavorite) {
            favoriteIcon.setImageResource(R.drawable.red_heart);
        } else {
            favoriteIcon.setImageResource(R.drawable.blue_heart);
        }
    }

    public void openProductLink(Item item) {
        // Define the URL of the product you want to link to
        String productUrl = item.getUri();

        // Create an Intent to open a web browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(productUrl));
        startActivity(browserIntent);
    }
}