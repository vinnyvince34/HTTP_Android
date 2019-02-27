package com.example.weatherapp1;

public class Wind {
    private String speed;

    public Wind(String speed) {
        this.speed = speed;
    }

    public Wind() {
    }

    public String getSpeed () {
        return speed;
    }
    public void setSpeed (String speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed='" + speed + '\'' +
                '}';
    }
}
