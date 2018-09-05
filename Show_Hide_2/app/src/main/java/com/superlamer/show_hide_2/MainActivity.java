package com.superlamer.show_hide_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textField;
    private Button showBtn;
    private Button hideBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Show and Hide");

        textField = findViewById(R.id.textField);
        showBtn = findViewById(R.id.showBtn);
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textField.setVisibility(View.VISIBLE);
            }
        });

        hideBtn = findViewById(R.id.hideBtn);
        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textField.setVisibility(View.INVISIBLE);
            }
        });
    }
}
