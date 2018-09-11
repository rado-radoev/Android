package com.superlamer.celebrityfaceoff;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> celebURLs = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();
    int choseCeleb = 0;
    int locationOfCorrectAnswer = 0;
    int[] answers = new int[4];


    ImageView celebrityImage;


    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        public Bitmap doInBackground(String ... urls) {


            Bitmap celebBitmap = null;

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                celebBitmap = BitmapFactory.decodeStream(in);

            } catch (IOException mfe) {
                mfe.printStackTrace();
            }

            return celebBitmap;
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        public String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }
    }

    

    public void celebChosen(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        celebrityImage = (ImageView) findViewById(R.id.celebrityImage);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("http://www.posh24.se/kandisar").get();
//            Log.i("Contents of url", result);

            String[] splitResult = result.split("<div class=\"sidebarContainer\">");

            Pattern p = Pattern.compile("src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebURLs.add(m.group(1));
//                Log.i("Image URL", m.group(1));
            }


            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebNames.add(m.group(1));
//                Log.i("Celebrity name" , m.group(1));
            }


            Random random = new Random();
            choseCeleb = random.nextInt(celebNames.size());

            ImageDownloader imageTask = new ImageDownloader();
            Bitmap celebImage = null;
            celebImage = imageTask.execute(celebURLs.get(choseCeleb)).get();
            celebrityImage.setImageBitmap(celebImage);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
