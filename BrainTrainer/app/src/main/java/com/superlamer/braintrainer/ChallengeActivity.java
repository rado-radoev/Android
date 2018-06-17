package com.superlamer.braintrainer;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;

public class ChallengeActivity extends AppCompatActivity {

    private final int COUNTDOWNTIME = 30;
    private final int MAX_QUESTIONS = 10;
    private int currentQuestion;
    private TextView countDownText;
    private TextView answerText;
    private TextView opText;
    private TextView resultText;
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

        countDownText = findViewById(R.id.countDownText);
        answerText = findViewById(R.id.answerText);
        opText = findViewById(R.id.operationText);
        resultText = findViewById(R.id.resultText);

        resultText.setText(String.format("%d/%d", 0, MAX_QUESTIONS));

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
        int num1 = rnd.nextInt(10) + 1;
        int num2 = 0;
        while (num2 < num1) {
            num2 = rnd.nextInt(10);
        }

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
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Unsupported mathematical operator supplied");
        }
    }

    private void advanceToNextQuetion() {
        currentQuestion++;
        Log.i("Current question: ", String.valueOf(currentQuestion));
        if (currentQuestion <= MAX_QUESTIONS) {
            generateTask();
            updateButtonText();
        }
        else {
            answerText.setText(String.format("Your score is: ", resultText.getText()));
        }
    }

    public void btnClicked(View view) {
        String answer = String.valueOf(result);
        String buttonText = ((Button) view).getText().toString();

        if (answer.equals(buttonText)) {
            answerText.setTextColor(Color.GREEN);
            answerText.setText("Correct");
            updateReusultText();
            advanceToNextQuetion();
        }
        else {
            answerText.setTextColor(Color.RED);
            answerText.setText("Wrong answer");
        }
    }

    private void updateReusultText() {
        result++;
        String res = resultText.getText().toString();
        String currentResult = res.substring(0, res.indexOf("/"));

        String newResult = String.valueOf(Integer.valueOf(currentResult) + 1);
        resultText.setText(String.format("%s/%d", newResult, MAX_QUESTIONS));
    }

    private void updateButtonText() {
        View view = (View) findViewById(android.R.id.content);
        ArrayList<Button> buttons = new ArrayList<Button>();

        for (View v : view.getTouchables()) {
            if (v instanceof  Button) {
                ((Button) v).setText(String.valueOf(rnd.nextInt(40) + 1));
                buttons.add((Button) v);
            }
        }

        buttons.get(rnd.nextInt(buttons.size())).setText(String.valueOf(result));
    }


    public void startCountDown() {

        final int countDownTime = Integer.valueOf(countDownText.getText().toString().substring(0,2));

        countDownText.setTextColor(Color.GRAY);

        new CountDownTimer(COUNTDOWNTIME * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) millisUntilFinished / 1000;
                //Log.i("Time is up", String.valueOf(time));
                countDownText.setText(String.format("%2ds", time));
            }

            @Override
            public void onFinish() {
                countDownText.setText(String.format("%2ds", 0));
                countDownText.setTextColor(Color.RED);
                answerText.setText(String.format("Your score is: %s", resultText.getText().toString()));
                currentQuestion = MAX_QUESTIONS + 1;
                //Log.i("Time is up", "");
            }
        }.start();
    }
}
