package com.example.basicweather.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherModel {

    @SerializedName("main")
    private Main main;

    @SerializedName("name")
    private String name;

    @SerializedName("weather")
    private List<Weather> weatherList;


    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public class Weather {

        @SerializedName("main")
        String main;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        @SerializedName("description")
        String description;

        @SerializedName("icon")
        String icon;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }


    }

    public class Main {
        @SerializedName("temp")
        Double temp;

        @SerializedName("humidity")
        String humidity;

        @SerializedName("feels_like")
        Double feels_like;

        @SerializedName("temp_min")
        Double temp_min;

        @SerializedName("temp_max")
        Double temp_max;

        public Double getTemp_min() {
            return temp_min;
        }



        public void setTemp_min(Double temp_min) {
            this.temp_min = temp_min;
        }

        public Double getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(Double temp_max) {
            this.temp_max = temp_max;
        }

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public Double getFeels_like() {
            return feels_like;
        }

        public void setFeels_like(Double feels_like) {
            this.feels_like = feels_like;
        }
    }

}
