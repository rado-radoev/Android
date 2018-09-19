package com.superlamer.notes3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NotesActivity extends AppCompatActivity {

    private EditText getNoteText() {
        return noteText;
    }

    private void setNoteText(EditText noteText) {
        this.noteText = noteText;
    }

    private int getNoteId() {
        return noteId;
    }

    private void setNoteId(int noteId) {
        this.noteId = noteId;
    }


    private EditText noteText;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        setNoteText((EditText) findViewById(R.id.noteText));

        Intent intent = getIntent();
        setNoteId(intent.getIntExtra("noteId", -1));

        if (getNoteId() == -1) {
            getNoteText().setText("");
        } else {
            getNoteText().setText(MainActivity.getNotesList().get(noteId).toString());
        }



        getNoteText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getNoteId() == -1) {
                    setNoteId(MainActivity.getNotesList().size());
                    MainActivity.getNotesList().add(getNoteId(), s.toString());
                } else {
                    MainActivity.getNotesList().set(getNoteId(), s.toString());
                }

                MainActivity.getArrayAdapter().notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
