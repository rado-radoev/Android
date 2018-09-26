package com.superlamer.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.security.Key;


public class LoginActivity extends AppCompatActivity implements  View.OnClickListener {

    private EditText userNameTextField;
    private EditText passwordTextField;
    private TextView signupOrLoginTextField;
    private Button signupLoginButton;
    private ImageView instagramLogo;
    private boolean signUpModeActive = true;
    private RelativeLayout backgroundRelativeLayout;

     @Override
    public void onClick(View v) {

         if (v.getId() == R.id.signupOrLoginTextField) {
            Log.i("AppInfo", "change signup mode");
            if (signUpModeActive) {
                signUpModeActive = false;
                signupLoginButton.setText("Login");
                signupOrLoginTextField.setText("Or, Signup");
            } else {
                signUpModeActive = true;
                signupLoginButton.setText("Signup");
                signupOrLoginTextField.setText("Or, Login");
            }

             userNameTextField.setText("");
             passwordTextField.setText("");
             
        } else if (v.getId() == R.id.backgroundRelativeLayout || v.getId() == R.id.instragramLogo) {
             InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                 inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
         }


     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        instantiateLayoutObjs();
    }

    private final void instantiateLayoutObjs() {

        userNameTextField = findViewById(R.id.usernameTextField);
        userNameTextField.setOnKeyListener(new KeyHandler());

        passwordTextField = findViewById(R.id.passwordTextField);
        passwordTextField.setOnKeyListener(new KeyHandler());

        signupOrLoginTextField = findViewById(R.id.signupOrLoginTextField);
        signupOrLoginTextField.setOnClickListener(this);


        signupLoginButton = findViewById(R.id.loginButton);

        instagramLogo = findViewById(R.id.instragramLogo);
        instagramLogo.setOnClickListener(this);

        backgroundRelativeLayout = findViewById(R.id.backgroundRelativeLayout);
        backgroundRelativeLayout.setOnClickListener(this);
    }


    public void signUp(View view) {
        if (signUpModeActive) {
            userSignUp(userNameTextField.getText().toString(), passwordTextField.getText().toString());
        } else {
            userLogin(userNameTextField.getText().toString(), passwordTextField.getText().toString());
        }

    }


    private boolean checkEmptyFields() {

        boolean isFieldEmpty = false;

        if (userNameTextField.getText().length() == 0 || passwordTextField.getText().length() == 0) {
            isFieldEmpty = true;
        }

        return isFieldEmpty;
    }

    private boolean userLogin(String userName, String password) {

        final boolean[] loginSuccessful = new boolean[1];
        loginSuccessful[0] = false;

        if (!checkEmptyFields()) {
            ParseUser.logInInBackground(userName, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        Log.i("Login", "Successful");
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        loginSuccessful[0] = true;
                    } else {
                        Log.i("Login", "Failed: " + e.toString());
                        Toast.makeText(getApplicationContext(), "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        loginSuccessful[0] = true;
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Username and Password are required", Toast.LENGTH_SHORT).show();
        }

        return loginSuccessful[0];
    }


    private boolean userSignUp(String userName, String password) {

        final boolean[] signUpSuccessful = new boolean[1];
        signUpSuccessful[0] = false;

        if (!checkEmptyFields()) {
            ParseUser user = new ParseUser();
            user.setUsername(userName);
            user.setPassword(password);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Sign up", "Successful");
                        Toast.makeText(getApplicationContext(), "SingUp Successful", Toast.LENGTH_SHORT).show();
                        signUpSuccessful[0] = true;
                    } else {
                        Log.i("Sign up", "Failed: " + e.toString());
                        Toast.makeText(getApplicationContext(), "SignUp Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        signUpSuccessful[0] = false;
                    }
                }
            });
        }  else {
            Toast.makeText(getApplicationContext(), "Username and Password are required", Toast.LENGTH_SHORT).show();
        }

        return signUpSuccessful[0];
    }


    class KeyHandler implements View.OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                Log.i("Enter", "Pressed");
                signUp(v);
                return true;
            }

            return false;
        }
    }

}


