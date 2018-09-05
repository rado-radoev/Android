package com.superlamer.braintrainer2;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;

public class GameActivity extends AppCompatActivity {

    private TextView countDownTextView;
    private Button playAgain;
    private SecureRandom random;
    private static String[] MATHEMATICAL_OPERATORS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Instantiata MATHEMATICAL_OPERATORS array
        MATHEMATICAL_OPERATORS = new String[]{"+" , "-", "/", "*"};


        // Instantiate random object
        random = new SecureRandom();

        // Play again button should be disabled until the timer reaches 0
        playAgain = (Button) findViewById(R.id.playAgainBtn);
        playAgain.setEnabled(false);


        // Start countdown timer. 30 seconds and counting
        countDownTextView = (TextView) findViewById(R.id.countDownTextView);
        countDownTextView.setText("30s");
        new CountDownTimer(30000 + 100, 1000) {

            int timer = 30;

            @Override
            public void onTick(long millisUntilFinished) {
                countDownTextView.setText(String.format("%02ds", timer));
                timer--;
            }

            @Override
            public void onFinish() {
                countDownTextView.setText("00s");
                playAgain.setEnabled(true);
            }

        }.start();
    }

    /**
     * Method to generate random math problems.
     * Operations will be addition, substraction, multiplication and division of integers
     * Math problem label will be updated within this method
     * @return returns integer, the result of the math problem
     */
    private int generateMathProblem() {
        // Grab random operator
        // generat two random numbers
        // do the operation
        // update the text view
        // output the result
    }

    private void updateAnswerFields() {
        // add all buttons to array
        // generate random number for 3 of the buttons
        // one random button will hold the result
    }
 }
