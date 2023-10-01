package com.example.geocare.Product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.geocare.R;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Item> itemList;

    public MyAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.itemName.setText(item.getName());
        holder.itemImage.setImageResource(item.getImageResourceId());

        holder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setFavorite(!item.isFavorite());
                updateFavoriteIcon(holder.favoriteIcon, item.isFavorite());
            }
        });

        updateFavoriteIcon(holder.favoriteIcon, item.isFavorite());
    }

    private void updateFavoriteIcon(ImageView favoriteIcon, boolean isFavorite) {
        if (isFavorite) {
            favoriteIcon.setImageResource(R.drawable.red_heart);
        } else {
            favoriteIcon.setImageResource(R.drawable.blue_heart);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView favoriteIcon;
        public ImageView itemImage;
        public TextView itemName;

        public ViewHolder(View itemView) {
            super(itemView);
            favoriteIcon = itemView.findViewById(R.id.Product_favourite);
            itemImage = itemView.findViewById(R.id.Product_imageView);
            itemName = itemView.findViewById(R.id.Product_name);
        }
    }
}
