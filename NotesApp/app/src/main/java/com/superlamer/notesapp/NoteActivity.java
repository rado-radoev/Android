package com.superlamer.notesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {

    EditText note;
    ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        notesList = MainActivity.notesList;

        note =  (EditText) findViewById(R.id.noteEditText);
        final Intent intent = getIntent();

        note.setText(intent.getStringExtra("note"));

        note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //intent.putExtra("updatedNote", note.getText());
                    MainActivity.notesList.add(intent.getIntExtra("arrayIndex", 0), note.getText().toString().substring(20));
                    //MainActivity.notesListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
