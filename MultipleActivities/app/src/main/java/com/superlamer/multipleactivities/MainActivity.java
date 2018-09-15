package com.superlamer.multipleactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    public void jumpToSecondActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("username", "rado");

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
