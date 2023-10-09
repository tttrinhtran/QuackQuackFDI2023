package com.example.geocare.Register;

import static com.google.common.io.Resources.getResource;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geocare.Model.User;
import com.example.geocare.R;

import java.util.ArrayList;

public class surveyAdapter extends RecyclerView.Adapter<surveyAdapter.MyViewHolder> {

    ArrayList<surveyItem> skinList;
    Context context;
    User user;

    ImageView nextBtn;
    public surveyAdapter(ArrayList<surveyItem> hobbiesList, Context context, User user, ImageView nextBtn)
    {
        this.skinList=hobbiesList;
        this.context=context;
        this.user=user;
        this.nextBtn=nextBtn;
    }
    @NonNull
    @Override
    public surveyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.survey_item, parent,false);
        return new surveyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull surveyAdapter.MyViewHolder holder, int position) {
        holder.text.setText(skinList.get(position).getName());
        int id = context.getResources().getIdentifier(skinList.get(position).getImg(), "drawable", context.getPackageName());
        holder.img.setImageResource(id);
        holder.background.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user.getUserSkinType()==null) {
                    holder.background.setCardBackgroundColor(Color.parseColor("#FF78C6FF"));
                    user.setUserSkinType(skinList.get(position).getName());
                    nextBtn.setClickable(true);
                    nextBtn.setImageResource(R.drawable.next_button_active);

                }
                else if(user.getUserSkinType().equals(skinList.get(position).getName()))
                {
                    holder.background.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    user.setUserSkinType(null);
                    nextBtn.setClickable(false);
                    nextBtn.setImageResource(R.drawable.next_button);


                } else if (!user.getUserSkinType().equals(skinList.get(position).getName())) {
                    holder.background.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    nextBtn.setClickable(false);
                    nextBtn.setImageResource(R.drawable.next_button);

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return skinList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView img;
        CardView background;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.skinSurveyText);
            img=itemView.findViewById(R.id.skinSurveyImg);
            background=itemView.findViewById(R.id.skinSurveyBg);
        }
    }
}
