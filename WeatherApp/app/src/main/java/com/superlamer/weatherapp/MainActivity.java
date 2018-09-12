package com.superlamer.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute("http://api.openweathermap.org/data/2.5/weather?q=San+Diego&APPID=6a0326b54ac62aae38ee842128683084");
    }
}
