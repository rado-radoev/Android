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
    private static int currentQuestion;

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
        MATHEMATICAL_OPERATORS = new String[]{"+" , "-"};


        // Instantiate random object
        random = new SecureRandom();

        // Play again button should be disabled until the timer reaches 0
        playAgain = (Button) findViewById(R.id.playAgainBtn);
        playAgain.setEnabled(false);

        startGame();

    }

    public void startGame() {
        // Instantiate current qustion variable
        currentQuestion = 1;
        
        startCountDown();
        generateMathProblem();
        updateAnswerFields();

        playAgain.setEnabled(false);
        answerCorrectness.setVisibility(View.INVISIBLE);
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
                currentQuestion = MAX_QUESTIONS;
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
        int MAX = 30;

        // generat two random numbers
        int num1 = random.nextInt(MAX) + 1;
        int num2 = random.nextInt(MAX) + 1;
        if (num1 < num2) {
            int temp = num2;
            num2 = num1;
            num1 = temp;
        }

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
            int randomAnswer = Math.abs(random.nextInt(result + 32 - random.nextInt(30)));
            b.setText(String.valueOf(randomAnswer));
        }

        // Assign reult to a random button
        buttons[random.nextInt(buttons.length)].setText(String.valueOf(result));
    }

    private boolean isCorrect(int answer) {

        boolean correct = false;

        if (result == answer) {
            correct = true;
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
        else {
            updateAnswerCorectness(false);
        }

        if (currentQuestion <= MAX_QUESTIONS) {
            nextQuestion();
            currentQuestion++;
        }
    }

    private void updateScore() {
        // get current score and increment
        String currentScoreText = scoreTextView.getText().toString().split("/")[0];
        int currentScore = Integer.valueOf(currentScoreText);

        currentScore++;

        scoreTextView.setText(String.format("%d/%d", currentScore, MAX_QUESTIONS));
    }

    private void nextQuestion() {
        generateMathProblem();
        updateAnswerFields();
    }

    private void resetTimer() {
        countDownTextView.setText("30s");
    }

    private void resetResults() {

    }

    public void resetGame(View view) {
        // reset timer to 30s
        countDownTextView.setText("30s");
        // reset results
        scoreTextView.setText(String.format("%d/%d", 0, MAX_QUESTIONS));
        // start game over
        startGame();
        // reset question counter
        currentQuestion = 1;
    }
 }
