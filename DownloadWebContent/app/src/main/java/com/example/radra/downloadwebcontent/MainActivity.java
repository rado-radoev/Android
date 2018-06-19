package com.example.radra.downloadwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            boolean failed = false;
           String result = "";
           URL url;
           HttpURLConnection urlConnection = null;

           try {
               url = new URL(urls[0]);
               urlConnection = (HttpURLConnection)url.openConnection();
               InputStream in = urlConnection.getInputStream();
               InputStreamReader reader = new InputStreamReader(in);
               int data = reader.read();

               while (data != -1) {
                   char current = (char)data;
                   result += current;
                   data = reader.read();
               }

           } catch (Exception e) {
               e.printStackTrace();
                failed = true;
           }

           return String.valueOf(failed);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = "";
        try {
            result = task.execute("http://www.google.com/").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Log.i("Content of URL: ", result);
    }
}
