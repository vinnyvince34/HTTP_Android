package com.example.weatherapp1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request;

public class Networking {
    static OkHttpClient client = new OkHttpClient();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static MainWeatherClass ConvertJSON (JSONObject json) {
        int i = 0;
        MainWeatherClass main = null;
        try {
            JSONObject jsonChild = null;
            JSONArray jsonArray = null;
            JsonParser jsonParser =new JsonParser();

            String base = json.get("base").toString();
            String visibility = json.get("visibility").toString();
            String dt = json.get("dt").toString();
            String id = json.get("id").toString();
            String name = json.get("name").toString();
            String httpCode = json.get("cod").toString();

            jsonChild = (JSONObject)json.get("coord");
            Coord coord = new Coord(jsonChild.get("lon").toString(), jsonChild.get("lat").toString());
            i++;

            jsonChild = (JSONObject)json.get("clouds");
            Cloud cloud = new Cloud(jsonChild.get("all").toString());
            i++;

            jsonChild = (JSONObject)json.get("wind");
            Wind wind = new Wind(jsonChild.get("speed").toString());
            i++;

            jsonChild = (JSONObject)json.get("sys");
            System system = new System(jsonChild.get("type").toString(), jsonChild.get("id").toString(), jsonChild.get("message").toString(), jsonChild.get("country").toString(), jsonChild.get("sunrise").toString(), jsonChild.get("sunset").toString());
            i++;

            jsonArray = (JSONArray) json.get("weather");
            jsonChild = jsonArray.getJSONObject(0);
            Log.d("Network", "ConvertJSON: " + jsonChild.toString());
            Log.d("Network", "ConvertJSON: " + jsonChild.get("id").toString());
//            Weather weather = new Weather(jsonChild.get("id").toString(), jsonChild.get("main").toString(), jsonChild.get("description").toString(), jsonChild.get("icon").toString());
            Weather[] weather = new Weather[1];
            weather[0] = new Weather();
            weather[0].setId(jsonChild.get("id").toString());
            weather[0].setIcon(jsonChild.get("icon").toString());
            weather[0].setMain(jsonChild.get("main").toString());
            weather[0].setDescription(jsonChild.get("description").toString());
            i++;

            jsonChild = (JSONObject)json.get("main");
            Other other = new Other(jsonChild.get("temp").toString(), jsonChild.get("pressure").toString(), jsonChild.get("humidity").toString(), jsonChild.get("temp_min").toString(), jsonChild.get("temp_max").toString());
            i++;

            main = new MainWeatherClass(weather, cloud, wind, coord, system, other, base, visibility, dt, id, name, httpCode);
            Log.d("Network", "ConvertJSON: " + main.toString());
        } catch (Exception e) {
            Log.d("Exception", "ConvertJSON: " + e.getMessage() + ", " + i);
            return null;
        }
        return main;
    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static String executeGet(String targetURL) {
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("content-type", "application/json;  charset=utf-8");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(false);

            InputStream is;
            int status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                Log.d("Network", "executeGet: " + connection.getResponseCode());
                is = connection.getErrorStream();
            }
            else {
                Log.d("Network", "executeGet: " + connection.getResponseCode());
                is = connection.getInputStream();
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            Log.d("Network", "executeGet: " + response.toString());
            return response.toString();
        } catch (Exception e) {
            return null;
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
