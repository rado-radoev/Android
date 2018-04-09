package com.superlamer.numbers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void calculate(View view) {
        EditText userText = (EditText) findViewById(R.id.numberTextField);

        int num = Integer.parseInt(userText.getText().toString());


        if (isTriangular(num)) {
            Toast.makeText(MainActivity.this, String.format("%d is triangular", num), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this, String.format("%d is not triangular", num), Toast.LENGTH_LONG).show();

            if (isSquare(num)) {
                Toast.makeText(MainActivity.this, String.format("%d is square", num), Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(MainActivity.this, String.format("%d is square", num), Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean isSquare(int num) {
        double squareRoot = Math.sqrt(num);

        if (squareRoot == Math.floor(squareRoot))
            return true;
        else
            return false;
    }

    public boolean isTriangular(int num) {

        if (!isNumberValid(num)) return false;

        int sum = 0;

        for (int n = 1; n <= num; n++) {
            sum += n;
            if (sum == num) return true;
        }

        return false;
    }


    private boolean isNumberValid(int num) {
        if (num < 0) return false;
        switch (num % 10) {
            case 2:
            case 3:
            case 7:
            case 8:
                return false;
        }

        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
