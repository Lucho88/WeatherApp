package com.example.basicweather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicweather.R;
import com.example.basicweather.Retrofit.SearchCityModel;

import java.util.ArrayList;

public class SearchCityRVAdapter extends RecyclerView.Adapter<SearchCityRVAdapter.ViewHolder> {
    private ArrayList<SearchCityModel.City> citiesList;
    private Context context;


    public SearchCityRVAdapter(ArrayList<SearchCityModel.City> citiesList, Context context) {
        this.citiesList = citiesList;
        this.context = context;
    }


    @NonNull
    @Override
    public SearchCityRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_rv_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCityRVAdapter.ViewHolder holder, int position) {
        SearchCityModel.City searchCityModel = citiesList.get(position);
        holder.cityTV.setText(searchCityModel.getName() + ", " + searchCityModel.getSys().getCountry());
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTV = itemView.findViewById(R.id.cityTV);
        }
    }

}
