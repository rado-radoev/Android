package com.superlamer.eggtimer;

import android.os.CountDownTimer;
import android.os.Handler;
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
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (handler == null) {
            handler = new Handler();
        }

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
//                Log.i("Minutes: ", String.valueOf(minutes));
//                Log.i("Seconds: ", String.valueOf(seconds));
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

    public Runnable timerUpdater = new Runnable() {
        @Override
        public void run() {
            int minutes = Integer.valueOf(timeDisplay.getText().toString().split(":")[0]);
            int seconds = Integer.valueOf(timeDisplay.getText().toString().split(":")[1]);

            handler.postDelayed(this, 1000);
            if (seconds >= 0) {
                seconds--;
            }

            if (seconds == 0){
                seconds = 59;
                minutes --;
                timeDisplay.setText(String.format(Locale.US, "%d:%02d", minutes, seconds));
            }

            if (minutes > 0) {
                Log.i("Minutes: ", String.valueOf(minutes) + " Seconds: " + String.valueOf(seconds));
                timeDisplay.setText(String.format(Locale.US, "%d:%02d", minutes, seconds));
            }
        }
    };


    public void startCountDown(View view) {

        Log.i("isCountdownRunning: ", String.valueOf(isCountDownRunning));
        // if there is no countdowm currently running
        // start countdown and change button text
        // change coundwonRunning variable to true

        // if there is countdown runnin
        // stop countdown
        // change button text to go
        // change varibale to false

        if (!isCountDownRunning) {
            goBtn.setText("STOP!");
            timeSeekBar.setVisibility(View.INVISIBLE);
            handler.post(timerUpdater);
            isCountDownRunning = true;
        }
        else {
            timeSeekBar.setVisibility(View.VISIBLE);
            goBtn.setText("GO!");
            handler.removeCallbacks(timerUpdater);
            isCountDownRunning = false;
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
