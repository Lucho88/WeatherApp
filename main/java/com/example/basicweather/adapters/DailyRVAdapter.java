package com.example.basicweather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicweather.R;
import com.example.basicweather.Model.DailyRVModal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DailyRVAdapter extends RecyclerView.Adapter<DailyRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DailyRVModal> dailyRVModalArrayList;

    public DailyRVAdapter(Context context, ArrayList<DailyRVModal> dailyRVModalArrayList) {
        this.context = context;
        this.dailyRVModalArrayList = dailyRVModalArrayList;
    }

    @NonNull
    @Override
    public DailyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daily_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyRVAdapter.ViewHolder holder, int position) {
        DailyRVModal dailyRVModal = dailyRVModalArrayList.get(position);
        holder.day.setText(dailyRVModal.getDay());
        Picasso.with(context).load("http://openweathermap.org/img/w/" + dailyRVModal.getIcon() + ".png").into(holder.icon);
        holder.temperature.setText(dailyRVModal.getTemperature());


    }

    @Override
    public int getItemCount() {
        return dailyRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView temperature, day;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.dailyConIV);
            temperature = itemView.findViewById(R.id.dayTempTV);
            day = itemView.findViewById(R.id.dayTV);
        }
    }
}
