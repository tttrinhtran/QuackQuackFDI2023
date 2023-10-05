package com.example.geocare.Product;

import static com.example.geocare.Constants.KEY_COLLECTION_PRODUCT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.EditText;

import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.R;

import java.util.ArrayList;
import java.util.List;import androidx.appcompat.widget.SearchView;
import android.text.TextUtils;

public class ProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Item> itemList; // Replace 'Item' with your data model
    SearchView searchView;

    FirebaseDatabaseController itemFirebaseDatabaseController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        itemFirebaseDatabaseController=new FirebaseDatabaseController<>(Item.class);

        searchView = (SearchView) findViewById(R.id.Product_search);

        //search UI set up
        EditText searchEditText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        Typeface customTypeface = ResourcesCompat.getFont(this, R.font.lato_semibold);
        searchEditText.setTypeface(customTypeface);
        searchEditText.setTextColor(getResources().getColor(R.color.blue_deep));
        searchEditText.setHintTextColor(getResources().getColor(R.color.blue_deep));
        getData();

        recyclerView = findViewById(R.id.Product_recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        adapter = new MyAdapter(ProductActivity.this, itemList);
        recyclerView.setAdapter(adapter);

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

    private void getData()
    {
        ArrayList<String>ItemName;
        ItemName=itemFirebaseDatabaseController.retrieveAllDocumentsIDOfaCollection(KEY_COLLECTION_PRODUCT);
        for(int i=0; i<ItemName.size(); i++)
        {
            if(itemList==null)
            {
                itemList=new ArrayList<>();
            }
            itemList.add((Item) itemFirebaseDatabaseController.retrieveObjectsFirestoreByID(KEY_COLLECTION_PRODUCT,ItemName.get(i)));
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
}