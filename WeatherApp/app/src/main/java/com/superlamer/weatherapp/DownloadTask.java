package com.superlamer.weatherapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask <String, Void, String> {

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

        try {
            JSONObject jsonObject = new JSONObject(result);

            // Get the weather conditions
            String weatherInfo = jsonObject.getString("weather");
            JSONArray weatherArr = new JSONArray(weatherInfo);
            for (int i = 0; i < weatherArr.length(); i++) {
                JSONObject weatherJson = weatherArr.getJSONObject(i);
                Log.i("main", weatherJson.getString("main"));
                Log.i("Description", weatherJson.getString("description"));
            }

            // Get the temps
            String tempInfo = jsonObject.getJSONObject("temp");
            //JSONArray tempArr = new JSONArray(tempInfo);
//            for (int i = 0; i < tempArr.length(); i++) {
//                JSONObject tempJson = tempArr.getJSONObject(i);
//                Log.i("Current temp", tempJson.getString("temp"));
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private double convertKelvinToFahrenheit(String kelvin) {
        double k = Double.valueOf(kelvin);
        return ((k * 9) / 5) - 459.67;
    }
}
