package com.superlamer.uber;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    private Switch riderDriverSwitch;
    private TextView driverTextView;
    private TextView riderTextView;


    private void redirectActivity() {
        Log.i ("riderOrDriver", ParseUser.getCurrentUser().get("riderOrDriver").toString());
        if (ParseUser.getCurrentUser().get("riderOrDriver").toString().toLowerCase().equals("rider")) {
            Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), ViewRequestsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        riderTextView = (TextView) findViewById(R.id.riderTextView);
        driverTextView = (TextView) findViewById(R.id.driverTextView);
        riderDriverSwitch = (Switch) findViewById(R.id.riderDriverSwitch);

        riderDriverSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    driverTextView.setTextColor(Color. BLACK);
                    riderTextView.setTextColor(Color.GRAY);
                    Log.i("Swtich is", String.valueOf(isChecked));
                } else {
                    riderTextView.setTextColor(Color.BLACK);
                    driverTextView.setTextColor(Color.GRAY);
                    Log.i("Swtich is", String.valueOf(isChecked));
                }
            }
        });

        if (ParseUser.getCurrentUser() == null) {
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Log.i("Anoymouse user", "login successful");
                    }
                    else {
                        Log.i("Anonmouse user", "login failed");
                    }
                }
            });
        } else {
            if (ParseUser.getCurrentUser().get("riderOrDriver") != null) {
                Log.i("Info", "Redirecting as " + ParseUser.getCurrentUser().get("riderOrDriver"));
                redirectActivity();
            }
        }

        getSupportActionBar().hide();

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void start(View view) {

        if (riderDriverSwitch.isChecked()) {
            ParseUser.getCurrentUser().put("riderOrDriver", riderDriverSwitch.getTextOn().toString().toLowerCase());
            Log.i("Redirecting as", riderDriverSwitch.getTextOn().toString());
        }
        else {
            ParseUser.getCurrentUser().put("riderOrDriver", riderDriverSwitch.getTextOff().toString());
            Log.i("Redirecting as", riderDriverSwitch.getTextOff().toString().toLowerCase());
        }

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                redirectActivity();
            }
        });


    }
}
