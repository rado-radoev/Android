package com.superlamer.braintrainer;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;

public class ChallengeActivity extends AppCompatActivity {

    private TextView textView;
    private final int COUNTDOWNTIME = 30;
    private TextView countDownText;
    private TextView answerText;
    private TextView opText;
    private int score;
    private SecureRandom rnd;
    private final int RANDOM_SEED = 50;
    private ArrayList<String> operators = new ArrayList<String>();
    private int activity_challenge;
    private int result;
    private  GridLayout gLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);


        operators = new ArrayList<String>();

        rnd = new SecureRandom();

        textView = findViewById(R.id.answerText);
        textView.setVisibility(View.INVISIBLE);

        countDownText = findViewById(R.id.countDownText);
        answerText = findViewById(R.id.answerText);
        opText = findViewById(R.id.operationText);

        generateListOfOperators();
        generateTask();
        updateButtonText();
        startCountDown();
    }

    private void generateListOfOperators() {
        operators.add("+");
        operators.add("-");
        operators.add("*");
        operators.add("/");
    }

    public void generateTask() {
        int num1 = rnd.nextInt(10);
        int num2 = rnd.nextInt(10);

        String operator = operators.get(rnd.nextInt(operators.size()));

        opText.setText(String.format("%d %s %d", num1, operator, num2));

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case  "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
            case "/":
                if (num1 < num2){
                    while (num1 > num2) {
                        num1 = rnd.nextInt(50);
                    }
                }
                result = num1 / num2;
                break;
            default:
                break;
        }
    }

    public void btnClicked(View view) {
        String answer = String.valueOf(result);
        String buttonText = ((Button) findViewById(R.id.answer1)).getText().toString();
        if (answer.equals(buttonText)) {
            answerText.setVisibility(View.VISIBLE);
            answerText.setText("Correct");
        }
        else {
            answerText.setVisibility(View.VISIBLE);
            answerText.setText("Wrong answer");
        }
    }

    private void updateButtonText() {
        Button btn1 = findViewById(R.id.answer1);
        Button btn2 = findViewById(R.id.answer2);
        Button btn3 = findViewById(R.id.answer3);
        Button btn4 = findViewById(R.id.answer4);

       btn1.setText(String.valueOf(result));
       btn2.setText(String.valueOf(rnd.nextInt(10) + result));
       btn3.setText(String.valueOf(rnd.nextInt(10) + result));
       btn4.setText(String.valueOf(rnd.nextInt(10) + result));
    }

    public boolean isAnswerCorrect(int userAnser, int expectedAnswer) {
        return userAnser == expectedAnswer;
    }

    public void startCountDown() {

        //countDownText.setText(String.format("%2ds", COUNTDOWNTIME));
        final int countDownTime = Integer.valueOf(countDownText.getText().toString().substring(0,2));

        countDownText.setTextColor(Color.BLACK);
        answerText.setAlpha(0);

        new CountDownTimer(COUNTDOWNTIME * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) millisUntilFinished / 1000;
                Log.i("Time is up", String.valueOf(time));
                countDownText.setText(String.format("%2ds", time));
            }

            @Override
            public void onFinish() {
                countDownText.setText(String.format("%2ds", 0));
                countDownText.setTextColor(Color.RED);
                answerText.setText(String.format("You score is: ", score ));
                answerText.setAlpha(1);
                Log.i("Time is up", "");

            }
        }.start();
    }
}
