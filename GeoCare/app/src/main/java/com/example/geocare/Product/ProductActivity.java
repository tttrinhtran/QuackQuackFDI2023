package com.example.geocare.Product;

import static com.example.geocare.Constants.KEY_COLLECTION_PRODUCT;
import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Home.HomeActivity;
import com.example.geocare.Model.User;
import com.example.geocare.Profile.ProfileActivity;
import com.example.geocare.R;
import com.example.geocare.Scan.ScanActivity;
import com.example.geocare.Schedule.ScheduleActivity;

import java.util.ArrayList;
import java.util.List;import androidx.appcompat.widget.SearchView;
import android.text.TextUtils;
import android.widget.ImageView;

public class ProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Item> itemList; // Replace 'Item' with your data model
    SearchView searchView;

    // for Navbar
    private ImageView homeIcon, producIcon, scanIcon, scheduleIcon, profileIcon;
    Context context;
User user;
    FirebaseDatabaseController itemFirebaseDatabaseController;
    FirebaseDatabaseController userFirebase;

    SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        itemFirebaseDatabaseController=new FirebaseDatabaseController<>(Item.class);
       userFirebase=new FirebaseDatabaseController<>(User.class);
        searchView = (SearchView) findViewById(R.id.Product_search);


        //search UI set up
        EditText searchEditText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        Typeface customTypeface = ResourcesCompat.getFont(this, R.font.lato_semibold);
        searchEditText.setTypeface(customTypeface);
        searchEditText.setTextColor(getResources().getColor(R.color.blue_deep));
        searchEditText.setHintTextColor(getResources().getColor(R.color.blue_deep));

        getUser();
        getData();
        fetch_UI(); navBar();

       setRecyclerView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveUser();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
        getData();
        setRecyclerView();
    }

    void setRecyclerView()
    {
        recyclerView = findViewById(R.id.Product_recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new MyAdapter(ProductActivity.this, itemList, user);
        recyclerView.setAdapter(adapter);
    }

    void getData()
    {
        itemList=new ArrayList<>();
        itemList.add(new Item("MILKY WAY 10% AHA + Oat Soothing Exfoliating Serum","10% AHA Oat Soothing Exfoliating Serum", false, "exfoliating","Exfoliant","Herbivore","Aqua/Water/Eau, Propanediol, Simmondsia Chinensis (Jojoba) Seed Oil, Sodium Hydroxide, Lactic Acid, Glycolic Acid, Gluconolactone, Cetearyl Olivate, Sorbitan Olivate, Octyldodecanol,Glycerin, Malic Acid, Tartaric Acid, Citric Acid, Squalane, Avena Sativa (Oat) Kernel Extract, Ceramide NP, Ceramide AP, Microcitrus Australasica Fruit Extract, Tremella Fuciformis, Sporocarp Extract, Hyaluronic Acid, 1,2-Hexanediol, Xanthan Gum, Diheptyl Succinate, Capryloyl Glycerin/Sebacic Acid Copolymer,Oryza Sativa (Rice) Bran Extract, Sclerotium Gum, Lecithin, Pullulan, Caprylhydroxamic Acid, Trisodium Ethylenediamine Disuccinate, Helianthus Annuus (Sunflower) Seed Oil, Triethyl Citrate, Rosmarinus Officinalis (Rosemary) Leaf Extract, Tocopherol, Silica, 2,3-Butanediol, Glyceryl Stearate, Prunus Amygdalus Dulcis (Sweet Almond) Oil, Sodium Lactate, Hydrogenated Lecithin, Gamma Octalactone, Vanillin, Glyceryl Caprylate, lsoamyl Acetate, Benzaldehyde, Anisaldehyde, Maltol, Phytosphingosine, Ethylhexylglycerin, Dimethylhydroxy Furanone","exfoliating_detail","https://www.herbivorebotanicals.com/collections/all/products/milky-way-10-aha-oat-soothing-exfoliating-serum"));
        itemList.add(new Item("CLOUD MILK Coconut + Maca Firming Body Cream", "Coconut Maca Firming Body Cream", false, "bodycream", "Body Cream", "Herbivore","Aqua/water/eau, squalane, cetyl alcohol, glyceryl behenate, caprylic/capric triglyceride, butyrospermum parkii (shea) butter, cetearyl olivate, propanediol, sorbitan olivate, 1,2-hexanediol, glycerin, glyceryl stearate citrate, cocos nucifera (coconut) oil, cocos nucifera (coconut) fruit juice, cocos nucifera (coconut) water, coconut alkanes","bodycream_detail","https://www.herbivorebotanicals.com/collections/all/products/cloud-milk-coconut-maca-firming-body-cream"));
        itemList.add(new Item("PINK CLOUD Soft Moisture Cream","PINK CLOUD Soft Moisture Cream", false, "moisture", "Moisture", "Herbivore", "Aqua/Water/Eau, Rosa Damascena Flower Water, Cetearyl Alcohol, Caprylic/Capric Triglyceride, Coconut Alkanes, Glyceryl Caprylate, Glycerin, Squalane, Glyceryl Stearate, Isoamyl Laurate, Microcrystalline Cellulose, Butyrospermum Parkii (Shea) Butter, Tremella Fuciformis Sporocarp Extract, Sodium Hyaluronate, Cocos Nucifera (Coconut) Fruit Juice, Camellia Sinensis Leaf Extract, Aloe Barbadensis Leaf Juice, Eclipta Prostrata Extract, Melia Azadirachta Leaf Extract, Moringa Oleifera Seed Oil, Coco-Caprylate/Caprate, Sodium Stearoyl Glutamate, Cetearyl Glucoside, Cellulose Gum, Tapioca Starch, Glyceryl Stearate Citrate, Citric Acid, Tetrasodium Glutamate Diacetate, Caprylhydroxamic Acid, Sodium Hydroxide, Citronellol, Geraniol","moisture_detail","https://www.herbivorebotanicals.com/collections/all/products/pink-cloud-soft-moisture-cream"));
        itemList.add(new Item("PINK CLOUD Rosewater + Tremella Creamy Jelly Cleanser", "Rosewater Tremella Creamy Jelly Cleanser", false, "cleanser", "Cleanser", "Herbivore", "Aqua/Water/Eau, Rosa Damascena Flower Water, Decyl Glucoside, Glycerin, Sodium Lauroyl Lactylate, Squalane, Glyceryl Caprylate, Xanthan Gum, Glyceryl Stearate, Tremella Fuciformis Sporocarp Extract, Caprylhydroxamic Acid, Camellia Sinensis Leaf Extract, Aloe Barbadensis Leaf Juice, Citric Acid, Eclipta Prostrata Extract, Sodium Hyaluronate, Melia Azadirachta Leaf Extract, Moringa Oleifera Seed Oil, Cocos Nucifera (Coconut) Fruit Juice, Tapioca Starch", "cleanser_detail","https://www.herbivorebotanicals.com/products/pink-cloud-rosewater-tremella-creamy-jelly-cleanser"));
        itemList.add(new Item("NOVA 15% Vitamin C + Turmeric Brightening Serum", "15% Vitamin C Turmeric Brightening Serum", false,"brightening", "Brightening Serum", "Herbivore", "Aqua/water/eau, Tetrahexyldecyl Ascorbate, Glycerin, C9-12 Alkane, Sodium Stearoyl Glutamate, Propanediol, Cetearyl Olivate, Sorbitan Olivate, Arctostaphylos Uva-Ursi Leaf Extract, Curcuma Longa (Turmeric) Root Extract, Terminalia Chebula Fruit Extract, Terminalia Ferdinandiana Fruit Extract, Hyaluronic Acid, Polyglyceryl-6 Stearate, Sclerotium Gum, Polyglyceryl-6 Polyricinoleate, Sodium Levulinate, Sodium Anisate, Melia Azadirachta Flower Extract, Coco-Caprylate/Caprate, Butylene Glycol, Melia Azadirachta Leaf Extract, Ocimum Sanctum Leaf Extract, Sodium Phytate, Averrhoa Carambola Fruit Extract, Polyglyceryl-6 Behenate, Ocimum Basilicum (Basil) Flower/Leaf Extract, Tocopheryl Acetate, Echinacea Purpurea Extract, Ananas Sativus (Pineapple) Fruit Juice, Corallina Officinalis Extract, Leuconostoc/Radish Root Ferment Filtrate, Citric Acid", "brightening_detail","https://www.herbivorebotanicals.com/collections/all/products/nova-15-vitamin-c-turmeric-brightening-serum"));
        itemList.add(new Item("MOON DEW 1% Bakuchiol + Peptides Retinol Alternative Firming Eye Cream", "1% Bakuchiol Peptides Retinol Alternative Firming Eye Cream", false, "eyecream", "Eye Cream", "Herbivore", "Water/aqua/eau, caprylic/capric triglyceride, octyldodecanol, glycerin, cetearyl alcohol, squalane, butyrospermum parkii (shea) butter, glyceryl stearate, vaccinium myrtillus seed oil, cetearyl olivate, melia azadirachta flower extract, glyceryl caprylate, sorbitan olivate, bakuchiol, pullulan, aloe barbadensis flower extract, chenopodium quinoa seed extract, coccinia indica fruit extract, corallina officinalis extract, melia azadirachta leaf extract, rosa damascena extract, tremella fuciformis sporocarp extract, vaccinium angustifolium (blueberry) fruit extract, vanilla planifolia fruit extract, citrus aurantium amara (bitter orange) leaf/twig extract, citrus aurantium dulcis (orange) peel extract, musa sapientum (banana) fruit extract, pyrus malus (apple) fruit extract, rubus idaeus (raspberry) fruit extract, lavandula angustifolia (lavender) flower/leaf/stem extract, gleditsia triacanthos seedextract, ocimum basilicum (basil) flower/leaf extract, curcuma longa (turmeric) root extract, ocimum sanctum leaf extract, solanum melongena (eggplant) fruit extract, decyl glucoside, lauryl glucoside, sodium stearoyl glutamate, cetyl hydroxyethylcellulose, citric acid, caprylhydroxamic acid, alcohol, hydrated silica, sodium nitrate, disodium phosphate, silica dimethyl silicate, sodium phosphate, caprylyl glycol, sodium benzoate", "eyecream_detail","https://www.herbivorebotanicals.com/products/moon-dew-1-bakuchiol-peptides-retinol-alternative-eye-cream"));
        checkFavorite();
    }
    void checkFavorite()
    {
        for(int i=0; i<itemList.size(); i++)
        {

            if(user.getUserFavorite().contains(itemList.get(i).getNameDetail()))
            {
                itemList.get(i).setFavorite(true);
            }
        }
    }



    // Method to filter data based on the search query
    private void filterData(String query) {
        List<Item> filteredItemList = new ArrayList<>();

        // Implement your filtering logic here
        for (Item item : itemList) {
            if (TextUtils.isEmpty(query) || item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredItemList.add(item);
            }
        }

        // Update the RecyclerView with filtered data
        adapter.setItems(filteredItemList);
        adapter.notifyDataSetChanged();
    }

    private void fetch_UI() {
        homeIcon = findViewById(R.id.NaviBarHomeIcon);
        producIcon = findViewById(R.id.NaviBarProductIcon);
        scanIcon = findViewById(R.id.NaviBarScanIcon);
        scheduleIcon = findViewById(R.id.NaviBarScheduleIcon);
        profileIcon = findViewById(R.id.NaviBarProfileIcon);
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
        firebaseDatabaseController.updateDocumentField(KEY_COLLECTION_USERS,user.getUserEmail(),"UserFavorite",user.getUserFavorite());
    }

    private void navBar() {
        producIcon.setImageResource(R.drawable.product_icon_filled);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUsertodb();
                Intent i = new Intent(ProductActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        scanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUsertodb();
                Intent i = new Intent(ProductActivity.this, ScanActivity.class);
                startActivity(i);
            }
        });

        scheduleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUsertodb();
                Intent i = new Intent(ProductActivity.this, ScheduleActivity.class);
                startActivity(i);
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUsertodb();
                Intent i = new Intent(ProductActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }

}