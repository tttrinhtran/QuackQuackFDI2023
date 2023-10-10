package com.example.geocare.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geocare.Product.Item;
import com.example.geocare.R;
import com.example.geocare.Register.surveyAdapter;

import java.sql.Array;
import java.util.ArrayList;

public class WhislistApdater extends RecyclerView.Adapter<WhislistApdater.MyViewHolder> {

    ArrayList<Item> whislistItem;
    Context context;

    public WhislistApdater(ArrayList<Item> whislistItem) {
        this.whislistItem = whislistItem;
    }

    @NonNull
    @Override
    public WhislistApdater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.whislist_item, parent,false);
        return new WhislistApdater.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WhislistApdater.MyViewHolder holder, int position) {
        holder.img.setImageResource(whislistItem.get(position).getImageDetailResourceIdByInt(context));
        holder.text.setText(whislistItem.get(position).getNameDetail());

    }

    @Override
    public int getItemCount() {
        return whislistItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView text;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.whistlist_item_img);
            text=itemView.findViewById(R.id.whistlist_item_title);

        }
    }
}
