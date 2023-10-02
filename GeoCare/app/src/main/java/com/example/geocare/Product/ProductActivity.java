package com.example.geocare.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.Product_recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        // Initialize your item list (populate it with data)
        // this.type = type;
        //        this.brandname = brandname;
        //        this.ingredients = ingredients;
        //        this.irritants = irritants;
        //        this.imageDetail = imageDetail;

        itemList = new ArrayList<>();
        itemList.add(new Item("MILKY WAY 10% AHA + Oat Soothing Exfoliating Serum","10% AHA Oat Soothing Exfoliating Serum", false, R.drawable.exfoliating,"Exfoliant","Herbivore","Aqua/Water/Eau, Propanediol, Simmondsia Chinensis (Jojoba) Seed Oil, Sodium Hydroxide, Lactic Acid, Glycolic Acid, Gluconolactone, Cetearyl Olivate, Sorbitan Olivate, Octyldodecanol,Glycerin, Malic Acid, Tartaric Acid, Citric Acid, Squalane, Avena Sativa (Oat) Kernel Extract, Ceramide NP, Ceramide AP, Microcitrus Australasica Fruit Extract, Tremella Fuciformis, Sporocarp Extract, Hyaluronic Acid, 1,2-Hexanediol, Xanthan Gum, Diheptyl Succinate, Capryloyl Glycerin/Sebacic Acid Copolymer,Oryza Sativa (Rice) Bran Extract, Sclerotium Gum, Lecithin, Pullulan, Caprylhydroxamic Acid, Trisodium Ethylenediamine Disuccinate, Helianthus Annuus (Sunflower) Seed Oil, Triethyl Citrate, Rosmarinus Officinalis (Rosemary) Leaf Extract, Tocopherol, Silica, 2,3-Butanediol, Glyceryl Stearate, Prunus Amygdalus Dulcis (Sweet Almond) Oil, Sodium Lactate, Hydrogenated Lecithin, Gamma Octalactone, Vanillin, Glyceryl Caprylate, lsoamyl Acetate, Benzaldehyde, Anisaldehyde, Maltol, Phytosphingosine, Ethylhexylglycerin, Dimethylhydroxy Furanone",R.drawable.exfoliating_detail));
        itemList.add(new Item("CLOUD MILK Coconut + Maca Firming Body Cream", "Coconut Maca Firming Body Cream", false, R.drawable.bodycream, "Body Cream", "Herbivore","Aqua/water/eau, squalane, cetyl alcohol, glyceryl behenate, caprylic/capric triglyceride, butyrospermum parkii (shea) butter, cetearyl olivate, propanediol, sorbitan olivate, 1,2-hexanediol, glycerin, glyceryl stearate citrate, cocos nucifera (coconut) oil, cocos nucifera (coconut) fruit juice, cocos nucifera (coconut) water, coconut alkanes",R.drawable.bodycream_detail));
        itemList.add(new Item("PINK CLOUD Soft Moisture Cream","PINK CLOUD Soft Moisture Cream", false, R.drawable.moisture, "Moisture", "Herbivore", "Aqua/Water/Eau, Rosa Damascena Flower Water, Cetearyl Alcohol, Caprylic/Capric Triglyceride, Coconut Alkanes, Glyceryl Caprylate, Glycerin, Squalane, Glyceryl Stearate, Isoamyl Laurate, Microcrystalline Cellulose, Butyrospermum Parkii (Shea) Butter, Tremella Fuciformis Sporocarp Extract, Sodium Hyaluronate, Cocos Nucifera (Coconut) Fruit Juice, Camellia Sinensis Leaf Extract, Aloe Barbadensis Leaf Juice, Eclipta Prostrata Extract, Melia Azadirachta Leaf Extract, Moringa Oleifera Seed Oil, Coco-Caprylate/Caprate, Sodium Stearoyl Glutamate, Cetearyl Glucoside, Cellulose Gum, Tapioca Starch, Glyceryl Stearate Citrate, Citric Acid, Tetrasodium Glutamate Diacetate, Caprylhydroxamic Acid, Sodium Hydroxide, Citronellol, Geraniol", R.drawable.moisture_detail));
        itemList.add(new Item("PINK CLOUD Rosewater + Tremella Creamy Jelly Cleanser", "Rosewater Tremella Creamy Jelly Cleanser", false, R.drawable.cleanser, "Cleanser", "Herbivore", "Aqua/Water/Eau, Rosa Damascena Flower Water, Decyl Glucoside, Glycerin, Sodium Lauroyl Lactylate, Squalane, Glyceryl Caprylate, Xanthan Gum, Glyceryl Stearate, Tremella Fuciformis Sporocarp Extract, Caprylhydroxamic Acid, Camellia Sinensis Leaf Extract, Aloe Barbadensis Leaf Juice, Citric Acid, Eclipta Prostrata Extract, Sodium Hyaluronate, Melia Azadirachta Leaf Extract, Moringa Oleifera Seed Oil, Cocos Nucifera (Coconut) Fruit Juice, Tapioca Starch", R.drawable.cleanser_detail));
        itemList.add(new Item("NOVA 15% Vitamin C + Turmeric Brightening Serum", "15% Vitamin C Turmeric Brightening Serum", false, R.drawable.brightening, "Brightening Serum", "Herbivore", "Aqua/water/eau, Tetrahexyldecyl Ascorbate, Glycerin, C9-12 Alkane, Sodium Stearoyl Glutamate, Propanediol, Cetearyl Olivate, Sorbitan Olivate, Arctostaphylos Uva-Ursi Leaf Extract, Curcuma Longa (Turmeric) Root Extract, Terminalia Chebula Fruit Extract, Terminalia Ferdinandiana Fruit Extract, Hyaluronic Acid, Polyglyceryl-6 Stearate, Sclerotium Gum, Polyglyceryl-6 Polyricinoleate, Sodium Levulinate, Sodium Anisate, Melia Azadirachta Flower Extract, Coco-Caprylate/Caprate, Butylene Glycol, Melia Azadirachta Leaf Extract, Ocimum Sanctum Leaf Extract, Sodium Phytate, Averrhoa Carambola Fruit Extract, Polyglyceryl-6 Behenate, Ocimum Basilicum (Basil) Flower/Leaf Extract, Tocopheryl Acetate, Echinacea Purpurea Extract, Ananas Sativus (Pineapple) Fruit Juice, Corallina Officinalis Extract, Leuconostoc/Radish Root Ferment Filtrate, Citric Acid", R.drawable.brightening_detail));
        itemList.add(new Item("MOON DEW 1% Bakuchiol + Peptides Retinol Alternative Firming Eye Cream", "1% Bakuchiol Peptides Retinol Alternative Firming Eye Cream", false, R.drawable.eyecream, "Eye Cream", "Herbivore", "Water/aqua/eau, caprylic/capric triglyceride, octyldodecanol, glycerin, cetearyl alcohol, squalane, butyrospermum parkii (shea) butter, glyceryl stearate, vaccinium myrtillus seed oil, cetearyl olivate, melia azadirachta flower extract, glyceryl caprylate, sorbitan olivate, bakuchiol, pullulan, aloe barbadensis flower extract, chenopodium quinoa seed extract, coccinia indica fruit extract, corallina officinalis extract, melia azadirachta leaf extract, rosa damascena extract, tremella fuciformis sporocarp extract, vaccinium angustifolium (blueberry) fruit extract, vanilla planifolia fruit extract, citrus aurantium amara (bitter orange) leaf/twig extract, citrus aurantium dulcis (orange) peel extract, musa sapientum (banana) fruit extract, pyrus malus (apple) fruit extract, rubus idaeus (raspberry) fruit extract, lavandula angustifolia (lavender) flower/leaf/stem extract, gleditsia triacanthos seedextract, ocimum basilicum (basil) flower/leaf extract, curcuma longa (turmeric) root extract, ocimum sanctum leaf extract, solanum melongena (eggplant) fruit extract, decyl glucoside, lauryl glucoside, sodium stearoyl glutamate, cetyl hydroxyethylcellulose, citric acid, caprylhydroxamic acid, alcohol, hydrated silica, sodium nitrate, disodium phosphate, silica dimethyl silicate, sodium phosphate, caprylyl glycol, sodium benzoate", R.drawable.eyecream_detail));

        adapter = new MyAdapter(ProductActivity.this, itemList);
        recyclerView.setAdapter(adapter);
    }

}