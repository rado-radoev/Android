package com.superlamer.alertspreferencesmenus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> languages = new ArrayList<>();
    ListView languagesListView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.language_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        boolean successful = false;

        switch (item.getItemId()) {
            case R.id.english:
                arrayAdapter.clear();
                languages.add("English");
                arrayAdapter.notifyDataSetChanged();
                successful = true;
                break;
            case R.id.spanish:
                arrayAdapter.clear();
                languages.add("Spanish");
                arrayAdapter.notifyDataSetChanged();
                successful = true;
                break;
            default:
                successful = false;
                break;
        }

        return  successful;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        languagesListView = (ListView) findViewById(R.id.languageListView);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, languages);
        languagesListView.setAdapter(arrayAdapter);
    }
}
