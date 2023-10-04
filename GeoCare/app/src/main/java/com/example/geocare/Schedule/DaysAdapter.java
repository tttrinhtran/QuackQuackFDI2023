package com.example.geocare.Schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.geocare.R;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek; // Import java.time.DayOfWeek
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {
    private List<DayOfWeek> daysList;

    public DaysAdapter(List<DayOfWeek> daysList) {
        this.daysList = daysList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayOfWeek day = daysList.get(position);
        String sourceString =day.toString();
        char characterToGet = sourceString.charAt(0);
        String newString = String.valueOf(characterToGet);

        LocalDate currentDate = LocalDate.now();

        int currentDayOfMonth = currentDate.getDayOfMonth();

        if (currentDayOfMonth-2 == position){
            holder.dayName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue_deep));
            holder.dayNumber.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue_deep));
            holder.dayName.setText(newString);
            holder.dayNumber.setText(String.valueOf(position +2));
        }
        else{
            holder.dayName.setText(newString);
            holder.dayNumber.setText(String.valueOf(position +2));
        }
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayName;
        public TextView dayNumber;

        public ViewHolder(View view) {
            super(view);
            dayName = view.findViewById(R.id.day_name);
            dayNumber = view.findViewById(R.id.day_number);
        }
    }
}
