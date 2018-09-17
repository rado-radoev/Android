package com.superlamer.alertspreferencesmenus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> languages = new ArrayList<>();
    private ListView languagesListView;
    private ArrayAdapter<String> arrayAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.language_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        setLanguage(item.getTitle().toString());
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        languagesListView = (ListView) findViewById(R.id.languageListView);
        sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, languages);
        languagesListView.setAdapter(arrayAdapter);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Language")
                .setMessage("Please choose  your preferred language")
                .setPositiveButton("Spanish", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setLanguage("Spanish");
                    }
                })
                .setNegativeButton("English", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setLanguage("English");
                    }
                })
                .setNeutralButton("No change", null)
                .show();


        String storedLanguage = sharedPreferences.getString("language", "");
        languages.add(storedLanguage);
        arrayAdapter.notifyDataSetChanged();

    }

    private void setLanguage(String language) {
        arrayAdapter.clear();
        languages.add(language);
        sharedPreferences.edit().putString("language", language).apply();
        arrayAdapter.notifyDataSetChanged();
    }
}
