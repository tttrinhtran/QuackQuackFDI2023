package com.example.geocare.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geocare.Product.Item;
import com.example.geocare.R;

import java.util.ArrayList;

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.MyViewHolder> {
    ArrayList<Item> shelfItems;
    Context context;

    public ShelfAdapter(ArrayList<Item> shelfItems, Context context) {
        this.shelfItems = shelfItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ShelfAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shelf_item, parent,false);
        return new ShelfAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShelfAdapter.MyViewHolder holder, int position) {
        holder.img.setImageResource(shelfItems.get(position).getImageResourceIdByInt(context));
    }

    @Override
    public int getItemCount() {
        return shelfItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.shelf_item_img);
        }
    }
}
