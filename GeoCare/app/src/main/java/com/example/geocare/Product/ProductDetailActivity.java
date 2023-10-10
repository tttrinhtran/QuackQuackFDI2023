package com.example.geocare.Product;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Model.User;
import com.example.geocare.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView image;
    TextView product_type, product_name, product_brandname, product_ingrdients;
    User user;
    ImageView add_button, buy_button, back_button, favourite_button;
    SharedPreferenceManager sharedPreferenceManager;
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
        add_button.setClickable(true);


        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.ProductDetail_homeActivitySheet));

        Item item = (Item) getIntent().getSerializableExtra("item_data");
        user= (User) getIntent().getSerializableExtra("user_data");

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
        updateFavoriteIconForDetail(favourite_button,item.isFavorite());


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
                finish();

            }
        });

        favourite_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean temp = item.isFavorite();
                if(item.isFavorite()==false)
                {
                    user.addToUserFavorite(item.getNameDetail());
                }
                else
                {

                    user.removeUserFavorite(item.getNameDetail());

                }
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
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.addToUserShelf(item.getNameDetail());
                saveUsertodb();
                add_button.setClickable(false);
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

    void getUser()
    {
        sharedPreferenceManager=new SharedPreferenceManager(User.class,this);
        user= (User) sharedPreferenceManager.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);
    }
    void saveUser()
    {
        sharedPreferenceManager=new SharedPreferenceManager<>(User.class,this);
        sharedPreferenceManager.storeSerializableObjectToSharedPreference(user,KEY_COLLECTION_USERS);
    }
    void saveUsertodb()
    {
        FirebaseDatabaseController firebaseDatabaseController=new FirebaseDatabaseController<>(Item.class);
        firebaseDatabaseController.updateDocumentField(KEY_COLLECTION_USERS,user.getUserEmail(),"UserSelf",user.getUserSelf());
    }
}