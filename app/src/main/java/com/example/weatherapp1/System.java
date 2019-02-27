package com.example.weatherapp1;

public class System {
    private String country;
    private String sunrise;
    private String sunset;
    private String id;
    private String type;
    private String message;

    public System(String country, String sunrise, String sunset, String id, String type, String message) {
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.id = id;
        this.type = type;
        this.message = message;
    }

    public System() {
    }

    public String getCountry () {
        return country;
    }
    public void setCountry (String country) {
        this.country = country;
    }
    public String getSunrise () {
        return sunrise;
    }
    public void setSunrise (String sunrise) {
        this.sunrise = sunrise;
    }
    public String getSunset () {
        return sunset;
    }
    public void setSunset (String sunset) {
        this.sunset = sunset;
    }
    public String getId () {
        return id;
    }
    public void setId (String id) {
        this.id = id;
    }
    public String getType () {
        return type;
    }
    public void setType (String type) {
        this.type = type;
    }
    public String getMessage () {
        return message;
    }
    public void setMessage (String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "System{" +
                "country='" + country + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
