/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //region Add entries to Parse DB
    /*
    ParseObject score = new ParseObject("Score");
    score.put("username", "rado");
    score.put("score", 90);
    score.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null) {
                Log.i("SaveInBackground", "Successful");
            } else {
                Log.i("SaveInBackground", "Failed Error: " + e.toString());
            }
        }
    });
    */
    //endregion

      //region Retrieve and update data from DB
      /*
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
      query.getInBackground("urAH6vn2JO", new GetCallback<ParseObject>() {
          @Override
          public void done(ParseObject object, ParseException e) {
              if (e == null && object != null) {

                  object.put("score", 200);
                  object.saveInBackground();

                  Log.i("ObjValue", object.getString("username"));
                  Log.i("ObjValue", Integer.toString(object.getInt("score")));
              }
          }
      });
      */
      //endregion

      // Create a Tweet class, username, tweet, save on Parse, then query it, and update the tweet content

      ParseObject object = new ParseObject("Tweet");
      object.put("username", "tweeter1");
      object.put("tweet", "This is my first ever tweet");
      object.saveInBackground(new SaveCallback() {
          @Override
          public void done(ParseException e) {
              if (e == null) {
                  Toast.makeText(getApplicationContext(), "Tweet created", Toast.LENGTH_LONG).show();
              } else {
                  Toast.makeText(getApplicationContext(), "Tweet cannot be created", Toast.LENGTH_LONG).show();
              }
          }
      });

      // Retrieve tweet
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
      query.getInBackground("1CFcykUjuy", new GetCallback<ParseObject>() {
          @Override
          public void done(ParseObject object, ParseException e) {
              if (e == null && object != null) {


                  Toast.makeText(getApplicationContext(), object.getString("username") + " tweeted: " + object.getString("tweet"), Toast.LENGTH_LONG).show();

                  object.put("tweet", "And this is my second tweet");
                  object.saveInBackground();

                  Toast.makeText(getApplicationContext(), object.getString("username") + " tweeted: " + object.getString("tweet"), Toast.LENGTH_LONG).show();
              }
          }
      });

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}