package com.example.weatherapp1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    protected TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, updatedField;
    public static String city = "London";
    public static String OPEN_WEATHER_MAP_API = "b60c7e86a1a0721e4f380436455f7f25";
    protected ListView lv = null;
    protected JSONObject json = null;
    protected Activity activity = getParent();
    public ArrayList<MainWeatherClass> weatherList;
    protected ListAdapter adapter;
    protected static Gson gson = new Gson();
    protected DisplayItem display = new DisplayItem();
    protected ArrayList<DisplayItem> displayItems = new ArrayList<DisplayItem>();
    protected Intent mainIntent = new Intent(this, Main3Activity.class);
    protected Retrofit retrofit = null;
    public static String endpointA = "weather?q=" + city + "&units=metric&appid=" + OPEN_WEATHER_MAP_API;
    public static SQLiteDatabase db = null;
    public static DBHelper helper;
    public static Cursor result;
    public static final int REQUEST_CODE = 1;
    public static boolean bEdit = false;
    public static boolean bIntentEmpty = true;

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
        Button addButton = (Button) findViewById(R.id.AddBTN);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bEdit = false;
                bIntentEmpty = false;
                Intent intent = new Intent(v.getContext(), Main3Activity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        helper = new DBHelper(this);
        weatherList = new ArrayList<MainWeatherClass>();
        adapter = new ListAdapter(this, weatherList, helper);

        listResourcesApi service = Networking.getRetrofitObject();

        service.getMainWeatherClass().enqueue(new Callback<MainWeatherClass>() {
            @Override
            public void onResponse(Call<MainWeatherClass> call, retrofit2.Response<MainWeatherClass> response) {
                Log.d("Network", "onResponse: " + response.body());
                try {
                    String xml = String.valueOf(response.body());
                    Log.d("Network", "onResponse: " + xml);
                    MainWeatherClass main = response.body();
                    Log.d("Network", "onPostExecute: " + main.toString());
                    if(main == null) {
                        Log.d("Exception", "onPostExecute: main is null");
                    }
                    Log.d("Network", "onPostExecute: " + main.toString());

                    helper = new DBHelper(getApplicationContext());
                    db = helper.getWritableDatabase();
                    result = db.rawQuery("SELECT  * FROM Weather", null);
                    result.moveToFirst();

                    String tempID = "";
                    String tempCountry = "";
                    String tempWindSpeed = "";
                    String tempVisibility = "";
                    String tempTemp = "";
                    String tempHumid = "";
                    String tempPressure = "";
                    while(!result.isAfterLast() ) {
                        tempID = result.getString(result.getColumnIndex("ID"));
                        tempCountry = result.getString(result.getColumnIndex("Country"));
                        tempWindSpeed = result.getString(result.getColumnIndex("Wind_speed"));
                        tempVisibility = result.getString(result.getColumnIndex("Visibility"));
                        tempTemp = result.getString(result.getColumnIndex("Temperature"));
                        tempHumid = result.getString(result.getColumnIndex("Humidity"));
                        tempPressure = result.getString(result.getColumnIndex("Pressure"));
                        displayItems.add(new DisplayItem(tempID, tempCountry, tempWindSpeed, tempVisibility, tempTemp, tempHumid, tempPressure));
                        result.moveToNext();
                    }

                    Log.d("Network", "onPostExecute: " + displayItems.toString());

                    weatherList.add(main);
                    lv = findViewById(R.id.weatherLV);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.d("Exception", "onResponse: " + e.getMessage());
                    return;
                }
            }
            @Override
            public void onFailure(Call<MainWeatherClass> call, Throwable t) {
                Log.d("onFailure", t.toString());
                return;
            }
        });
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
//                String xml = Networking.run("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
//                        "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
                Log.d("Network", "doInBackground:");
                return null;
            } catch (Exception e) {
                Log.d("Exception", "doInBackground: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String xml) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(bIntentEmpty == true) {
                Intent intent = getIntent();
            } else {
                if(bEdit == true) {
                    MainWeatherClass receiver = (MainWeatherClass) data.getParcelableExtra("UpdateData");
                    Log.d("MainActivity",weatherList.get(0).getBase() + "\n");
                    if (resultCode == Activity.RESULT_OK) {
                        weatherList.set(weatherList.size(), new MainWeatherClass(receiver.getWeather(), receiver.getCloud(), receiver.getWind(), receiver.getCoord(), receiver.getSys(), receiver.getMain(), receiver.getBase(), receiver.getVisibility(), receiver.getDt(), receiver.getId(), receiver.getName(), receiver.getHttpCode()));
                        adapter.notifyDataSetInvalidated();
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        return;
                    }
                } else {
                    MainWeatherClass receiver = (MainWeatherClass) data.getParcelableExtra("AddData");
                    Log.d("MainActivity",weatherList.get(0).getBase()  + "\n");
                    if (resultCode == Activity.RESULT_OK) {
                        weatherList.add(new MainWeatherClass(receiver.getWeather(), receiver.getCloud(), receiver.getWind(), receiver.getCoord(), receiver.getSys(), receiver.getMain(), receiver.getBase(), receiver.getVisibility(), receiver.getDt(), receiver.getId(), receiver.getName(), receiver.getHttpCode()));
                        helper.insert(weatherList.size() + 1, receiver.getName(), receiver.getWind().getSpeed(), receiver.getVisibility(), receiver.getMain().getTemp(), receiver.getMain().getHumidity(), receiver.getMain().getPressure());
                        adapter.notifyDataSetInvalidated();
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        return;
                    }
                }
            }
        }
    }
}
