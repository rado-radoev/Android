package com.superlamer.celebrityfaceoff;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
    Random random;


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

    private void generateRandomAnswers() {
        Button[] buttons = new Button[] { findViewById(R.id.button),
                                            findViewById(R.id.button2),
                                            findViewById(R.id.button3),
                                            findViewById(R.id.button4)};

        int randomCelebIndex = random.nextInt(celebNames.size());
        int[] usedIndexes = new int[buttons.length];
        for (int i = 0; i < usedIndexes.length; i++) {
            usedIndexes[i] = 0;
        }
        int count = 0;

        for (Button b : buttons) {

            for (int i = 0; i < usedIndexes.length; i++) {
                if (usedIndexes[i] == randomCelebIndex) {
                    randomCelebIndex = random.nextInt(celebNames.size());
                } else {
                    usedIndexes[i] = randomCelebIndex;
                }
            }

            b.setText(celebNames.get(randomCelebIndex));
            count++;
        }



        int randomButton = random.nextInt(buttons.length - 1);
        Log.i("Random button", String.valueOf(randomButton));
        buttons[randomButton].setText(celebNames.get(choseCeleb));
        Log.i("Chosen celeb", String.valueOf(choseCeleb));
    }

    public void celebChosen(View view) {
        Button button = (Button) view;

        if (button.getText().equals(celebNames.get(choseCeleb))) {
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong! This is: " + celebNames.get(choseCeleb), Toast.LENGTH_LONG).show();
        }

        nextQuestion();
    }


    private void nextQuestion() {
        choseCeleb = random.nextInt(celebNames.size());
        Log.i("Chosen celeb", String.valueOf(choseCeleb));

        ImageDownloader imageTask = new ImageDownloader();
        Bitmap celebImage = null;
        try {
            celebImage = imageTask.execute(celebURLs.get(choseCeleb)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Log.i("Chose celeb URL", celebURLs.get(choseCeleb));
        celebrityImage.setImageBitmap(celebImage);

        generateRandomAnswers();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();
        celebrityImage = (ImageView) findViewById(R.id.celebrityImage);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("http://www.posh24.se/kandisar").get();
//            Log.i("Contents of url", result);

            String[] splitResult = result.split("<div class=\"sidebarContainer\">");

            Pattern p = Pattern.compile("<img src=\"(.*?)\"");
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


            choseCeleb = random.nextInt(celebNames.size());
            Log.i("Chosen celeb", String.valueOf(choseCeleb));

            ImageDownloader imageTask = new ImageDownloader();
            Bitmap celebImage = null;
            celebImage = imageTask.execute(celebURLs.get(choseCeleb)).get();
            Log.i("Chose celeb URL", celebURLs.get(choseCeleb));
            celebrityImage.setImageBitmap(celebImage);

            generateRandomAnswers();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
