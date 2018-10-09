package com.superlamer.twitterclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseUser;
import com.superlamer.twitterclone.ObjectSerializer;
import com.superlamer.twitterclone.R;

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
                // do something
                break;
            case R.id.yourFeed:
                // do something
                break;
            case R.id.logout:
                // do something
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("User List");

        intent = getIntent();
        try {
            users = (ArrayList<String>) ObjectSerializer.deserialize(intent.getStringExtra("users"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        userListView = (ListView) findViewById(R.id.usersListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);
        userListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }
}
