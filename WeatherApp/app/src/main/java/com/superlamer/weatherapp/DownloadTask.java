package com.superlamer.weatherapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DownloadTask extends AsyncTask <String, Void, String> {

    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... urls) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection;

        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                result += current;
                data =  reader.read();
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Map<String, String> weatherData = null;

        try {
            weatherData = new HashMap<String, String>();
            JSONObject jsonObject = new JSONObject(result);

            // Get the weather conditions
            String weatherInfo = jsonObject.getString("weather");
            JSONArray weatherArr = new JSONArray(weatherInfo);
            for (int i = 0; i < weatherArr.length(); i++) {
                JSONObject weatherJson = weatherArr.getJSONObject(i);

                Log.i("main", weatherJson.getString("main"));
                weatherData.put("main", weatherJson.getString("main"));

                Log.i("Description", weatherJson.getString("description"));
                weatherData.put("Description", weatherJson.getString("description"));
            }

            // Get the temps
            JSONObject tempInfo = jsonObject.getJSONObject("main");
            String currTemp = tempInfo.getString("temp");
            Log.i("Current temp",  String.format("%.2f %s", convertKelvinToCelcius(currTemp), "\u2103"));
            weatherData.put("currentTemp", String.format(Locale.US,"%.2f %s", convertKelvinToCelcius(currTemp), "\u2103"));

            String minTemp = tempInfo.getString("temp_min");
            Log.i("Min temp",  String.format("%.2f %s", convertKelvinToCelcius(minTemp), "\u2103"));

            String maxTemp = tempInfo.getString("temp_max");
            Log.i("Max temp", String.format("%.2f %s", convertKelvinToCelcius(maxTemp), "\u2103"));

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            delegate.processFinish(weatherData);
        }
    }

    private double convertKelvinToCelcius(String kelvin) {
        double k = Double.valueOf(kelvin);
        return k - 273.15;
    }
}
