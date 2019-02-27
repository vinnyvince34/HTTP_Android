package com.example.weatherapp1;

import java.util.Arrays;

public class MainWeatherClass {
    private Weather[] weather;
    private Cloud cloud;
    private Wind wind;
    private Coord coord;
    private System sys;
    private Other main;
    private String base;
    private String visibility;
    private String dt;
    private String id;
    private String name;
    private String httpCode;

    public MainWeatherClass() {
    }

    public MainWeatherClass(Weather weather[], Cloud cloud, Wind wind, Coord coord, System sys, Other main, String base, String visibility, String dt, String id, String name, String httpCode) {
        this.weather = weather;
        this.cloud = cloud;
        this.wind = wind;
        this.coord = coord;
        this.sys = sys;
        this.main = main;
        this.base = base;
        this.visibility = visibility;
        this.dt = dt;
        this.id = id;
        this.name = name;
        this.httpCode = httpCode;
    }

    public MainWeatherClass(Weather weather[], Cloud cloud, Wind wind, Coord coord, System sys, Other main) {
        this.weather = weather;
        this.cloud = cloud;
        this.wind = wind;
        this.coord = coord;
        this.sys = sys;
        this.main = main;
        this.base = "";
        this.visibility = "";
        this.dt = "";
        this.id = "";
        this.name = "";
        this.httpCode = "";
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather weather[]) {
        this.weather = weather;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public System getSys() {
        return sys;
    }

    public void setSys(System sys) {
        this.sys = sys;
    }

    public Other getMain() {
        return main;
    }

    public void setMain(Other main) {
        this.main = main;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    @Override
    public String toString() {
        return "MainWeatherClass{" +
                "weather=" + Arrays.toString(weather) +
                ", cloud=" + cloud +
                ", wind=" + wind +
                ", coord=" + coord +
                ", sys=" + sys +
                ", main=" + main +
                ", base='" + base + '\'' +
                ", visibility='" + visibility + '\'' +
                ", dt='" + dt + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", httpCode='" + httpCode + '\'' +
                '}';
    }
}
