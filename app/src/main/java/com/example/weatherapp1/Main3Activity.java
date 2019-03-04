package com.example.weatherapp1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

//Edit Page
public class Main3Activity extends AppCompatActivity {
    Intent intent;
    MainWeatherClass toEdit = null;
    MainWeatherClass toAdd = null;
    JSONObject json;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        final TextInputEditText cityInput = (TextInputEditText) findViewById(R.id.CityInput);
        Button SaveBtn = (Button) findViewById(R.id.SaveBtn);
        TextView textView = (TextView) findViewById(R.id.TextView);
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if(MainActivity.bEdit == true) {
            intent = getIntent();
            toEdit = (MainWeatherClass) intent.getParcelableExtra("EditUser");

            textView.setText("Edit City");
            cityInput.setText(toEdit.getName());

            SaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sNewAddress = String.valueOf(cityInput.getText());
                    toEdit.setName(sNewAddress);
                    Intent passIntent = new Intent(v.getContext(), MainActivity.class).putExtra("UpdateData", (Parcelable) toEdit);
                    setResult(RESULT_OK, passIntent);
                    finish();
                }
            });
        } else {
            intent = getIntent();
            toAdd = new MainWeatherClass();

            textView.setText("Add City");

            SaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String sNewAddress = String.valueOf(cityInput.getText());
                        setQuery(sNewAddress);
                    } catch (Exception e) {
                        Log.d("Exception", "onClick: " + e.getMessage());
                    }
                }
            });
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                MainActivity.bIntentEmpty = true;
                Intent exitIntent = new Intent(this, MainActivity.class);
                startActivity(exitIntent);
                finish();
                Log.d("Network", "onOptionsItemSelected: here");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void setQuery(String query) {
        if (Networking.isNetworkAvailable(this)) {
            Main3 task = new Main3();
            task.execute(query);
            Log.d("Network", "taskLoadUp: here");
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    class Main3 extends AsyncTask< String, Void, String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Network", "onPreExecute: here");
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                String xml = Networking.run("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                        "&units=metric&appid=" + MainActivity.OPEN_WEATHER_MAP_API);
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
                Intent passIntent = new Intent(getApplicationContext(), MainActivity.class).putExtra("AddData", (Parcelable) main);
                setResult(RESULT_OK, passIntent);
                finish();
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
