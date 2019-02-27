package com.example.weatherapp1;

public class Cloud {
    private String type;

    public Cloud(String type) {
        this.type = type;
    }

    public Cloud() {
    }

    public String getType () {
        return type;
    }
    public void setAll (String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Cloud{" +
                "type='" + type + '\'' +
                '}';
    }
}
