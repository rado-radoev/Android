package com.superlamer.basic_phrases_2;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mplayer;

    public void speak(View view) {

        String buttonTag = view.getTag().toString();

        int resourceId = getResources().getIdentifier(buttonTag, "raw", getApplicationContext().getPackageName());
        MediaPlayer mediaPlayer = MediaPlayer.create(this, resourceId);
        mediaPlayer.start();

        Log.i("Button tapped", buttonTag);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
