package com.example.geocare.Product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.geocare.Model.User;
import com.example.geocare.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Item> itemList;
    private Context context; // Add a context variable

    private User user;

    public MyAdapter(Context context, List<Item> itemList, User user) {
        this.context = context;
        this.itemList = itemList;
        this.user=user;
    }
    public void setItems(List<Item> items) {
        itemList = items;
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
        holder.itemImage.setImageResource(item.getImageResourceIdByInt(context));

        // Set click listeners for both itemImage and itemName
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open ProductDetailActivity when image is clicked
                openProductDetailActivity(item,user);
            }
        });

        holder.itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open ProductDetailActivity when name is clicked
                openProductDetailActivity(item,user);
            }
        });

        holder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if(item.isFavorite()==false)
                {
                    user.addToUserFavorite(item.getNameDetail());
                }
                else
                {

                        user.removeUserFavorite(item.getNameDetail());

                }
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

    private void openProductDetailActivity(Item item, User user) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("item_data", item);
        intent.putExtra("user_data", user);
        context.startActivity(intent);
    }

}
