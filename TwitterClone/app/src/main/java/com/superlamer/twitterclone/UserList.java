package com.superlamer.twitterclone;

import android.content.Intent;
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
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
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

        users = new ArrayList<>();
        users.add("Alex");
        users.add("Emma");

        setTitle("User List");

        intent = getIntent();
        userListView = (ListView) findViewById(R.id.usersListView);
        userListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckedTextView checkedTextView = (CheckedTextView) view;

                if (checkedTextView.isChecked()) {
                    Log.i("Info", "Row Checked");
                } else {
                    Log.i("Info", "Row is not checked");
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
                }
            }
        });

    }
}
