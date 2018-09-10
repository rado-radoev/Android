package com.superlamer.guessthecelebrity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    String[] names;
    Bitmap[] celebrityImages;

    public class URLParser extends AsyncTask<String, Void, String> {

        @Override
        public String doInBackground(String... urls) {

            return null;
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
