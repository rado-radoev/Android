package com.superlamer.twitterclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import java.util.ArrayList;

public class Feed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        setTitle("Your feed");

        final ListView feedListView = (ListView) findViewById(R.id.feedListView);

        List<Map<String, String>> tweetData = new ArrayList<Map<String, String>>();

//
//        for (int i = 1; i <= 5; i++) {
//            Map<String, String> tweetInfo = new HashMap<String, String>();
//
//            tweetInfo.put("content", "Tweet Content: " + String.valueOf(i));
//
//            tweetInfo.put("username", "User: " + String.valueOf(i));
//
//            tweetData.add(tweetInfo);
//        }


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
        query.whereContainedIn("username", ParseUser.getCurrentUser().getList("follows"));
        query.orderByDescending("createdAt");
        query.setLimit(20);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    if (objects.size() > 0) {

                        List<Map<String, String>> tweetData = new ArrayList<Map<String, String>>();

                        for (ParseObject tweet : objects) {

                            Map<String, String> tweetInfo = new HashMap<String, String>();

                            tweetInfo.put("content", tweet.getString("tweet"));

                            tweetInfo.put("username", tweet.getString("username"));

                            tweetData.add(tweetInfo);


                        }

                        SimpleAdapter simpleAdapter = new SimpleAdapter(Feed.this, tweetData, android.R.layout.simple_list_item_2, new String[] {"content", "username"}, new int[] {android.R.id.text1, android.R.id.text2});

                        feedListView.setAdapter(simpleAdapter);

                    }



                }
            }
        });

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, tweetData, android.R.layout.simple_list_item_2,
                new String[]{"content", "username"}, new int[] {android.R.id.text1, android.R.id.text2});

        feedListView.setAdapter(simpleAdapter);
    }
}
