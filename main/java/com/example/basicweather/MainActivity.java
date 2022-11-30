package com.example.basicweather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicweather.Retrofit.CurrentWeatherModel;
import com.example.basicweather.Retrofit.ForecastWeatherModel;
import com.example.basicweather.api.RetrofitBuilder;
import com.example.basicweather.api.ApiInterface;
import com.example.basicweather.adapters.DailyRVAdapter;
import com.example.basicweather.adapters.WeatherRVAdapter;
import com.example.basicweather.Model.DailyRVModal;
import com.example.basicweather.Model.WeatherRVModal;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;

    private BottomNavigationView bottomNavigationView;


    private ConstraintLayout constraintLayout;
    private static final String TAG = "MainActivity";
    int LOCATION_REQUEST_CODE = 10001;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private TextView textTemp, textFeelsLike, textHum, txtCity, txtTempMinMax, txtDescription;

    private View widgetGroup;

    private ImageView iconImage;

    private Button searchCityButton;

    private RecyclerView weatherHourlyRV, weatherDailyRV;

    private double latitude, longitude;

    private ArrayList<WeatherRVModal> weatherRVModalArrayList;
    private WeatherRVAdapter weatherRVAdapter;

    private ArrayList<DailyRVModal> dailyRVModalArrayList;
    private DailyRVAdapter dailyRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        textTemp = findViewById(R.id.textTemp);
        textFeelsLike = findViewById(R.id.textFeelsLike);
        textHum = findViewById(R.id.textHum);
        txtCity = findViewById(R.id.txtCity);
        txtTempMinMax = findViewById(R.id.textTempMinMax);
        txtDescription = findViewById(R.id.textDescription);
        iconImage = findViewById(R.id.iconImg);
        searchCityButton = (Button) findViewById(R.id.searchCityButton);

        widgetGroup = findViewById(R.id.widgetGroup);



        weatherHourlyRV =  findViewById(R.id.weatherRV);
        weatherRVModalArrayList = new ArrayList<>();
        weatherRVAdapter = new WeatherRVAdapter(this, weatherRVModalArrayList);
        LinearLayoutManager linLaMan = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        weatherHourlyRV.setLayoutManager(linLaMan);
        weatherHourlyRV.setAdapter(weatherRVAdapter);

        weatherDailyRV = findViewById(R.id.dailyRV);
        dailyRVModalArrayList = new ArrayList<>();
        dailyRVAdapter = new DailyRVAdapter(this,dailyRVModalArrayList);
        LinearLayoutManager layoutManagerDaily = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        weatherDailyRV.setLayoutManager(layoutManagerDaily);
        weatherDailyRV.setAdapter(dailyRVAdapter);

        constraintLayout = findViewById(R.id.constraintLO);
        bottomNavigationView = findViewById(R.id.bottomNav);

        searchCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });
    }

    public static MainActivity getInstance(){
        return instance;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            askLocationPermission();
        }

    }

    private void getLastLocation() {


                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    askLocationPermission();
                }
                Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
                locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            //we have a location
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d(TAG, "onSuccess: Location: " + location);
                            Log.d(TAG, "onSuccess: Latitude: " + latitude);
                            Log.d(TAG, "onSuccess: Longitude: " + longitude);
                            getWeatherCurrent(latitude, longitude);
                            getWeatherForecast(latitude, longitude);
                        }else {
                            Toast.makeText(MainActivity.this, "Location was NULL", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                locationTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: " + e.getLocalizedMessage() );
                    }
                });

    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.d(TAG, "askLocationPermission: you should show an alert dialog");
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission Granted
                getLastLocation();
            }else {
                //Permission Not Granted
                askLocationPermission();
            }
        }
    }


    private void getWeatherCurrent(Double lat, Double lon) {

                ApiInterface apiInterface = RetrofitBuilder.getClient().create(ApiInterface.class);

                Call<CurrentWeatherModel> call = apiInterface.getWeatherData(lat, lon);

                call.enqueue(new Callback<CurrentWeatherModel>() {
                    @Override
                    public void onResponse(Call<CurrentWeatherModel> call, Response<CurrentWeatherModel> response) {

                        if(response.code() == 200){
                            textTemp.setText((int) Math.round(response.body().getMain().getTemp()) + "°");
                            textFeelsLike.setText("Feels Like" + "  " + (int) Math.round(response.body().getMain().getFeels_like()) + "°");
                            textHum.setText("Humidity" + "  " + response.body().getMain().getHumidity() + "%");
                            txtCity.setText(response.body().getName());
                            txtTempMinMax.setText(getResources().getString(R.string.tempMin) + " " + (int) Math.round(response.body().getMain().getTemp_min())
                                    + "°" + " " + getResources().getString(R.string.tempMax) + (int) Math.round(response.body().getMain().getTemp_max()) + "°");
                            List<CurrentWeatherModel.Weather> list = response.body().getWeatherList();

                            if(list!=null && !list.isEmpty()){
                                txtDescription.setText(list.get(0).getDescription().toUpperCase());

                                String icon = list.get(0).getIcon();
                                Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + icon + ".png").into(iconImage);

                            }


                        }else {
                            Log.d(TAG, "onResponse: " + response.errorBody().toString() + (response));
                        }
                        widgetGroup.setVisibility(View.VISIBLE);
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<CurrentWeatherModel> call, Throwable t) {

                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                    }
                });

        }


    private void getWeatherForecast(Double lat, Double lon) {

                ApiInterface apiInterface = RetrofitBuilder.getClient().create(ApiInterface.class);
                Call<ForecastWeatherModel> call = apiInterface.getForecast(lat, lon);
                call.enqueue(new Callback<ForecastWeatherModel>() {
                    @Override
                    public void onResponse(Call<ForecastWeatherModel> call, Response<ForecastWeatherModel> response) {
                        if(response.code() == 200){
                            weatherRVModalArrayList.clear();
                            dailyRVModalArrayList.clear();
                            List<ForecastWeatherModel.Hourly> listHourly = response.body().getHourlyList();
                            List<ForecastWeatherModel.Daily> listDaily = response.body().getDailyList();

                            Calendar cal = Calendar.getInstance(TimeZone.getDefault(),Locale.getDefault());
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            String timezone = response.body().getTimezone();
                            sdf.setTimeZone(TimeZone.getTimeZone(timezone));

                            for (int i=0; i<25; i++){
                                cal.setTimeInMillis(Long.parseLong(listHourly.get(i).getDt()) * 1000);
                                String time = "";
                                time = sdf.format(new Date(cal.getTimeInMillis()));
                                int temp = (int) Math.round(listHourly.get(i).getTemp());
                                String temperature = String.valueOf(temp);
                                String icon = listHourly.get(i).getWeatherList().get(0).getIcon();
                                weatherRVModalArrayList.add(new WeatherRVModal(time, temperature, icon));
                                weatherRVModalArrayList.get(0).setTime("Now");
                            }
                            for (int m=0; m<listDaily.size(); m++){
                                int tempMin = (int) Math.round(listDaily.get(m).getTemp().getMin());
                                int tempMax = (int) Math.round(listDaily.get(m).getTemp().getMax());
                                String temperature = tempMin + "°" + "/" + tempMax + "°";
                                String icon = listDaily.get(m).getDailyWeatherList().get(0).getIcon();

                                cal.setTimeInMillis(Long.parseLong(listDaily.get(m).getTime()) * 1000);
                                int dayN = cal.get(Calendar.DAY_OF_WEEK);
                                String day = "";
                                switch (dayN){
                                    case 1:
                                        day = "Mon";
                                        break;
                                    case 2:
                                        day = "Tue";
                                        break;
                                    case 3:
                                        day = "Wed";
                                        break;
                                    case 4:
                                        day = "Thu";
                                        break;
                                    case 5:
                                        day = "Fri";
                                        break;
                                    case 6:
                                        day = "Sat";
                                        break;
                                    case 7:
                                        day = "Sun";
                                        break;
                                }
                                dailyRVModalArrayList.add(new DailyRVModal(day, icon, temperature));

                            }
                            dailyRVAdapter.notifyDataSetChanged();
                            weatherRVAdapter.notifyDataSetChanged();


                        }else {
                            Log.d(TAG, "onResponse: " + response.errorBody().toString());
                        }
                        widgetGroup.setVisibility(View.VISIBLE);
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<ForecastWeatherModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: no response");
                    }
                });

    }
}