package com.superlamer.currencyconverter2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private double LEVVALUE = 1.76;

    public void convertCurrency(View view) {

        String currentCurrency = ((TextView)findViewById(R.id.currencyText)).getText().toString();
        Log.i("Current currency is: ", currentCurrency);
        double currCurrency = 0D;
        try {
            currCurrency = Double.valueOf(currentCurrency);
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, "Wrong number format", Toast.LENGTH_LONG).show();
        }

        Currency currToConvertFrom = new Currency("US Dollar", currCurrency);
        Currency currToConvertTo = new Currency("BG Lev", LEVVALUE);

        double convertedCurr = calcCurrency(currToConvertFrom, currToConvertTo);

        Toast.makeText(this, String.valueOf(convertedCurr), Toast.LENGTH_LONG).show();

    }

    private double calcCurrency(Currency currToConverFrom, Currency currToConvertTo) {
        return  currToConverFrom.getValue() * currToConvertTo.getValue();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
