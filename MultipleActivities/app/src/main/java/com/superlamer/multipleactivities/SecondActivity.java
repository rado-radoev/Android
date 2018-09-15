package com.superlamer.multipleactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    public void jumpToMainActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        Toast.makeText(getApplicationContext(), intent.getStringExtra("username"), Toast.LENGTH_LONG).show();
    }
}
