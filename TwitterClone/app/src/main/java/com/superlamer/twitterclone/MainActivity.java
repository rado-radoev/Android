package com.superlamer.twitterclone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private SharedPreferences sharedPreferences;
    Intent intent;
    ArrayList<String> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Twitter: Login");

        setUsername(findViewById(R.id.usernameTextField));
        setPasswrod(findViewById(R.id.passwordTextField));
        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

    }

    private void redirect() {
        if (ParseUser.getCurrentUser() != null) {
            ParseUser.logInInBackground(getUsername().getText().toString(), getPasswrod().getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), UserList.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Login Fialed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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

    public void signupLogin(View view) {

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", getUsername().getText().toString());
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null  && objects.size() > 0) {
                    userLogIn();
                } else {
                    userSignUp();
                }
            }
        });
    }


    private boolean userSignUp() {
        final boolean[] signupSuccessful = new boolean[1];
        signupSuccessful[0] = false;

        if (!isFieldEmpty(getUsername()) && !isFieldEmpty(getPasswrod())) { // username filed has text
            final ParseUser user = new ParseUser();
            user.setUsername(getUsername().getText().toString());
            user.setPassword(getPasswrod().getText().toString());
            
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Info", "Sign up successful");

                        Toast.makeText(MainActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();

                        signupSuccessful[0] = true;

                        sharedPreferences.edit().putString("username", getUsername().getText().toString());

                        user.put("follows", new JSONArray());
                        user.put("tweets", new JSONArray());

                        user.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i("Info", "follows column added");
                                } else {
                                    Log.i("Info", "follows column NOT added");
                                }
                            }
                        });

                        redirect();

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

    private boolean userLogIn() {
        final boolean[] loginSuccessful = new boolean[1];
        loginSuccessful[0] = false;

        if (!isFieldEmpty(getUsername()) && !isFieldEmpty(getPasswrod())) {
            ParseUser.logInInBackground(getUsername().getText().toString(), getPasswrod().getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Log.i("Login", "Successful");
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        sharedPreferences.edit().putString("username", getUsername().getText().toString());
                        loginSuccessful[0] = true;
                    } else {
                        Log.i("Login", "Failed");
                        Toast.makeText(MainActivity.this, "Login fialed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        loginSuccessful[0] = false;
                    }
                }
            });
        }

        redirect();
        return loginSuccessful[0];
    }

//region Unused Code
/*    private void allUsers() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                Log.i("Info", "Inside findcallback");
                if (e == null) {
                    Log.i("Info", "Not null");
                    int i = 0;
                    for (ParseUser obj : objects) {
                        Log.i("Info", "Parsing: " + obj.getUsername());
                        users.add(obj.getUsername().toString());
                        Log.i("Info", "User in array: " + users.get(i));
                        i++;
                    }
                } else {
                    Log.i("Info", "Error occured: " + e.getMessage());
                }
            }
        });
    }


    private JSONArray getAllUsers() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        final JSONArray jsonArray = new JSONArray();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    JSONObject jsonObject;
                    for (int i = 0; i < objects.size(); i++) {
                        try {
                            jsonObject = new JSONObject();
                            jsonObject.put("name", objects.get(i).getUsername());
                            jsonArray.put(i, jsonObject);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    Log.i("Info", "Error occured: " + e.getMessage());
                }
            }
        });

        return jsonArray;
    }


    private ArrayList<String> convertJSONToArrayList(JSONArray jsonArray) {
        ArrayList<String> users = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                users.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return  users;
    }

*/
//endregion

    public EditText getUsername() {
        return username;
    }

    public void setUsername(View view) {
        this.username = (EditText) view;
    }

    public EditText getPasswrod() {
        return password;
    }

    public void setPasswrod(View view) {
        this.password = (EditText) view;
    }
}
