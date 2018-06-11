package com.superlamer.gridlayout;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonTapped(View view) {

        int id = view.getId();
        String ourId = "";

        ourId = view.getContext().getResources().getResourceEntryName(id);

//        view.getContext().getResources().getIdentifier(ourId, "raw", "com.superlamer.gridlayout");

        int resourceID = view.getContext().getResources().getIdentifier(ourId, "raw", "com.superlamer.gridlayout");

        MediaPlayer mplayer = MediaPlayer.create(this, resourceID);
        mplayer.start();

        Log.i("button tapped", ourId);
    }
}
