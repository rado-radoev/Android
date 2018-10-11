package com.superlamer.twitterclone;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.superlamer.twitterclone.ObjectSerializer;
import com.superlamer.twitterclone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {

    private Intent intent;
    private ArrayList<String> users;
    private ListView userListView;
    private ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tweet_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.tweet:
                addTweet();
                break;
            case R.id.yourFeed:
                getListOfTweets(ParseUser.getCurrentUser());
                break;
            case R.id.logout:
                ParseUser.logOut();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateTweetSize(String tweet) {
        int TWEET_MAX_LENGTH = 240;
        boolean tweetLengthInRnage = false;

        if (tweet.length() <= TWEET_MAX_LENGTH) {
            tweetLengthInRnage = true;
        }

        return tweetLengthInRnage;
    }

    public void addTweet() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send a tweet");

        final EditText tweetContentEditText = new EditText(this);

        builder.setView(tweetContentEditText);
        builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("info", tweetContentEditText.getText().toString());

                ParseUser.getCurrentUser().add("tweets", tweetContentEditText.getText().toString());

                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(UserList.this, "Tweet tweeted!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserList.this, "Tweet cannot be tweeted! Try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();



    }

    public void getListOfTweets (ParseUser curentUser) {
        // get all tweets for user
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
        query.whereEqualTo("username", curentUser.getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    JSONArray jsonArray;
                    for (int i = 0; i < objects.size(); i++) {
                        jsonArray = objects.get(i).getJSONArray("tweets");

                        for (int k = 0; k < jsonArray.length(); k++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(k);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }

                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        users = new ArrayList<>();
        users.add("Alex");
        users.add("Emma");

        setTitle("User List");

        if (ParseUser.getCurrentUser().get("follows") == null) {

            List<String> emptyList = new ArrayList<String>();
            ParseUser.getCurrentUser().put("follows", emptyList);
        }

        if (ParseUser.getCurrentUser().get("tweets") == null) {

            List<String> emptyList = new ArrayList<String>();
            ParseUser.getCurrentUser().put("tweets", emptyList);
        }

        intent = getIntent();
        userListView = (ListView) findViewById(R.id.usersListView);
        userListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckedTextView checkedTextView = (CheckedTextView) view;

                if (checkedTextView.isChecked()) {
                    Log.i("Info", "Row Checked");

                    List<String> usernames = new ArrayList<>();

                    try {
                        usernames = ParseUser.getCurrentUser().getList("follows");
                    } catch (NullPointerException npe) {
                        npe.printStackTrace();
                    }


                    if (!usernames.contains(users.get(position))) {
                        ParseUser.getCurrentUser().add("follows", users.get(position));
                        ParseUser.getCurrentUser().saveInBackground();
                    }




                } else {
                    Log.i("Info", "Row is not checked");

                    ParseUser.getCurrentUser().getList("follows").remove(users.get(position));
                    List newlist = ParseUser.getCurrentUser().getList("follows");
                    ParseUser.getCurrentUser().remove("follows");
                    ParseUser.getCurrentUser().put("follows", newlist);

                    ParseUser.getCurrentUser().saveInBackground();
                }

            }
        });

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, users);
        userListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


        users.clear();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseUser user : objects) {
                        users.add(user.getUsername());
                    }

                    arrayAdapter.notifyDataSetChanged();

                    for (String username : users) {
                        if (ParseUser.getCurrentUser().getList("follows").contains(username)) {
                            userListView.setItemChecked(users.indexOf(username), true);
                        }
                    }
                }
            }
        });

    }
}
