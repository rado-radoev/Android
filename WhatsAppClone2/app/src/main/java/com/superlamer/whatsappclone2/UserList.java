package com.superlamer.whatsappclone2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

public class UserList extends AppCompatActivity {

    ArrayList<String> userList;
    ArrayAdapter usersArrayAdapter;
    ListView usersListView;
    String senderUserName;
    String receiverUserName;
    EditText chatText;
    String messageId;
    Intent intent;

    private final String[] conversationId = new String[1];

    private final void setConversationId(String conversationId) {
        this.conversationId[0] = conversationId;
    }

    public final String[] getConversationId() {
        return conversationId;
    }


    public void sendMessage(View view) {
        // iF there is a conversation between the two users get the ID and display all messages
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Conversation");
        query.whereEqualTo("Receiver", receiverUserName);
        query.whereEqualTo("Sender", senderUserName);
        query.orderByDescending("createdAt");
        query.setLimit(1);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                 if (e == null && objects.size() > 0) {
                    setConversationId(objects.get(0).getObjectId());
                    Log.i("Conversation id", getConversationId()[0]);
                } else {
                    System.out.println("Could not get conversation id. New conversation started");
                    final ParseObject newConvo = new ParseObject("Conversation");
                    System.out.println("objId = " + newConvo.getObjectId());
                    newConvo.put("Sender", senderUserName);
                    newConvo.put("Receiver", receiverUserName);
                    newConvo.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Toast.makeText(UserList.this, "New conversation could not be created!", Toast.LENGTH_SHORT).show();
                            } else {
                                setConversationId(newConvo.getObjectId());

                                System.out.println("Starting new conversation");

                                setConversationId(newConvo.getObjectId());

                                final ParseObject newMessage = new ParseObject("Messages");
                                newMessage.put("conversationId", getConversationId()[0]);
                                System.out.println("Object ID: " + getConversationId()[0]);
                                intent.putExtra("conversationId", getConversationId()[0]);

                                newMessage.put("Sender", senderUserName);
                                System.out.println("Sender: " + senderUserName);
                                newMessage.put("Receiver", receiverUserName);
                                System.out.println("Receiver: " + receiverUserName);
                                Map<String, String> sentMsg = new HashMap<String, String>();
                                sentMsg.put("username", senderUserName);
                                sentMsg.put("message", chatText.getText().toString());
                                newMessage.add("Messages", sentMsg);
                                newMessage.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            messageId = newMessage.getObjectId();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        if (getConversationId()[0] != null) {

            intent.putExtra("conversationId", getConversationId()[0]);
            intent.putExtra("sender", senderUserName);
            intent.putExtra("receiver", receiverUserName);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("User Chat list");

        intent = new Intent(this, ChatActivity.class);

        chatText = (EditText) findViewById(R.id.chatText);

        senderUserName = ParseUser.getCurrentUser().getUsername();
        Log.i("Sender username", senderUserName);

        usersListView =  (ListView) findViewById(R.id.userListView);
        usersListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                receiverUserName = userList.get(position);
                Log.i("Receiver username", receiverUserName);
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
