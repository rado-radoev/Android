package com.superlamer.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ChallengeActivity extends AppCompatActivity {

    private TextView textView;
    private final int COUNTDOWNTIME = 30;
    private TextView countDownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        textView = findViewById(R.id.answerText);
        textView.setVisibility(View.INVISIBLE);

        startCountDown();
    }

    public void startCountDown() {

        int countDownTime = Integer.valueOf(countDownText.getText().toString().substring(0,2));

        new CountDownTimer(COUNTDOWNTIME, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) millisUntilFinished / 60;
                countDownText.setText(String.format("%2ds", time));
            }

            @Override
            public void onFinish() {
                Log.i("Time is up", "");

            }
        }.start();
    }
}
