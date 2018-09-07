package com.superlamer.braintrainer2;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;

public class GameActivity extends AppCompatActivity {

    private TextView countDownTextView;
    private TextView taskTextView;
    private TextView answerCorrectness;
    private TextView scoreTextView;
    private Button playAgain;
    private SecureRandom random;
    private static String[] MATHEMATICAL_OPERATORS;
    private static int result;
    private final int MAX_QUESTIONS = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Instantiate score text view
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        // Instantiate answer correctness text field
        answerCorrectness = (TextView)  findViewById(R.id.answerCorectness);

        // Instantiate taskTextView
        taskTextView = (TextView) findViewById(R.id.taskTextView);

        // Instantiata MATHEMATICAL_OPERATORS array
        MATHEMATICAL_OPERATORS = new String[]{"+" , "-", "/", "*"};


        // Instantiate random object
        random = new SecureRandom();

        // Play again button should be disabled until the timer reaches 0
        playAgain = (Button) findViewById(R.id.playAgainBtn);
        playAgain.setEnabled(false);

    }

    public void startGame() {
        startCountDown();
        generateMathProblem();
        updateAnswerFields();

        playAgain.setEnabled(false);
        answerCorrectness.setVisibility(View.INVISIBLE);

        updateScore();
    }

    private void startCountDown() {
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
     */
    private void generateMathProblem() {
        // Grab random operator
        int MAX = 100;

        // generat two random numbers
        int num1 = random.nextInt(MAX) + 1;
        int num2 = random.nextInt(MAX) + 1;
        int res = 0;

        // get random operation
        String operator = MATHEMATICAL_OPERATORS[random.nextInt(MATHEMATICAL_OPERATORS.length)];

        // update the text view
        taskTextView.setText(String.format("%d %s %d", num1, operator, num2));

        // do the operation
        switch (operator) {
            case "+":
                res = num1 + num2;
                break;
            case "-":
                res = num1 - num2;
                break;
            case "*":
                res = num1 * num2;
                break;
            case "/":
                res = num1 / num2;
                break;
            default:
                res = 0;
        }

        // output the res
        result = res;
    }

    /**
     * Method to generate random numbers and assign to buttons
     */
    private void updateAnswerFields() {

        // add all buttons to array
        Button[] buttons = new Button[] {findViewById(R.id.button0),
                                        findViewById(R.id.button1),
                                        findViewById(R.id.button2),
                                        findViewById(R.id.button3)};

        // generate random number for all of the buttons
        for (Button b : buttons) {
            b.setText(random.nextInt(result + 32 - random.nextInt(30)));
        }

        // Assign reult to a random button
        buttons[random.nextInt(buttons.length)].setText(result);
    }

    private boolean isCorrect(int answer) {

        boolean correct = false;

        if (result == answer) {
            correct = true;
            updateScore();
        }

        return correct;
    }

    private void updateAnswerCorectness(boolean answerCorrect) {

        answerCorrectness.setVisibility(View.VISIBLE);

        if (answerCorrect) {
            answerCorrectness.setText("CORRECT");
            answerCorrectness.setTextColor(Color.rgb(0,255,0));
        } else {
            answerCorrectness.setText("WRONG");
            answerCorrectness.setTextColor(Color.rgb(255,0,0));
        }
    }

    public void getUserAnswer(View view) {

        Button b = (Button) view;
        int buttonDigit = Integer.valueOf(b.getText().toString());

        if (isCorrect(buttonDigit)) {
            updateAnswerCorectness(true);
            updateScore();
        }

    }

    private void updateScore() {
        // get current score and increment
        String currentScoreText = scoreTextView.getText().toString().split("/")[0];
        int currentScore = Integer.valueOf(currentScoreText);

        currentScore++;

        scoreTextView.setText(String.format("%d/%d", currentScore, MAX_QUESTIONS));
    }

    public void resetGame() {
        // reset timer to 30s
        // reset results
        // reset buttons
        // hide correct/wrong text
        // generate new task
        // disable play again button

    }
 }
