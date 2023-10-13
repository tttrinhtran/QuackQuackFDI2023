package com.example.geocare.Scan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geocare.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ScanResultActivity extends AppCompatActivity {
    private ImageView product_image, product_rating;
    private TextView product_name, product_ingrdients, product_irritants,
            product_brandname, product_advise, product_type;
    private View supportView;
    private View bottomSheet;
    Button add_button;
    String receivedString;
    int lab;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        product_image = findViewById(R.id.ScanResultScreen_product_image);
        bottomSheet = findViewById(R.id.homeactivitysheet);
        product_name = findViewById(R.id.ScanResultScreen_product_name);
        product_ingrdients = findViewById(R.id.ScanResultScreen_product_ingrdients);
        product_irritants = findViewById(R.id.ScanResultScreen_product_irritant);
        product_brandname = findViewById(R.id.ScanResultScreen_product_brandname);
        product_advise = findViewById(R.id.ScanResultScreen_product_advice);
        product_rating = findViewById(R.id.ScanResultScreen_product_rating);
        product_type = findViewById(R.id.ScanResultScreen_product_type);
        add_button = findViewById(R.id.ScanResultScreen_add_button);
        supportView = (View) findViewById(R.id.SupportViewScan);

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.homeactivitysheet));
        //initialize height
        bottomSheetBehavior.setPeekHeight(550);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        receivedString = getIntent().getStringExtra("data");

        onListenerClick();


        process(receivedString);

    }

    private void onListenerClick() {
        supportView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScanResultActivity.this, ScanActivity.class);
                startActivity(i);
            }
        });
    }

    private void process(String receivedString) {
        boolean found = false;
        receivedString = receivedString.toLowerCase();
        for (ProductInformation product : ProductDatabase.products) {
            if (receivedString.toLowerCase().contains(product.getBrandName().toLowerCase())){
                //product_name.setText("Tim thay");
                String[] wordsArray = product.getProductName().split(" ");
                int count = 0;

                for (String word : wordsArray){
                    if (receivedString.toLowerCase().contains(word.toLowerCase()))
                        count++;
                }
                //product_type.setText(""+count);

                if (count >= wordsArray.length/2){
                    product_type.setText(product.getProductType());
                    product_name.setText(product.getProductName());
                    product_brandname.setText(product.getBrandName());
                    product_ingrdients.setText(product.getIngredients());
                    irritantCheck(product.getIngredients());
                    found = true;
                    break;
                }
            }
    };

        String ListIrritant = null;
        if (!found){
            for (String irritant: IrritantList.irritant){
                if (receivedString.toLowerCase().contains(irritant.toLowerCase())){
                    if (ListIrritant == null)
                        ListIrritant = irritant;
                    else
                        ListIrritant += ", " + irritant;
                }
            }

            if (ListIrritant == null) {
                ListIrritant = "None";
            }

            Intent intent = new Intent(ScanResultActivity.this, NotFoundActivity.class);
            intent.putExtra("Irritant", ListIrritant);
            startActivity(intent);

            finish();
        }
    }


    private void irritantCheck(String ingredients) {
        String ListIrritant = null;
        for (String irritant : IrritantList.irritant){
            if (ingredients.toLowerCase().contains(irritant.toLowerCase())){
                if (ListIrritant == null)
                    ListIrritant = irritant;
                else
                    ListIrritant += ", " + irritant;
            }
        }

        if (ListIrritant == null) {
            product_irritants.setText("None");
            product_advise.setText("Good for your skin type");
        }
        else {
            product_irritants.setText(ListIrritant);
            product_advise.setText("Be careful!");
        }
    }
}
