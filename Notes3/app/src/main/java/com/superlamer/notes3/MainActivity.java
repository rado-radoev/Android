package com.superlamer.notes3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //region Getters and Setters
    @org.jetbrains.annotations.Contract(pure = true)
    public static ArrayList<String> getNotesList() {
        return notesList;
    }

    public void setNotesList(ArrayList<String> notesList) {
        this.notesList = notesList;
    }

    public static ArrayAdapter<String> getArrayAdapter() {
        return arrayAdapter;
    }

    public void setArrayAdapter(ArrayAdapter<String> arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }

    public static ListView getNotesListView() {
        return notesListView;
    }

    public void setNotesListView(ListView notesListView) {
        this.notesListView = notesListView;
    }
    //endregion

    private static ArrayList<String> notesList;
    private static ArrayAdapter<String> arrayAdapter;
    private static ListView notesListView;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addNewNote:
                Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNotesList(new ArrayList<String>());
        setNotesListView((ListView) findViewById(R.id.notesListView));
        setArrayAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getNotesList()));

        getNotesListView().setAdapter(getArrayAdapter());

        //region Note ListView on Item Click Listener
        getNotesListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                intent.putExtra("noteId", position);
                startActivity(intent);
            }
        });
        //endregion

        //region note ListView on Long Click Listener
        getNotesListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you really want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getNotesList().remove(position);
                                getArrayAdapter().notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
        //endregion


        if (getNotesList().size() == 0) {
            getNotesList().add("Example Note");
            getArrayAdapter().notifyDataSetChanged();
        }
    }
}
