package com.superlamer.whatsappclone2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.LoginException;

public class UserList extends AppCompatActivity {

    private ArrayList<String> userList;
    private ArrayAdapter usersArrayAdapter;
    private ListView usersListView;
    private String senderUserName;
    private String receiverUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("User Chat list");

        senderUserName = ParseUser.getCurrentUser().getUsername();
        Log.i("Sender username", senderUserName);

        usersListView =  (ListView) findViewById(R.id.userListView);
        usersListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                receiverUserName = userList.get(position);
                Log.i("Receiver username", receiverUserName);
                ParseObject newMessage = new ParseObject("Messages");

                // iF there is a conversation between the two users get the ID and display all messages
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Conversation");
                List<String> chatParticipants = new ArrayList<>();
                chatParticipants.add(senderUserName);
                chatParticipants.add(receiverUserName);
                query.whereContainsAll()
                // else create new conversation ID and fill in the Messages db
            }
        });

        userList = new ArrayList<>();
        usersArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, userList);
        usersListView.setAdapter(usersArrayAdapter);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    for (ParseUser user : objects) {
                        userList.add(user.getUsername());
                    }
                    usersArrayAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(UserList.this, "No chat users found! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        usersArrayAdapter.notifyDataSetChanged();
    }

}
