package com.superlamer.whatsappclone2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    Button loginOrSignupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("WhatsApp - Login / Signup");

        usernameText = (EditText) findViewById(R.id.userNameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginOrSignupBtn = (Button) findViewById(R.id.LoginSignupBtn);

        redirect();

    }

    public void loginOrSignup(View view) {
        if (isEmpty(usernameText) || isEmpty(passwordText)) {
            Toast.makeText(this, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (ParseUser.getCurrentUser() == null) {
                signUp();
            } else {
                login();
            }
        }
    }

    public void logout(View view) {
        if (ParseUser.getCurrentUser() != null) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Logout failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public boolean isEmpty(EditText textField) {

        boolean isEmpty = false;

        if (textField.getText().toString().equalsIgnoreCase("")) {
            isEmpty = true;
        }

        return isEmpty;
    }

    public void signUp() {
        if (ParseUser.getCurrentUser() == null) {
            ParseUser newUser = new ParseUser();
            newUser.setUsername(usernameText.getText().toString());
            newUser.setPassword(passwordText.getText().toString());

            newUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "Singup Successful", Toast.LENGTH_SHORT).show();
                        redirect();
                    }
                    else {
                        if (e.getMessage().contains("already exists")) {
                            login();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Signup Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }


    public void login() {
        ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    redirect();
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void redirect() {
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(this, UserList.class);
            startActivity(intent);
        }
    }
}
