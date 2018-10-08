package com.superlamer.twitterclone;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Twitter: Login");

        setUsername(findViewById(R.id.usernameTextField));
        setPasswrod(findViewById(R.id.passwordTextField));

        if (ParseObject.)

        // Signup new user
        if (ParseUser.getCurrentUser() == null) {

        }
    }

    private boolean isFieldEmpty(EditText textField) {

        boolean emptyFields = true; // empty fields: YES

        if (textField.getText().length() > 0){
            emptyFields = false; // empty fields: NO
        }

        return  emptyFields;
    }

    private boolean isUserLoggedIn() {

        boolean isUserLoggedIn = false; // user Logged in: NO
        if (ParseUser.getCurrentUser() != null) {
            isUserLoggedIn = true; // user logged in: YES
        }

        return isUserLoggedIn;
    }


    private boolean userSignUp(String username, int[] password) {
        final boolean[] signupSuccessful = new boolean[1];
        signupSuccessful[0] = false;

        if (isFieldEmpty(getUsername()) && isFieldEmpty(getPasswrod()) &&
                !isUserLoggedIn()) { // username filed has text
            ParseUser user = new ParseUser();
            user.setUsername(getUsername().getText().toString());
            user.setPassword(getPasswrod().getText().toString());
            
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Info", "Sign up successful");
                        Toast.makeText(MainActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                        signupSuccessful[0] = true;
                        sharedPreferences.edit().putString("username", getUsername().toString());
                        // dispalyListOfUsers();
                    } else {
                        Log.i("Error", "Sing up unsuccessful!");
                        Toast.makeText(MainActivity.this, "Signup failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        signupSuccessful[0] = false;
                    }
                }
            });
        } else {
            Toast.makeText(this, "Username and password are required! Or you are alrady logged in!", Toast.LENGTH_SHORT).show();
        }

        return signupSuccessful[0];
    }




    public EditText getUsername() {
        return username;
    }

    public void setUsername(View view) {
        this.username = username;
    }

    public EditText getPasswrod() {
        return password;
    }

    public void setPasswrod(View view) {
        this.password = password;
    }
}
