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
import org.apache.commons.text.similarity.LevenshteinDistance;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ScanResultActivity extends AppCompatActivity {
    private ImageView product_image, product_rating;
    private TextView product_name, product_ingrdients, product_irritants,
            product_brandname, product_advise, product_type;
    private View supportView;
    private View bottomSheet;
    Button add_button;
    String receivedString;

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
        supportView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScanResultActivity.this, ScanActivity.class);
                startActivity(i);
            }
        });
    }





    private static double calculateTokenSimilarity(String s1, String s2) {
        // Tokenize strings into words
        String[] tokens1 = s1.split("\\s+");
        String[] tokens2 = s2.split("\\s+");

        // Create sets of tokens for each string
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        for (String token : tokens1) {
            set1.add(token.toLowerCase()); // Normalize to lowercase
        }
        for (String token : tokens2) {
            set2.add(token.toLowerCase()); // Normalize to lowercase
        }

        // Calculate Jaccard Similarity of token sets
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        double jaccardSimilarity = (double) intersection.size() / union.size();

        return jaccardSimilarity;
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
            matchString = matchString.toLowerCase(); // Normalize match string

            // Calculate modified Jaccard Similarity
            double similarity = calculateModifiedJaccardSimilarity(inputText, matchString);

            // Update best match if similarity is higher
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                bestMatch = matchString;
            }
        }

        // Define a threshold for similarity (adjust as needed)
        double threshold = 0.08;
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

//        for (String product : productList) {
//            if (receivedString.toLowerCase().contains(product.toLowerCase())){
//                //product_name.setText("Tim thay");
//                String[] wordsArray = product.split(" ");
//                int count = 0;
//
//                for (String word : wordsArray){
//                    if (receivedString.toLowerCase().contains(word.toLowerCase()))
//                        count++;
//                }
//
//                if (count >= wordsArray.length/2){
//                      productData.retrieveObjectsFirestoreByID(KEY_COLLECTION_PRODUCT,product);
//
////                    product_type.setText(product.getProductType());
////                    product_name.setText(product.getProductName());
////                    product_brandname.setText(product.getBrandName());
////                    product_ingrdients.setText(product.getIngredients());
////                    irritantCheck(product.getIngredients());
////                    found = true;
////                    break;
//                }
//            }
//    };

        String ListIrritant = null;
//        if (!found){
//            for (String irritant: IrritantList.irritant){
//                if (receivedString.toLowerCase().contains(irritant.toLowerCase())){
//                    if (ListIrritant == null)
//                        ListIrritant = irritant;
//                    else
//                        ListIrritant += ", " + irritant;
//                }
//            }
//
//            if (ListIrritant == null) {
//                ListIrritant = "None";
//            }
//
//            Intent intent = new Intent(ScanResultActivity.this, NotFoundActivity.class);
//            intent.putExtra("Irritant", ListIrritant);
//            startActivity(intent);
//
//            finish();
//        }
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
