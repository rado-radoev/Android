package com.superlamer.logindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void logIn(View view) {
        EditText userNameText = (EditText) findViewById(R.id.userNameField);
        EditText passwordText = (EditText) findViewById(R.id.passwordField);

        Toast.makeText(MainActivity.this, "Hii" , Toast.LENGTH_LONG);

        Log.i("Info", String.format("%s %s", "UserName:", userNameText.getText().toString()));
        Log.i("Info", String.format("%s %s", "Password:", passwordText.getText().toString()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
