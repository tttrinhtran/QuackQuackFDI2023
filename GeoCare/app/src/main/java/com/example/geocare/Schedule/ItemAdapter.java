package com.example.geocare.Schedule;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geocare.R;

import java.util.List;
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<ProductItem> productList;

    public ItemAdapter(List<ProductItem> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_routine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem product = productList.get(position);

        // Bind data to views
        holder.productImage.setImageResource(product.getImageIDResource());
        holder.productAmount.setText(product.getAmount());
        holder.productCategory.setText(product.getCategory());
        holder.productShortDescription.setText(product.getShortDescription());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

//    public void removeItem(int position) {
//        if (position >= 0 && position < productList.size()) {
//            productList.remove(position);
//            notifyItemRemoved(position);
//        }
//    }


    public void removeItem(int position) {
        if (position >= 0 && position < productList.size()) {
            // Delay the removal by a short amount of time
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    productList.remove(position);
                    notifyItemRemoved(position);
                }
            }, 100); // Delay in milliseconds (adjust as needed)
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productAmount;
        public TextView productCategory;
        public TextView productShortDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productAmount = itemView.findViewById(R.id.productAmount);
            productCategory = itemView.findViewById(R.id.productCategory);
            productShortDescription = itemView.findViewById(R.id.productShortDescription);
        }
    }
}
