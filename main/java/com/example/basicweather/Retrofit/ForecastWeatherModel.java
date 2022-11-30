package com.example.basicweather.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastWeatherModel {

    @SerializedName("timezone")
    private String timezone;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @SerializedName("hourly")
    private List<Hourly> hourlyList;

    @SerializedName("daily")
    private List<Daily> dailyList;

    public List<Hourly> getHourlyList() {
        return hourlyList;
    }

    public void setHourlyList(List<Hourly> hourlyList) {
        this.hourlyList = hourlyList;
    }

    public List<Daily> getDailyList() {
        return dailyList;
    }

    public void setDailyList(List<Daily> dailyList) {
        this.dailyList = dailyList;
    }


    public class Hourly {
        public String getDt() {
            return dt;
        }

        public void setDt(String dt) {
            this.dt = dt;
        }

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public List<Weather> getWeatherList() {
            return weatherList;
        }

        public void setWeatherList(List<Weather> weatherList) {
            this.weatherList = weatherList;
        }

        @SerializedName("dt")
        private String dt;

        @SerializedName("temp")
        private Double temp;

        @SerializedName("weather")
        private List<Weather> weatherList;

    }

    public class Weather {
        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @SerializedName("icon")
        private String icon;
    }

    public class Daily {
        @SerializedName("dt")
        private String time;

        @SerializedName("temp")
        private Temp temp;

        public Temp getTemp() {
            return temp;
        }

        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        @SerializedName("weather")
        private List<DailyWeather> dailyWeatherList;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<DailyWeather> getDailyWeatherList() {
            return dailyWeatherList;
        }

        public void setDailyWeatherList(List<DailyWeather> dailyWeatherList) {
            this.dailyWeatherList = dailyWeatherList;
        }
    }

    public class Temp {

        @SerializedName("min")
        private Double min;

        public Double getMin() {
            return min;
        }

        public void setMin(Double min) {
            this.min = min;
        }

        public Double getMax() {
            return max;
        }

        public void setMax(Double max) {
            this.max = max;
        }

        @SerializedName("max")
        private Double max;

    }


    public class DailyWeather {
        @SerializedName("icon")
        private String icon;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
