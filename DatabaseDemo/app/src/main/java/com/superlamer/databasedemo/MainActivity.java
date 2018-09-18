package com.superlamer.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        try {
            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3 ))");
            sqLiteDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Rado', 34)");
            sqLiteDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Tomy', 4)");

            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM users", null);
            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");

            c.moveToFirst();

            while (c != null) {
                Log.i("name", c.getString(nameIndex));
                Log.i("age", String.valueOf(c.getInt(ageIndex)));

                c.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        try {
            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("events", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR, year INT(4))");
            sqLiteDatabase.execSQL("INSERT INTO events (name, year) VALUES ('Bulgaria estblished', 681)");
            sqLiteDatabase.execSQL("INSERT INTO events (name, year) VALUES ('Bulgaria liberations', 1878)");

            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM events", null);
            int nameIndex = c.getColumnIndex("name");
            int yearIndex = c.getColumnIndex("year");

            while (c != null) {

                Log.i("Event", c.getString(nameIndex));
                Log.i("Year", String.valueOf(c.getInt(yearIndex)));


                c.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
