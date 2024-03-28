package com.example.geocare.Scan;

import static com.example.geocare.Constants.KEY_COLLECTION_PRODUCT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Product.Item;
import com.example.geocare.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ScanResultActivity extends AppCompatActivity {
   ImageView product_image, product_rating;
    private TextView product_name, product_ingrdients, product_irritants,
            product_brandname, product_advise, product_type;
    private View supportView;
    private View bottomSheet;
    Button add_button;
    String receivedString;
    private ImageView backButton;

    FirebaseDatabaseController<Item> productData;
    ArrayList<String> productList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        product_image = findViewById(R.id.ScanResultScreen_product_image);
        //bottomSheet = findViewById(R.id.homeactivitysheet);
        product_name = findViewById(R.id.ScanResultScreen_product_name);
        product_ingrdients = findViewById(R.id.ScanResultScreen_product_ingrdients);
        product_irritants = findViewById(R.id.ScanResultScreen_product_irritant);
        product_brandname = findViewById(R.id.ScanResultScreen_product_brandname);
        product_advise = findViewById(R.id.ScanResultScreen_product_advice);
        product_rating = findViewById(R.id.ScanResultScreen_product_rating);
        product_type = findViewById(R.id.ScanResultScreen_product_type);
        add_button = findViewById(R.id.ScanResultScreen_add_button);
        supportView = (View) findViewById(R.id.SupportViewScan);
        backButton = findViewById(R.id.ScanResultScreen_back_button);


        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.homeactivitysheet));
        //initialize height
        bottomSheetBehavior.setPeekHeight(550);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        receivedString = getIntent().getStringExtra("data");

        onListenerClick();

        getData();
        process(receivedString);

    }
    private void getData()
    {
        productData=new FirebaseDatabaseController<>(Item.class);
        productList=productData.retrieveAllDocumentsIDOfaCollection(KEY_COLLECTION_PRODUCT);


    }

    private void onListenerClick() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScanResultActivity.this, ScanActivity.class);
                startActivity(i);
            }
        });
    }






    private static double calculateModifiedJaccardSimilarity(String s1, String s2) {
        // Tokenize strings into words
        String[] tokens1 = s1.split("\\s+");
        String[] tokens2 = s2.split("\\s+");

        // Calculate Jaccard Similarity with length consideration
        double intersectionCount = 0;
        double unionCount = Math.max(tokens1.length, tokens2.length);

        for (String token : tokens1) {
            if (s2.contains(token)) {
                intersectionCount++;
            }
        }

        return intersectionCount / unionCount;
    }

    boolean found = false;
    private String checkText(String inputText, ArrayList<String> stringsToMatch) {
        inputText = inputText.replace("\n", " ").toLowerCase(); // Normalize input text
        double maxSimilarity = 0.0;
        String bestMatch = inputText; // Default to input text if no match found

        for (String matchString : stringsToMatch) {
           String tmpmatchString = matchString.toLowerCase(); // Normalize match string

            // Calculate modified Jaccard Similarity
            double similarity = calculateModifiedJaccardSimilarity(inputText, tmpmatchString);

            // Update best match if similarity is higher
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                bestMatch = matchString;
            }
        }

        // Define a threshold for similarity (adjust as needed)
        double threshold = 0.09;
        if (maxSimilarity >= threshold) {
            found=true;
            return bestMatch;
        }
        return inputText;
    }

    private void process(String receivedString) {
        // check lại
        receivedString = receivedString.toLowerCase();
        String product=checkText(receivedString,productList);
        if(found)
        {
            Item scanProduct=productData.retrieveObjectsFirestoreByID(KEY_COLLECTION_PRODUCT,product);

            product_type.setText(scanProduct.getType());
            product_image.setImageResource(scanProduct.getImageDetailResourceIdByInt(this));
            product_name.setText(scanProduct.getNameDetail());
            product_brandname.setText(scanProduct.getBrandname());
            product_ingrdients.setText(scanProduct.getIngredients());
            irritantCheck(scanProduct.getIngredients());

        }


        String ListIrritant = null;
        if (!found || (receivedString.contains("obagi"))){
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
