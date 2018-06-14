package com.superlamer.eggtimer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView timeDisplay;
    private SeekBar timeSeekBar;
    private Button goBtn;
    private boolean isCountDownRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goBtn = findViewById(R.id.goBtn);

        timeDisplay = findViewById(R.id.displayTimer);
        timeDisplay.setText("0:30");


        timeSeekBar = findViewById(R.id.timerbar);
        timeSeekBar.setMax(600);
        timeSeekBar.setProgress(30);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int minutes = (progress % 3600) / 60;
                int seconds = (progress % 3600) % 60;
                Log.i("Minutes: ", String.valueOf(minutes));
                Log.i("Seconds: ", String.valueOf(seconds));
                timeDisplay.setText(String.format(Locale.US,"%d:%02d", minutes, seconds));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void startCountDown(View view) {

        if (isCountDownRunning) {
            goBtn.setText("GO!");
            timeSeekBar.setVisibility(View.VISIBLE);
        }
        else {
            timeSeekBar.setVisibility(View.INVISIBLE);
            goBtn.setText("STOP!");
        }

//        new CountDownTimer(time , 1000) {
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//                textView.setText(String.valueOf("seconds remaining: " + millisUntilFinished / 1000));
//            }
//
//            @Override
//            public void onFinish() {
//                textView.setText("0:00");
//            }
//        }.start();
    }
}
