package com.superlamer.basic_phrases_2;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mplayer;

    public void speak(View view) {

        String buttonTag = view.getTag().toString();

        int song = (raw) findViewById(R.raw

        mplayer = MediaPlayer.create(this,R.raw.;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
