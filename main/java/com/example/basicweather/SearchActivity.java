package com.example.basicweather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicweather.Retrofit.RecyclerItemClickListener;
import com.example.basicweather.Retrofit.SearchCityModel;
import com.example.basicweather.adapters.SearchCityRVAdapter;
import com.example.basicweather.api.ApiInterface;
import com.example.basicweather.api.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private final static String TAG = "SearchActivity";
    private SearchView searchSV;
    private ImageView closeIV;

    private RecyclerView searchCityRV;
    private ArrayList<SearchCityModel.City> citiesArrayList;
    private SearchCityRVAdapter searchCityRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        closeIV = findViewById(R.id.closeIV);
        searchSV = findViewById(R.id.searchET);

        searchCityRV = findViewById(R.id.searchCityRV);
        citiesArrayList = new ArrayList<>();
        searchCityRVAdapter = new SearchCityRVAdapter(citiesArrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        searchCityRV.setLayoutManager(linearLayoutManager);
        searchCityRV.setAdapter(searchCityRVAdapter);

        searchCityRV.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this, searchCityRV, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String cityName = citiesArrayList.get(position).getName();
                double lat = citiesArrayList.get(position).getCoord().getLat();
                double lon = citiesArrayList.get(position).getCoord().getLon();
                Toast.makeText(SearchActivity.this, cityName + lat + lon, Toast.LENGTH_LONG).show();
                Intent i = new Intent(SearchActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", lat);
                bundle.putDouble("lon", lon);
                i.putExtras(bundle);
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        closeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SearchActivity.this, MainActivity.class));
            }
        });


        searchSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCities(searchSV.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    searchCityRV.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
    }

    private void searchCities(String city) {
        ApiInterface apiInterface = RetrofitBuilder.getClient().create(ApiInterface.class);
        Call<SearchCityModel> call = apiInterface.getCities(city);
        call.enqueue(new Callback<SearchCityModel>() {
            @Override
            public void onResponse(Call<SearchCityModel> call, Response<SearchCityModel> response) {
                if(response.code() == 200){
                    searchCityRV.setVisibility(View.VISIBLE);
                    citiesArrayList.clear();
                    List<SearchCityModel.City> cityList  = response.body().getCityList();
                    citiesArrayList.addAll(cityList);
                    searchCityRVAdapter.notifyDataSetChanged();
                    if(cityList.isEmpty()){
                        Toast.makeText(SearchActivity.this,"No cities found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    searchCityRV.setVisibility(View.INVISIBLE);
                    Toast.makeText(SearchActivity.this,"No cities, bad code", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + response.errorBody().toString() + response.code());
                }


            }

            @Override
            public void onFailure(Call<SearchCityModel> call, Throwable t) {
                Log.d(TAG, "onFailure: no response" );
            }
        });

    }


}