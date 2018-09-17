package com.superlamer.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView notesListView;
    protected static ArrayList<String> notesList;
    protected static ArrayAdapter notesListAdapter;
    private SharedPreferences sharedPreferences;

    /**
     * Method that creates a new note menu
     *
     * @param menu the new menut to be created
     * @return the new menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.add_new_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Method to perform an action when an item on the menu is selected
     *
     * @param item the item that was selected
     * @return a boolean if the action executed properly
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        notesListView = (ListView) findViewById(R.id.notesListVIew);

//        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
//                intent.putExtra("note", parent.getItemAtPosition(position).toString());
//                intent.putExtra("arrayIndex", position);
//                startActivity(intent);
//            }
//        });

        notesList = new ArrayList<>();
        notesListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notesList);
        notesList.add("Example Note");
        sharedPreferences.edit().putString("notes", "Example Note").apply();
        notesListAdapter.notifyDataSetChanged();

        sharedPreferences.getString("notes", "");
        notesListAdapter.notifyDataSetChanged();

    }
}