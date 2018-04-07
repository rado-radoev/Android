package com.superlamer.higherorlower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    private SecureRandom rnd = new SecureRandom();
    private int numberToGuess;


//    private void fillInArray () {
//        for (int i = 0; i < numbersToSearch.length; i++) {
//            numbersToSearch[i] = i + 1;
//        }
//    }


//    private void searchNumber(int numberToSearchFor) {
//        int i = 1;
//        int j = numbersToSearch.length;
//
//        while (i < j) {
//            int m = (i + j) / 2;
//
//            if (numberToSearchFor > numbersToSearch[m]) {
//                i = m + 1;
//            } else {
//                j = m;
//            }
//        }
//
//        if (numberToSearchFor == numbersToSearch[i]) {
//            Toast.makeText(MainActivity.this, "You guessed it!", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(MainActivity.this, "Choose again", Toast.LENGTH_LONG).show();
//        }
//    }

    public void guessNumber(View view) {

        EditText userEntry = (EditText) findViewById(R.id.userGuessTextField);
        int num = Integer.valueOf(userEntry.getText().toString());

        if (num < numberToGuess)
            Toast.makeText(MainActivity.this, "Guess Higher!",Toast.LENGTH_SHORT).show();
        else if (num > numberToGuess)
            Toast.makeText(MainActivity.this, "Guess Lower!",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "You guessed it!",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberToGuess = rnd.nextInt(20) + 1;
        //fillInArray();
    }
}
