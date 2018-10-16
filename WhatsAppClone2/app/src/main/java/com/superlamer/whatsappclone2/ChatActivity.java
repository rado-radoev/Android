package com.superlamer.whatsappclone2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    EditText chatText;
    String conversationId;
    int layoutPosition = 0; // 0 == left, 1 == right
    Intent intent = getIntent();


    public void sendMessage(View view) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Messages");
        query.whereEqualTo("conversationId", intent.getStringExtra("conversationId"));

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject message : objects) {
                        List<Map<String, String>> messagesList = message.getList("messages");
                        Map<String, String> newMessage = new HashMap<String, String>();
                        newMessage.put("username", intent.getStringExtra("receiver"));
                        newMessage.put("message", chatText.getText().toString());
                        messagesList.add(newMessage);

                        message.remove("messages");
                        message.put("messages", messagesList);

                        message.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if ( e == null) {

                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        conversationId = intent.getStringExtra("conversationId");

        chatText = (EditText) findViewById(R.id.chatText);

        final ListView chatListView = (ListView) findViewById(R.id.chatListView);


        List<Map<String, String>> messageData = new ArrayList<Map<String, String>>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Messages");
        query.whereEqualTo("conversationId", conversationId);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null ) {
                    if (objects.size() > 0) {
                        List<Map<String, String>> messageData = new ArrayList<>();

                        for (ParseObject message : objects) {
                            List<Map<String, String>> messages = message.getList("Messages");
                            for (Map<String, String> msg: messages) {
                                messageData.add(msg);
                            }
                        }
                        SimpleAdapter simpleAdapter = new SimpleAdapter(ChatActivity.this, messageData, android.R.layout.simple_expandable_list_item_2,
                                new String[] { "message", "username" }, new int[] {android.R.id.text1, android.R.id.text2});
                        chatListView.setAdapter(simpleAdapter);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, messageData, android.R.layout.simple_expandable_list_item_2,
                new String[] {"message", "username"}, new int[] {android.R.id.text1, android.R.id.text2});
    }
}
