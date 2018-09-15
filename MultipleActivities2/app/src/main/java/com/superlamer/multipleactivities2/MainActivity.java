package com.superlamer.multipleactivities2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView friendsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        friendsListView = (ListView) findViewById(R.id.friendsListView);

        final String[] friendsNames = new String[] {"Peter", "John", "Mark", "Coolio", "LL Cool J"};
        final ArrayList<String> friendsList = new ArrayList<String>();
        friendsList.addAll(Arrays.asList(friendsNames));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendsList);
        friendsListView.setAdapter(arrayAdapter);

        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                        intent.putExtra("name", friendsList.get(position));

                        startActivity(intent);

                        arrayAdapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });
            }
        });

    }
}
