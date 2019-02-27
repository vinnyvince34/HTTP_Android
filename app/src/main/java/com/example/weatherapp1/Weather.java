package com.example.weatherapp1;

public class Weather {
    private String icon;
    private String description;
    private String main;
    private String id;

    public Weather(String icon, String description, String main, String id) {
        this.icon = icon;
        this.description = description;
        this.main = main;
        this.id = id;
    }

    public Weather() {
    }

    public String getIcon () {
        return icon;
    }
    public void setIcon (String icon) {
        this.icon = icon;
    }
    public String getDescription () {
        return description;
    }
    public void setDescription (String description) {
        this.description = description;
    }
    public String getMain () {
        return main;
    }
    public void setMain (String main) {
        this.main = main;
    }
    public String getId () {
        return id;
    }
    public void setId (String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", main='" + main + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
