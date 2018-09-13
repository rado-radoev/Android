package com.superlamer.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    EditText cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView backgroundImageView = (ImageView) findViewById(R.id.weatherBackgroundImageView);

        cityName = (EditText) findViewById(R.id.cityNameTextField);



    }

    public void getWeather(View view) {
        Log.i("City Name", cityName.getText().toString());
        String newCityName = cityName.getText().toString().replace(" ", "+");
        Log.i("NEW City Name", newCityName);


        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute("http://api.openweathermap.org/data/2.5/weather?q=" + newCityName + "&APPID=6a0326b54ac62aae38ee842128683084");
    }
}
