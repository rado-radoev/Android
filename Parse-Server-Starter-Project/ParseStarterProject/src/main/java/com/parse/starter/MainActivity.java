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

import com.parse.FindCallback;
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

import java.util.List;
import java.util.Random;

// https://docs.bitnami.com/aws/faq/get-started/find-credentials/#option-2-find-password-by-connecting-to-your-application-through-ssh

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


    /** Generate 100 users and add to DB
      Random rnd = new Random();
      for (int i = 0; i < 100; i++) {
          ParseObject obj = new ParseObject("Score");
          obj.put("username", "user" + i);
          obj.put("score", rnd.nextInt(500));
          obj.saveEventually(new SaveCallback() {
              @Override
              public void done(ParseException e) {
                  if (e != null) {
                      e.printStackTrace();
                  } else {
                      Log.i("User save", "Successful");
                  }

              }
          });
      }
     */

    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.whereGreaterThan("score", 200).findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> objects, ParseException e) {
            if (e == null && objects != null) {
                for (ParseObject objectO : objects) {
                    Log.i("User: " + objectO.getString("username") + ". Old score", String.valueOf(objectO.getInt("score")));
                    objectO.put("score", objectO.getInt("score") + 50);
                    Log.i("User: " + objectO.getString("username") + ". New score", String.valueOf(objectO.getInt("score")));
                    objectO.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.i("Score", "Updated");
                            }
                            else {
                                Log.i("Score", "Not updated");
                            }
                        }
                    });
                }
            }
        }
    });

/*
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

      query.whereEqualTo("username", "tweeter1");
      query.setLimit(1);

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
*/




    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}