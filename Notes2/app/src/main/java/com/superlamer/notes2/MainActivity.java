package com.superlamer.notes2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> notes;
    ArrayAdapter<String> notesAdapter;
    ListView notesList;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notes = new ArrayList<>();
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesList = (ListView) findViewById(R.id.notesList);
        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        notes.add("Example note");

    }
}
