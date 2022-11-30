package com.example.basicweather.api;

import com.example.basicweather.Retrofit.CurrentWeatherModel;
import com.example.basicweather.Retrofit.ForecastWeatherModel;
import com.example.basicweather.Retrofit.SearchCityModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
//api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
//api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    @GET("weather?appid=cad2de7ce7da1b44901d4a2842083848&units=metric")
    Call<CurrentWeatherModel> getWeatherData(@Query("lat") Double lat, @Query("lon") Double lon);

    @GET("onecall?exclude=minutely&appid=cad2de7ce7da1b44901d4a2842083848&units=metric")
    Call<ForecastWeatherModel> getForecast(@Query("lat") Double lat, @Query("lon") Double lon);

    @GET("find?mode=json&type=like&appid=cad2de7ce7da1b44901d4a2842083848&cnt=10")
    Call<SearchCityModel> getCities(@Query("q") String city);

}


//http://api.openweathermap.org/data/2.5/find?mode=json&type=like&q=CITY&appid=cad2de7ce7da1b44901d4a2842083848&cnt=10