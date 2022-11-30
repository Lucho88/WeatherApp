package com.example.basicweather.Model;

public class DailyRVModal {
    private String day;
    private String icon;
    private String temperature;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public DailyRVModal(String day, String icon, String temperature) {
        this.day = day;
        this.icon = icon;
        this.temperature = temperature;
    }
}
