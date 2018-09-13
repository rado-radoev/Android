package com.superlamer.weatherapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements  AsyncResponse {

    EditText cityName;
    TextView currenCondition;
    TextView currentConditionDescription;
    TextView currentTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText) findViewById(R.id.cityNameTextField);
        currenCondition = (TextView) findViewById(R.id.currentConditionTextView);
        currentConditionDescription = (TextView) findViewById(R.id.currentConditionDescTextVIew);
        currentTemp = (TextView) findViewById(R.id.currentTempTextView);

        ImageView backgroundImageView = (ImageView) findViewById(R.id.weatherBackgroundImageView);


    }

    public void getWeather(View view) {
        Log.i("City Name", cityName.getText().toString());
        //String newCityName = cityName.getText().toString().replace(" ", "+");
        String newCityName = null;
        try {
            newCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");
            Log.i("NEW City Name", newCityName);

            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

            DownloadTask downloadTask = new DownloadTask();
            downloadTask.delegate = this;
            downloadTask.execute("http://api.openweathermap.org/data/2.5/weather?q=" + newCityName + "&APPID=6a0326b54ac62aae38ee842128683084");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void processFinish(Map<String, String> output) {
        Log.i("Output", output.toString());
        if (!output.isEmpty()) {
            currenCondition.setText(String.format(Locale.US, "%s: %s", "Currently", output.get("main")));
            currentConditionDescription.setText(String.format(Locale.US, "%s: %s", "Current condition", output.get("Description")));
            currentTemp.setText(String.format(Locale.US, "%s: %s", "Current temp: ", output.get("currentTemp")));
        }
        else {
            Toast.makeText(getApplicationContext(), "Invalid location", Toast.LENGTH_SHORT).show();
        }
    }
}
