package com.superlamer.multipleactivities2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class SecondActivity extends AppCompatActivity {


    public void goToMainActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView secondActivityTextView = (TextView) findViewById(R.id.secondActivityTextView);
        Intent intent = getIntent();

        secondActivityTextView.setText(intent.getStringExtra("name"));
    }
}
