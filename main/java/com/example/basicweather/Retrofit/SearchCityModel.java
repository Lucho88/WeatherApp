package com.example.basicweather.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchCityModel {

    @SerializedName("list")
    @Expose
    private List<City> cityList;

    public List<City> getCityList() {
        return cityList;
    }


    public static class City {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("sys")
        @Expose
        private Sys sys;
        @SerializedName("coord")
        @Expose
        private Coord coord;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Sys getSys() {
            return sys;
        }
        public Coord getCoord() {
            return coord;
        }

    }

    public class Sys {
        @SerializedName("country")
        @Expose
        private String country;

        public String getCountry() {
            return country;
        }

    }

    public class Coord {
        @SerializedName("lat")
        @Expose
        private double lat;

        @SerializedName("lon")
        @Expose
        private double lon;

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }

    }

}
