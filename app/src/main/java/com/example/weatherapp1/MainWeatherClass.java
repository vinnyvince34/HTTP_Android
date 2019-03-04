package com.example.weatherapp1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Pack200;

public class MainWeatherClass implements Parcelable {
    private List<Weather> weather;
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

    public MainWeatherClass(List<Weather> weather, Cloud cloud, Wind wind, Coord coord, System sys, Other main, String base, String visibility, String dt, String id, String name, String httpCode) {
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

    public MainWeatherClass(List<Weather> weather, Cloud cloud, Wind wind, Coord coord, System sys, Other main) {
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

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
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
                "weather=" + weather +
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.weather);
        dest.writeParcelable(this.cloud, flags);
        dest.writeParcelable(this.wind, flags);
        dest.writeParcelable(this.coord, flags);
        dest.writeParcelable(this.sys, flags);
        dest.writeParcelable(this.main, flags);
        dest.writeString(this.base);
        dest.writeString(this.visibility);
        dest.writeString(this.dt);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.httpCode);
    }

    protected MainWeatherClass(Parcel in) {
        this.weather = new ArrayList<>();
        in.readList(this.weather, Weather.class.getClassLoader());
        this.cloud = in.readParcelable(Cloud.class.getClassLoader());
        this.wind = in.readParcelable(Wind.class.getClassLoader());
        this.coord = in.readParcelable(Coord.class.getClassLoader());
        this.sys = in.readParcelable(System.class.getClassLoader());
        this.main = in.readParcelable(Other.class.getClassLoader());
        this.base = in.readString();
        this.visibility = in.readString();
        this.dt = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.httpCode = in.readString();
    }

    public static final Creator<MainWeatherClass> CREATOR = new Creator<MainWeatherClass>() {
        @Override
        public MainWeatherClass createFromParcel(Parcel source) {
            return new MainWeatherClass(source);
        }

        @Override
        public MainWeatherClass[] newArray(int size) {
            return new MainWeatherClass[size];
        }
    };
}
