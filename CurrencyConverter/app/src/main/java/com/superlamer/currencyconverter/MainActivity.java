package com.superlamer.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void convertCurrentcy(View view) {
        EditText amountToConvert = (EditText) findViewById(R.id.amountToConvertTextField);

        Double convertAmount = Double.parseDouble(amountToConvert.getText().toString());

        Toast.makeText(MainActivity.this, String.format("%.2f", (convertAmount * 1.60)).concat(" BG Lev"), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
