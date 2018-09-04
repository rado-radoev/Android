package com.superlamer.egg_timer_2;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView timerClock;
    private SeekBar timerSeekbar;
    private Button goBth;
    private Boolean counterIsActive = false;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goBth = (Button) findViewById(R.id.goBtn);

        timerClock = (TextView) findViewById(R.id.timerClock);
        timerClock.setText("0:30");

        timerSeekbar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);


        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
                if (progress < 0) {
                    progress = 0;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setTitle("Egg Timer");
    }

    private void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondsStr = String.valueOf(seconds);
        if (seconds < 10) {
            secondsStr = "0" + secondsStr;
        }

        timerClock.setText(String.format("%d:%s", minutes, secondsStr));
    }

    private void resetClock() {
        timerSeekbar.setEnabled(true);
        timerClock.setText("0:30");
        countDownTimer.cancel();
        goBth.setText("GO!");
        counterIsActive = false;
    }

    public void controlTimer(View view) {

        if (!counterIsActive) {
            counterIsActive = true;
            timerSeekbar.setEnabled(false);
            goBth.setText("Stop");

            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int)millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timerClock.setText("0:00");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.watch_alarm);
                    mediaPlayer.start();
                    resetClock();
                }
            }.start();
        } else {
            resetClock();
        }
    }
}
