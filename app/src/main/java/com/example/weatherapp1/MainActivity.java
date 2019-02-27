package com.example.weatherapp1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, updatedField;
    String city = "London";
    String OPEN_WEATHER_MAP_API = "b60c7e86a1a0721e4f380436455f7f25";
    ListView lv = null;
    JSONObject json = null;
    Activity activity = getParent();
    ArrayList<MainWeatherClass> weatherList = new ArrayList<MainWeatherClass>();
    ListAdapter adapter = new ListAdapter(this, weatherList);
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        cityField = (TextView) findViewById(R.id.cityField);
        updatedField = (TextView) findViewById(R.id.updateField);
        detailsField = (TextView) findViewById(R.id.detailField);
        currentTemperatureField = (TextView) findViewById(R.id.tempField);
        humidity_field = (TextView) findViewById(R.id.humidField);
        pressure_field = (TextView) findViewById(R.id.pressureField);

        taskLoadUp(city);
    }

    public void taskLoadUp(String query) {
        if (Networking.isNetworkAvailable(this)) {
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
            Log.d("Network", "taskLoadUp: here");
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    class DownloadWeather extends AsyncTask< String, Void, String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Network", "onPreExecute: here");
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        protected String doInBackground(String...args) {
            try {
                String xml = Networking.run("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                        "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
                Log.d("Network", "doInBackground: " + xml);
                return xml;
            } catch (Exception e) {
                Log.d("Exception", "doInBackground: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String xml) {
            try {
                json = new JSONObject(xml);
                Log.d("Network", "onPostExecute: " + json.toString(1));
                MainWeatherClass main = gson.fromJson(xml, MainWeatherClass.class);
                Log.d("Network", "onPostExecute: " + main.toString());
                if(main == null) {
                    Log.d("Exception", "onPostExecute: main is null");
                }
                Log.d("Network", "onPostExecute: " + main.toString());
                weatherList.add(main);

                lv = findViewById(R.id.weatherLV);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
